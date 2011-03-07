package de.tdng2011.game.kernel

import actors.Actor
import de.tdng2011.game.library.util.ScubywarsLogger

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 23.01.11
 * Time: 16:59
 */

object ScoreBoard extends Actor with ScubywarsLogger {

  var scores = Map[Long, Int]()

  def act {
    loop {
      react {
        case x : PlayerAddedMessage => {
          scores = scores + (x.publicId -> 0)
        }

        case x : PlayerRemovedMessage => {
          scores = scores - x.player.publicId
        }

        case x : AddPointsMessage => {
          scores = scores + (x.publicId -> (scores.get(x.publicId).getOrElse(0) + x.points))
          ConnectionHandler.event(ScoreBoardChangedMessage(scores))
          logger.debug("AddPointsMessage received, scores are now: " + scores)
        }

        case x => {
          logger.warn("Received unknown message " + x)
        }
      }
    }
  }
}