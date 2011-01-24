package de.tdng2011.game.kernel

import java.net.{Socket, ServerSocket}
import actors.Actor


/*
very very quick and dirty hack, no production code!
*/
object ConnectionHandler extends Runnable {

  var clientThreads = List[ClientActor]()
  val socket = new ServerSocket(1337);

  def event(entityDescriptions : IndexedSeq[EntityDescription]){
    clientThreads.map(a => a !! entityDescriptions)
    clientThreads = (for(t  <- clientThreads) yield if(t.getState != Actor.State.Terminated) t).asInstanceOf[List[ClientActor]]
  }

  new Thread(this).start


  override def run(){
    while(true) {
      val clientSocket = socket.accept
      val clientThread = new ClientActor(clientSocket)
      clientThreads = clientThread :: clientThreads
      clientThread.start
    }
  }
}

class ClientActor(val clientSocket : Socket) extends Actor {

  def act = {
    loop {
      react {
        case x : IndexedSeq[EntityDescription] => {
          try {
            if(clientSocket.isConnected) x.map(b => clientSocket.getOutputStream.write(b.bytes))
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