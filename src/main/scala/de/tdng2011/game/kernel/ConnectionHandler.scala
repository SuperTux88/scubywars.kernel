package de.tdng2011.game.kernel

import java.net.{Socket, ServerSocket}
import actors.Actor
import Actor.State._

/*
very very quick and dirty hack, no production code!
*/
object ConnectionHandler extends Runnable {

  var clientThreads = List[ClientActor]()
  val socket = new ServerSocket(1337);

  def event(entityDescriptions : IndexedSeq[EntityDescription]){
    clientThreads.map(a => if(a.getState != Terminated) a !! entityDescriptions)
  }

  new Thread(this).start


  override def run(){
    while(true) {
      val clientSocket = socket.accept
      val clientThread = new ClientActor(clientSocket)
      clientThreads = clientThread :: clientThreads
      clientThreads = clientThreads.filter(_.getState != Terminated)
      println("ClientActors: " + clientThreads.size)
    }
  }
}

class ClientActor(val clientSocket : Socket) extends Actor {

  def act = {
    loop {
      react {
        case x : IndexedSeq[EntityDescription] => {
          try {
            if(clientSocket.isConnected) x.map(b => clientSocket.getOutputStream.write(b.bytes)) else exit
          } catch {
            case e => exit
          }
        }

        case x : ActorKillMessage => exit

        case _ => {}
      }
    }
  }
}