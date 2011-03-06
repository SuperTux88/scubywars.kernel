package de.tdng2011.game.kernel

import java.net.{Socket, ServerSocket}
import actors.Actor
import Actor.State._
import java.io.DataInputStream
import de.tdng2011.game.library.util.{StreamUtil, ByteUtil}
import de.tdng2011.game.library.EntityTypes

/*
very very quick and dirty hack, no production code!
*/
object ConnectionHandler extends Runnable {

  var clientActors = List[Actor]()
  val socket = new ServerSocket(1337);

  def event(event : Any){
    clientActors.foreach(a => if(a.getState != Terminated) a !! event)
  }

  new Thread(this).start


  override def run(){
    while(true) {
      val clientSocket = socket.accept
      val clientThread = new ClientActor(clientSocket).start
      clientActors = clientThread :: clientActors
      clientActors = clientActors.filter(_.getState != Terminated)
      println("ClientActors: " + clientActors.size)
    }
  }
}

class ClientActor(val clientSocket : Socket) extends Actor {

  private var player : Player = null

  var relation = 0

  private var handshakeFinished = false;
  def act = {
    loop {
      react {
        case x : IndexedSeq[EntityDescription] => {

          if(mailboxSize > 15){
            println("alert, " + this + " client actor has a mailbox size of " + mailboxSize)
          }

          if(handshakeFinished) {
            try {
              if(clientSocket.isConnected){
                clientSocket.getOutputStream.write(ByteUtil.toByteArray(EntityTypes.World, x.size))
                x.foreach(b => clientSocket.getOutputStream.write(b.bytes))
              } else {
                removeClient
              }
            } catch {
              case e => removeClient
            }
          } else {
            handshake(clientSocket);
          }
        }

        // TODO: was ist mit messages waehrend dem handshake

        case x : PlayerAddedMessage => {
          sendBytesToClient(ByteUtil.toByteArray(EntityTypes.PlayerJoined, x.publicId, x.name))
        }

        case x : PlayerRemovedMessage => {
          sendBytesToClient(ByteUtil.toByteArray(EntityTypes.PlayerLeft, x.player.publicId))
        }

        case x : ScoreBoardChangedMessage => {
          sendBytesToClient(ByteUtil.toByteArray(EntityTypes.Scoreboard, x.scoreBoard))
        }

        case _ => {}
      }
    }
  }

  def sendBytesToClient(bytes : Array[Byte]) {
    if(handshakeFinished) {
      try {
        if(clientSocket.isConnected){
          clientSocket.getOutputStream.write(bytes)
        }
      }
    }
  }

  def removeClient {
    if (relation == 0) {
      World !! RemovePlayerFromWorldMessage(player)
    }
    exit
  }

  def handshake(clientSocket : Socket) {
    val iStream  = new DataInputStream(clientSocket.getInputStream)
    val buf      = StreamUtil.read(iStream, 8)
    val typeId   = buf.getShort
    val size     = buf.getInt
    relation = buf.getShort
    if(relation == 0) { // player case, 1 is listener
      handShakePlayer(iStream, size - 2)
    } else if(relation != 1) { // not visualizer
      println("illegal connection from " + clientSocket.getInetAddress + " - closing connection!");
      clientSocket.close
    }
    finishHandshake(clientSocket)
  }

  def finishHandshake(clientSocket : Socket) {
    handshakeFinished=true
    sendBytesToClient(ByteUtil.toByteArray(EntityTypes.Scoreboard, ScoreBoard.scores))
    for (name <- World.nameMap) {
      sendBytesToClient(ByteUtil.toByteArray(EntityTypes.PlayerName, name._1, name._2))
    }
  }

  def handShakePlayer(iStream : DataInputStream, size : Int) {
    val name = StreamUtil.read(iStream, size).asCharBuffer.toString
    World !? AddPlayerMessage(name) match {
      case x : Some[Player] => {
        player = x.get
        new Thread(new ReaderThread(clientSocket,player)).start
        println("started client thread")
        clientSocket.getOutputStream.write(ByteUtil.toByteArray(EntityTypes.Handshake, 0.byteValue, player.publicId))
        ScoreBoard !! PlayerAddedMessage(player.publicId, name)
      }
      case x => {
        println("fatal response from player add: " + x)
        clientSocket.getOutputStream.write(ByteUtil.toByteArray(EntityTypes.Handshake, 1.byteValue))
      }
    }
  }
}

class ReaderThread(val clientSocket : Socket, player : Actor) extends Runnable {
   override def run(){
     val iStream = new DataInputStream(clientSocket.getInputStream)
    while(true){
      val buf       = StreamUtil.read(iStream, 6)
      val typeId    = buf.getShort
      val size      = buf.getInt

      val msgBuffer = StreamUtil.read(iStream, size)
      if (typeId == EntityTypes.Action.id) {
        val turnLeft = msgBuffer.get == 1
        val turnRight = msgBuffer.get == 1
        val thrust = msgBuffer.get == 1
        val fire = msgBuffer.get == 1
        player !! PlayerActionMessage(turnLeft, turnRight, thrust, fire)
      }
    }
  }
}




