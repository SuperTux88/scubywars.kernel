package de.tdng2011.game.kernel

import actors.Actor

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 23.01.11
 * Time: 16:59
 * To change this template use File | Settings | File Templates.
 */
case class Score()

object ScoreBoard extends Actor {
  def act {
    loop {
      react {
        case x : ActorKillMessage => {
          exit
        }
        case x => {
          println("I am the score board! I received message: " + x)
        }
      }
    }
  }
}