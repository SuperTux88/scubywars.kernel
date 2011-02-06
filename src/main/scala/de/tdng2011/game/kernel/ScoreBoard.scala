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

  var players = Map[Long, Int]()

  def act {
    loop {
      react {
        case x : PlayerAddToScoreboardMessage => {
          players = players + (x.publicId -> 0)
        }

        case x : AddPointsMessage => {
          players = players + (x.publicId -> (players.get(x.publicId).getOrElse(0) + x.points))
          println(players)
        }

        case x => {
          println("I am the score board! I received message: " + x)
        }
      }
    }
  }
}