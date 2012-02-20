package de.tdng2011.game.kernel

import java.net.{ Socket, ServerSocket }
import actors.Actor
import Actor.State._
import java.io.DataInputStream
import de.tdng2011.game.library.EntityTypes
import de.tdng2011.game.library.util.{ ScubywarsLogger, StreamUtil, ByteUtil }
import collection.immutable.Map

/*
very very quick and dirty hack, no production code!
*/
object ConnectionHandler extends Runnable with ScubywarsLogger {

  var clientActors = List[Actor]()
  val socket = new ServerSocket(1337);

  def event(event: Any) {
    clientActors.foreach(a => if (a.getState != Terminated) a !! event)
  }

  new Thread(this).start

  override def run() {
    while (true) {
      val clientSocket = socket.accept
      clientSocket setTcpNoDelay true
      val clientThread = new ClientActor(clientSocket).start
      clientActors = clientThread :: clientActors
      clientActors = clientActors.filter(_.getState != Terminated)
      logger.debug("Client Actors after filtering: " + clientActors.size)
    }
  }
}

class ClientActor(val clientSocket: Socket) extends Actor with ScubywarsLogger {

  private var player: Player = null

  var relation = 0

  private var handshakeFinished = false

  handshake

  def act = {
    loop {
      react {
        case x => sendMessageToClient(x)
      }
    }
  }

  def isNgVisualizer = relation == 3
  def isNgPlayer = relation == 2
  def isNgClient = isNgPlayer || isNgVisualizer

  def sendMessageToClient(message: Any): Unit = {

    if (mailboxSize > 15) {
      logger.warn("alert, " + this + " client actor has a mailbox size of " + mailboxSize)
    }

    try {
      message match {
        case m: IndexedSeq[EntityDescription] => {
          sendBytesToClient(ByteUtil.toByteArray(EntityTypes.World, m.size))
          m.foreach(b => sendBytesToClient(b.bytes))
        }

        case m: PlayerAddedMessage => {
          sendBytesToClient(ByteUtil.toByteArray(EntityTypes.PlayerJoined, m.publicId, m.name))
        }

        case m: PlayerRemovedMessage => {
          sendBytesToClient(ByteUtil.toByteArray(EntityTypes.PlayerLeft, m.player.publicId))
        }

        case m: ScoreBoardChangedMessage => {
          sendBytesToClient(ByteUtil.toByteArray(EntityTypes.ScoreBoard, m.scoreBoard))
        }

        // NG Messages
        case m if isNgClient => m match {
          case m: PlayerKilledMessage => {
            sendBytesToClient(ByteUtil.toByteArray(EntityTypes.PlayerKilledEvent, m.victimPublicId, m.shotPublicId, m.killerPublicId, m.shotPosition, m.victimPosition));
          }

          case m: PlayerCollisionMessage => {
            sendBytesToClient(ByteUtil.toByteArray(EntityTypes.PlayerCollisionEvent, m.player1PublicId, m.player2PublicId, m.player1Position, m.player2Position));
          }

          case m: ShotCollisionMessage => {
            sendBytesToClient(ByteUtil.toByteArray(EntityTypes.ShotCollisionEvent, m.shot1PublicId, m.shot2PublicId, m.s1Position, m.s2Position));
          }

          case m: PlayerSpawnedMessage => {
            sendBytesToClient(ByteUtil.toByteArray(EntityTypes.PlayerSpawnedEvent, m.publicId, m.position))
          }

          case m: ShotSpawnedMessage => {
            sendBytesToClient(ByteUtil.toByteArray(EntityTypes.ShotSpawnedEvent, m.publicId, m.parentId, m.position))
          }
        }

        case m => logger.trace("ignoring message " + m)
      }
    } catch {
      case e => {
        if (player != null) {
          logger.info("player " + player + " left the game (connection closed / error)")
          World !! RemovePlayerFromWorldMessage(player)
        }

        exit
      }
    }
  }

