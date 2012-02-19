package de.tdng2011.game.kernel

import de.tdng2011.game.library.EntityTypes
import de.tdng2011.game.library.util.{ScubywarsLogger, ByteUtil, Vec2}

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/19/11
 * Time: 2:43 AM
 * To change this template use File | Settings | File Templates.
 */

class Shot(startDirection : Float, startPos : Vec2, publicId : Long, val parentId : Long) extends Entity(startPos, publicId) with ScubywarsLogger {

  protected val entityType = EntityTypes.Shot

  direction = startDirection
  radius    = Shot.defaultRadius
  speed     = Shot.defaultSpeed // m/s

  private var lifeTime : Float = Shot.defaultLifeTime

  think {
    case x : ThinkMessage => {
      if(lifeTime <= 0) World !! RemoveShotFromWorldMessage(this)
      lifeTime -= x.time.floatValue
      updatePosition(x)
      getEntityDescription
    }

    case x => {
      logger.warn("Received unknown message " + x)
      None
    }
  }

  private def updatePosition(x : ThinkMessage) {
    val len = x.time * speed
    val step = ahead * len.floatValue
    pos=Vec2Internal(pos + step).norm
  }

  private def getEntityDescription = {
    val bytes = ByteUtil.toByteArray(entityType, publicId, pos.x, pos.y, direction, radius, speed, parentId, lifeTime)
    Some(EntityDescription (pos, publicId, direction, speed, radius, entityType,this,bytes))
  }

  private def ahead : Vec2 = Vec2(1, 0).rotate(direction)
}

object Shot {
  val defaultRadius   = 5.shortValue
  val defaultSpeed    = (Player.defaultSpeed * 4).shortValue // m/s
  val defaultLifeTime = World.size/defaultSpeed.asInstanceOf[Float]*0.5f
}