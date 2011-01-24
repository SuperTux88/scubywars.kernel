package de.tdng2011.game.kernel

import java.net.{Socket, ServerSocket}
import java.util.concurrent.{LinkedBlockingQueue, ArrayBlockingQueue, BlockingQueue}

/*
very very quick and dirty hack, no production code!
*/
object ConnectionHandler extends Runnable {

  var clientThreads = List[ClientThread]()
  val socket = new ServerSocket(1337);

  def event(entityDescriptions : IndexedSeq[EntityDescription]){
    for(thread <- clientThreads){
      for(description <- entityDescriptions){
        thread.messageQueue.add(description.bytes)
      }
    }
  }

  new Thread(this).start


  override def run(){
    while(true) {
      val clientSocket = socket.accept
      val clientThread = new ClientThread(clientSocket)
      clientThreads = clientThread :: clientThreads
      new Thread(clientThread).start
    }
  }
}

class ClientThread(val clientSocket : Socket) extends Runnable{
    val messageQueue = new LinkedBlockingQueue[Array[Byte]]()

    override def run(){
      while(true) {
        val byteArray = messageQueue.take
        if(clientSocket.isConnected) {
          clientSocket.getOutputStream.write(byteArray);
        } else {
          return;
        }
      }
    }
}