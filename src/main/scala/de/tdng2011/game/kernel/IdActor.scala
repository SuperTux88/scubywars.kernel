package de.tdng2011.game.kernel

import actors.Actor

object IdActor extends Actor {
  var publicIds = 0

  start

  def act = {
    loop {
      react {
        case x => {
          publicIds += 1
          publicIds
        }
      }
    }
  }
}