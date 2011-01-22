package de.tdng2011.game.kernel.collision

import de.tdng2011.game.kernel.{Shot, Player}

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/22/11
 * Time: 9:26 PM
 * To change this template use File | Settings | File Templates.
 */

object PlayerShotContract extends Contract {
   onCollide {
     case (a : Player, b: Shot) => {
       println("i am applying!");
     }

     case (a : Shot, b : Player) => {
       println("i am applying!");
     }

     case barbraStreisand => {
       println("debug (implement log here): ill ignore " + barbraStreisand);
     }
   }
}