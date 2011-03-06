package de.tdng2011.game.kernel

import actors.Actor

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/22/11
 * Time: 10:15 PM
 */

case class ThinkMessage(time : Double, entities : IndexedSeq[Entity])
case class PlayerActionMessage(turnLeft : Boolean, turnRight : Boolean, thrust : Boolean, fire : Boolean)
case class RespawnMessage()
case class AddPointsMessage(points : Int, publicId : Long)
case class AddPlayerMessage(name : String)
case class PlayerAddedMessage(publicId : Long, name : String)
case class RemovePlayerFromWorldMessage(player : Player)
case class PlayerRemovedMessage(player : Player)
case class ShotCreatedMessage(shot : Entity)
case class RemoveShotFromWorldMessage(shot : Shot)