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
case class ShotCreatedMessage(shot : Shot)
case class RemoveShotFromWorldMessage(shot : Shot)
case class ScoreBoardChangedMessage(scoreBoard : Map[Long, Int])

// NG Messages
case class PlayerKilledMessage(victimPublicId : Long, shotPublicId : Long, killerPublicId : Long, shotPosition : Vec2, victimPosition : Vec2)
case class ShotSpawnedMessage(publicId : Long, parentId : Long, position : Vec2)
case class PlayerSpawnedMessage(publicId : Long, position : Vec2)
case class PlayerCollisionMessage(player1PublicId : Long, player2PublicId : Long, player1Position : Vec2, player2Position : Vec2)
case class ShotCollisionMessage(shot1PublicId : Long,  shot2PublicId : Long, s1Position : Vec2, s2Position : Vec2)