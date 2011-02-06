package de.tdng2011.game.kernel.collision

import de.tdng2011.game.kernel._

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/22/11
 * Time: 9:26 PM
 * To change this template use File | Settings | File Templates.
 */

object ShotShotContract extends Contract {
  onCollide {
    case (a : Shot, b: Shot) => {
      boom(a,b)
    }

    case barbraStreisand => {
      println("debug (implement log here): ill ignore " + barbraStreisand);
    }
  }

  private def boom(a : Shot, b : Shot){
    println("shot " + a.publicId + " & shot " + b.publicId + " collided! sending remove message to world")
    World !! RemoveEntityFromWorldMessage(a)
    World !! RemoveEntityFromWorldMessage(b)
  }

}