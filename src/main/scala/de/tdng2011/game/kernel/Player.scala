package de.tdng2011.game.kernel

import scala.math.Pi
import java.util.Random

class Player(startPos : Vec2, publicId : Long) extends Entity(startPos, publicId) {

  protected var direction = 2.0d
  protected var radius = 15
  protected var speed = 100 // m/s
	protected val rotSpeed =2*Pi //rad/s
  protected val entityType = EntityTypes.Player

  protected var turnLeft = false
  protected var turnRight = false
  protected var thrust = false
  protected var fire = false

  think {
    case x : ThinkMessage => {
      updatePosition(x)
      Some(EntityDescription (pos, publicId, direction, speed, radius, entityType, this))
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
      if (turnLeft) direction -= (x.time * rotSpeed)
      if (turnRight) direction += (x.time * rotSpeed)
      direction %= 2 * Pi
      if (direction < 0)
        direction += 2 * Pi

      // calc new pos
      val len= x.time * speed
      val step = if (thrust) ahead * len else Vec2(0,0)
      pos=(pos + step).norm
  }
}