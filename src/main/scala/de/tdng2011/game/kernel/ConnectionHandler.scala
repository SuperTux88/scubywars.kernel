package de.tdng2011.game.kernel

import java.net.{Socket, ServerSocket}
import actors.Actor
import Actor.State._
import de.tdng2011.game.util.ByteUtil

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
  private var handshakeFinished = true;
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
            handshake();
          }
        }

        case x : ActorKillMessage => exit

        case _ => {}
      }
    }
  }


  def handshake(){
  //  clientSocket.getInputStream.read...
    val player = World !! PlayerAddMessage match {
      case x : Option[Actor] => {
        new Thread(new ReaderThread(clientSocket,x.get)).start
      }
    }
    handshakeFinished=true

  }
}

class ReaderThread(val clientSocket : Socket, player : Actor) extends Runnable {
  override def run(){
    while(true){
      // read from connection
    }
  }
}