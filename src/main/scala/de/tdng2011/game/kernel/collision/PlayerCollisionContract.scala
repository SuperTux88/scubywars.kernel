package de.tdng2011.game.kernel.collision

import de.tdng2011.game.kernel._
import de.tdng2011.game.library.util.ScubywarsLogger

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 23.01.11
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */

object PlayerCollisionContract extends Contract with ScubywarsLogger {
  onCollide {
    case (a : Player, b : Player) => {
        logger.debug("Player " + a.publicId + " & player " + b.publicId + " collided! sending respawn message!")
        a !! RespawnMessage()
        b !! RespawnMessage()
        ScoreBoard !! AddPointsMessage(-1, a.publicId)
        ScoreBoard !! AddPointsMessage(-1, b.publicId)
    }

    case x => {
      logger.trace("ignoring message " + x)
    }
  }
}