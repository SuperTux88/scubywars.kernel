package de.tdng2011.game.kernel

import actors.Actor

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 23.01.11
 * Time: 16:59
 */

object ScoreBoard extends Actor {

  var players = Map[Long, Int]()

  def act {
    loop {
      react {
        case x : PlayerAddedMessage => {
          players = players + (x.publicId -> 0)
        }

        case x : AddPointsMessage => {
          players = players + (x.publicId -> (players.get(x.publicId).getOrElse(0) + x.points))
          ConnectionHandler.event(ScoreBoardChangedMessage(players))
          println(players)
        }

        case x => {
          println("I am the score board! I received message: " + x)
        }
      }
    }
  }
}