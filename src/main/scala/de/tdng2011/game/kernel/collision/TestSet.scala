package de.tdng2011.game.kernel.collision

import actors.Actor
import de.tdng2011.game.kernel._
import de.tdng2011.game.util.ByteUtil
import java.nio.ByteBuffer

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 23.01.11
 * Time: 16:30
 * To change this template use File | Settings | File Templates.
 */

object TestSet {
   def main(args : Array[String]){
    //val byteArray = ByteUtil.toByteArray(1d,2f,3l)


     //val byteBuffer = ByteBuffer.wrap(byteArray)
     //println(byteBuffer.getDouble)
     //println(byteBuffer.getFloat)
     //println(byteBuffer.getLong)

     println(EntityTypes.Player.id)
     println(EntityTypes.Shot.id)
     println(EntityTypes.PowerUp.id)
   }
}