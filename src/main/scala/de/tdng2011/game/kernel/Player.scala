package de.tdng2011.game.kernel

import scala.math.Pi
import java.util.Random
import de.tdng2011.game.util.ByteUtil

class Player(startPos : Vec2, publicId : Long) extends Entity(startPos, publicId) {

  protected var direction = 2.0f
  protected var radius = 15.shortValue
  protected var speed = 100.shortValue // m/s
	protected val rotSpeed : Float =2*Pi.floatValue //rad/s
  protected val entityType = EntityTypes.Player

  protected var turnLeft = false
  protected var turnRight = false
  protected var thrust = false
  protected var fire = false

  think {
    case x : ThinkMessage => {
      updatePosition(x)
      getEntityDescription
    }

    case x : PlayerActionMessage => {
      action(x)
      None
    }

    case m : RespawnMessage => {
      respawn
      None
    }

    case barbraStreisand  => {
      None
    }

  }

  def ahead : Vec2 = Vec2(1, 0).rotate(direction)

  private def action(x : PlayerActionMessage){
    turnLeft = x.turnLeft
    turnRight = x.turnRight
    fire = x.fire
    thrust = x.thrust
  }

  private def respawn {
    pos=Vec2(new Random().nextInt(1000), new Random().nextInt(1000))    // TODO Game field size
  }

  private def updatePosition(x : ThinkMessage) {
      if (turnLeft) direction -= (x.time * rotSpeed).floatValue
      if (turnRight) direction += (x.time * rotSpeed).floatValue
      direction %= 2 * Pi.shortValue
      if (direction < 0)
        direction += 2 * Pi.shortValue

      // calc new pos
      val len = x.time * speed
      val step = if (thrust) ahead * len.floatValue else Vec2(0,0)
      pos=(pos + step).norm
  }

  private def getEntityDescription = {
    val bytes = ByteUtil.toByteArray(entityType.id.shortValue, publicId, pos.x, pos.y, direction, radius, speed, rotSpeed, turnLeft, turnRight, thrust, fire)
    Some(EntityDescription (pos, publicId, direction, speed, radius, entityType, this, bytes))
  }
}