package de.tdng2011.game.kernel

import de.tdng2011.game.util.ByteUtil

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/19/11
 * Time: 2:43 AM
 * To change this template use File | Settings | File Templates.
 */

class Shot(startDirection : Float, startPos : Vec2, publicId : Long, val parentId : Long) extends Entity(startPos, publicId) {

  protected var direction = startDirection
  protected var radius = 5.shortValue
  protected var speed = 400.shortValue // m/s
  protected val lifeTime : Float = WorldDefs.size/speed.asInstanceOf[Float]*0.5f
  protected val entityType = EntityTypes.Shot

  think {

    case x : ThinkMessage => {
      updatePosition(x)
      getEntityDescription
    }

    case x => {
      println("nen dickes rohr")
      None
    }
  }

   private def updatePosition(x : ThinkMessage) {
      val len = x.time * speed
      val step = ahead * len.floatValue
      pos=(pos + step).norm
  }

  private def getEntityDescription = {
    val bytes = ByteUtil.toByteArray(entityType.id.shortValue, publicId, pos.x, pos.y, direction, radius, speed, parentId, lifeTime)
    Some(EntityDescription (pos, publicId, direction, speed, radius, entityType,this,bytes))
  }

  private def ahead : Vec2 = Vec2(1, 0).rotate(direction)

}