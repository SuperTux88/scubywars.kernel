package de.tdng2011.game.kernel

import scala.math.Pi
class Player(startPos : Vec2, publicId : Long) extends Entity(startPos, publicId) {

  var direction = 2.0d
  var radius = 15
  var speed = 100 // m/s
	val rotSpeed =2*Pi //rad/s
  val entityType = EntityTypes.Player

  var turnLeft = false
  var turnRight = false
  var thrust = false
  var fire = false

  think {
    case x : ThinkMessage => {
      if (turnLeft) direction -= (x.time * rotSpeed)
      if (turnRight) direction += (x.time * rotSpeed)
      direction %= 2 * Pi
      if (direction < 0)
        direction += 2 * Pi

      // calc new pos
      val len= x.time * speed
      val step = if (thrust) ahead * len else Vec2(0,0)
      pos=(pos + step).norm
      Some(EntityDescription (pos, publicId, direction, speed, radius, entityType))
    }

    case x : PlayerActionMessage => {
      println("player action received")
      turnLeft = x.turnLeft
      turnRight = x.turnRight
      fire = x.fire
      thrust = x.thrust
      None
    }

    case barbraStreisand  => {
      println("this is the player unknown call received: " + barbraStreisand);
      None
    }

  }

  def ahead : Vec2 = Vec2(1, 0).rotate(direction)


}