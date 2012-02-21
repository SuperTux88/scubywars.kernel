package de.tdng2011.game.kernel

import actors.Actor

object IdActor extends Actor {
  var publicIds: Long = 0

  def getNextId = (IdActor !? IdRequestMessage).asInstanceOf[Long]

  def act = {
    loop {
      react {
        case x => {
          publicIds += 1
          reply {
            publicIds
          }
        }
      }
    }
  }
}