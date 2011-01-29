package de.tdng2011.game.kernel

import java.net.{Socket, ServerSocket}
import actors.Actor
import Actor.State._
import java.io.DataInputStream
import de.tdng2011.game.util.{StreamUtil, ByteUtil}

/*
very very quick and dirty hack, no production code!
*/
object ConnectionHandler extends Runnable {

  var clientActors = List[Actor]()
  val socket = new ServerSocket(1337);

  def event(entityDescriptions : IndexedSeq[EntityDescription]){
    clientActors.map(a => if(a.getState != Terminated) a !! entityDescriptions)
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
  private var handshakeFinished = false;
  def act = {
    loop {
      react {
        case x : IndexedSeq[EntityDescription] => {
          if(handshakeFinished){
            try {

                if(clientSocket.isConnected){
                  clientSocket.getOutputStream.write(ByteUtil.toByteArray(EntityTypes.World.id.shortValue))
                  x.map(b => clientSocket.getOutputStream.write(b.bytes))
                } else {
                  exit
                }
            } catch {
              case e => exit
            }
          } else {
            handshake(clientSocket);
          }
        }

        case x : ActorKillMessage => exit

        case _ => {}
      }
    }
  }


  def handshake(clientSocket : Socket){
    println("handshake now!")
    val stream = new DataInputStream(clientSocket.getInputStream)
    val relation = StreamUtil.read(stream, 2).getShort
    println("relation received! " + relation)
    if(relation == 0){ // player case, 1 is listener
      val name = StreamUtil.read(stream, 24).asCharBuffer.toString
      val player = World !? PlayerAddMessage() match {
        case x : Some[Player] => {
          new Thread(new ReaderThread(clientSocket,x.get)).start
          println("startet client thread")
          clientSocket.getOutputStream.write(ByteUtil.toByteArray(0.byteValue,x.get.publicId))
          // TODO: send name to scoreboard with public id
        }
        case x => {
          println("fatal response from player add: " + x)
        }
      }
    } else if(relation != 1){ // not visualizer
      println("illegal connection from " + clientSocket.getInetAddress + " - closing connection!");
      clientSocket.close

    }
    handshakeFinished=true
  }
}

class ReaderThread(val clientSocket : Socket, player : Actor) extends Runnable {
   override def run(){
    while(true){
      val msgBuffer = StreamUtil.read(new DataInputStream(clientSocket.getInputStream), 4)
      val turnLeft = msgBuffer.get == 1
      val turnRight = msgBuffer.get == 1
      val thrust = msgBuffer.get == 1
      val fire = msgBuffer.get == 1
      player !! PlayerActionMessage(turnLeft, turnRight, thrust, fire)
      println("server received playerAction: " + turnLeft + " : " + turnRight + " : " + thrust + " : " + fire)
    }
  }
}




