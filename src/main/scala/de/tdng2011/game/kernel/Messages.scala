package de.tdng2011.game.kernel

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/22/11
 * Time: 10:15 PM
 * To change this template use File | Settings | File Templates.
 */

case class ThinkMessage(time : Double)
case class ActorKillMessage()
case class PlayerActionMessage(turnLeft : Boolean, turnRight : Boolean, thrust : Boolean, fire : Boolean)
case class RespawnMessage()
case class AddPointsMessage(points : Int, publicId : Long)