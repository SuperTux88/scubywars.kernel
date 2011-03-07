package de.tdng2011.game.kernel.collision

import de.tdng2011.game.kernel._
import de.tdng2011.game.library.util.ScubywarsLogger


object ShotShotContract extends Contract with ScubywarsLogger {
  onCollide {
    case (a : Shot, b: Shot) => {
      boom(a,b)
    }

    case x => {
      logger.trace("ignoring message " + x);
    }
  }

  private def boom(a : Shot, b : Shot){
    logger.debug("Shot " + a.publicId + " & shot " + b.publicId + " collided! sending remove message to world")
    World !! RemoveShotFromWorldMessage(a)
    World !! RemoveShotFromWorldMessage(b)
  }

}