  def sendBytesToClient(bytes: Array[Byte]) {
    if (handshakeFinished) try {
      if (clientSocket.isConnected) {
        TimeoutCleanupThread.addSocket(clientSocket)
        val os = clientSocket.getOutputStream
        os.write(bytes)
        os.flush
        TimeoutCleanupThread.removeSocket(clientSocket)
      } else {
        World !! RemovePlayerFromWorldMessage(player)
      }
    }
  }

  def handshake {
    val iStream = new DataInputStream(clientSocket.getInputStream)
    val buf = StreamUtil.read(iStream, 8)
    val typeId = buf.getShort
    val size = buf.getInt
    relation = buf.getShort
    logger.info("Client handshake from " + clientSocket.getInetAddress + ", type: " + typeId + ", size: " + size + ", relation: " + relation)
    if (relation == 0 || relation == 2) { // player case, 1 or 3 is listener
      handShakePlayer(iStream, size - 2)
    } else if (relation != 3 && relation != 1) { // not visualizer
      logger.warn("Illegal connection from " + clientSocket.getInetAddress + " - closing connection!");
      clientSocket.close
    }
    finishHandshake
  }

  def finishHandshake {
    handshakeFinished = true
    for (name <- World.nameMap) {
      sendBytesToClient(ByteUtil.toByteArray(EntityTypes.PlayerName, name._1, name._2))
    }
    sendBytesToClient(ByteUtil.toByteArray(EntityTypes.ScoreBoard, ScoreBoard.scores))
  }

  def handShakePlayer(iStream: DataInputStream, size: Int) {
    val name = StreamUtil.read(iStream, size).asCharBuffer.toString
    World !? AddPlayerMessage(name) match {
      case x: Some[Player] => {
        player = x.get
        new Thread(new ReaderThread(clientSocket, player)).start
        logger.debug("started client thread")

        clientSocket.getOutputStream.write(ByteUtil.toByteArray(EntityTypes.Handshake, 0.byteValue, player.publicId))
      }
      case x => {
        logger.error("Fatal response from player add to world: " + x)
        clientSocket.getOutputStream.write(ByteUtil.toByteArray(EntityTypes.Handshake, 1.byteValue))
      }
    }
  }
}

class ReaderThread(val clientSocket: Socket, player: Actor) extends Runnable with ScubywarsLogger {
  override def run() {
    val iStream = new DataInputStream(clientSocket.getInputStream)
    var reading = true
    while (reading) {
      try {
        val buf = StreamUtil.read(iStream, 6)
        val typeId = buf.getShort
        val size = buf.getInt

        val msgBuffer = StreamUtil.read(iStream, size)
        if (typeId == EntityTypes.Action.id) {
          val turnLeft = msgBuffer.get == 1
          val turnRight = msgBuffer.get == 1
          val thrust = msgBuffer.get == 1
          val fire = msgBuffer.get == 1
          player !! PlayerActionMessage(turnLeft, turnRight, thrust, fire)
        } else {
          logger.warn("unknown typeId: " + typeId + " with size: " + size)
        }
      } catch {
        case e =>
          logger.error("Reading from client failed. Stopping reader thread now.", e)
          reading = false
      }
    }
  }
}

object TimeoutCleanupThread extends Runnable with ScubywarsLogger {
  var socketMap = Map[Socket, Long]()

  new Thread(this).start

  override def run() {
    while (true) {
      Thread sleep 100
      socketMap.foreach(s => if (System.currentTimeMillis() - s._2 > 2000) {
        logger.info("write timeout, closing socket")
        s._1.close()
        removeSocket(s._1)
      })
    }
  }

  def addSocket(socket: Socket) {
    socketMap = socketMap + (socket -> System.currentTimeMillis())
  }

  def removeSocket(socket: Socket) {
    socketMap = socketMap - socket
  }
}