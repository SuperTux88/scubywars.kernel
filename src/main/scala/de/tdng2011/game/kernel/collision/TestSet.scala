package de.tdng2011.game.kernel.collision

import actors.Actor
import de.tdng2011.game.kernel._

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 23.01.11
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */

object TestSet {
   def main(args : Array[String]){
     var trackedCollisions = Set[(Actor,Actor)]()
     val playerA = new Player(Vec2(10,10), 1);
     val playerB = new Player(Vec2(20,20), 2);
     trackedCollisions = trackedCollisions + ((playerA,playerB))

     println(trackedCollisions.contains(playerA, playerB))
     println(trackedCollisions.contains(playerB, playerA))

   }
}