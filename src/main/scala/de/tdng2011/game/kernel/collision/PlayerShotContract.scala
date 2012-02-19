package de.tdng2011.game.kernel.collision

import de.tdng2011.game.kernel._
import de.tdng2011.game.library.util.ScubywarsLogger

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/22/11
 * Time: 9:26 PM
 * To change this template use File | Settings | File Templates.
 */

object PlayerShotContract extends Contract with ScubywarsLogger {
  onCollide {
    case (a: Player, b: Shot) => {
      boom(a, b)
    }

    case (b: Shot, a: Player) => {
      boom(a, b)
    }

    case barbraStreisand => {
      logger.trace("ignore message " + barbraStreisand);
    }
  }

  private def boom(a: Player, b: Shot) {
    logger.debug("player " + a.publicId + " & shot " + b.publicId + " collided! sending respawn message!")

    ScoreBoard !! AddPointsMessage(-1, a.publicId)
    ScoreBoard !! AddPointsMessage(2, b.parentId)
    a !! RespawnMessage()
    World !! RemoveShotFromWorldMessage(b)
    ConnectionHandler event PlayerKilledMessage(a.publicId, b.publicId, b.parentId, b.pos, a.pos)

  }

}