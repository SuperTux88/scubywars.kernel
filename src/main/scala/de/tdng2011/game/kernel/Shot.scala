package de.tdng2011.game.kernel

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/19/11
 * Time: 2:43 AM
 * To change this template use File | Settings | File Templates.
 */

class Shot(var direction : Double, startPos : Vec2, publicId : Long) extends Entity(startPos, publicId) {
  var radius = 5
  var speed = 400 // m/s
  val lifeTime = WorldDefs.size/speed*0.5
  val entityType = EntityTypes.Shot

  think {
    case x => {
      println("stupid nothing doing shot impl")
      Some(EntityDescription (pos, publicId, direction, speed, radius, entityType,this))
    }
  }
}