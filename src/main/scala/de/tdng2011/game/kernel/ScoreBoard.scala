package de.tdng2011.game.kernel

import actors.Actor

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 23.01.11
 * Time: 16:59
 */

object ScoreBoard extends Actor {

  var scores = Map[Long, Int]()

  def act {
    loop {
      react {
        case x : PlayerAddedMessage => {
          scores = scores + (x.publicId -> 0)
        }

        case x : AddPointsMessage => {
          scores = scores + (x.publicId -> (scores.get(x.publicId).getOrElse(0) + x.points))
          ConnectionHandler.event(ScoreBoardChangedMessage(scores))
          println(scores)
        }

        case x => {
          println("I am the score board! I received message: " + x)
        }
      }
    }
  }
}