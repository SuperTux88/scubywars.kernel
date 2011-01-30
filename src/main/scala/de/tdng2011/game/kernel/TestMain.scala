package de.tdng2011.game.kernel

import de.tdng2011.game.library.EntityTypes

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 23.01.11
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */

object TestMain {
   def main(args : Array[String]){

     println(EntityTypes.Player.id)
     println(EntityTypes.Shot.id)
     println(EntityTypes.PowerUp.id)
   }
}