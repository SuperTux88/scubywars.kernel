package de.tdng2011.game.kernel

import scala.math.Pi
class Player(pos : Vec2, publicId : Long) extends Entity(pos, publicId) {

  var direction = 2.0d
  var radius = 15
  var speed = 100 // m/s
	val rotSpeed =2*Pi //rad/s
  val entityType = EntityTypes.Player

  val turnLeft = false
  val turnRight = true
  val thrust = true
  val fire = false

  think {
    case ('think, time : Double) => {
      if (turnLeft) direction -= (time * rotSpeed)
      if (turnRight) direction += (time * rotSpeed)
      direction %= 2 * Pi
      if (direction < 0)
        direction += 2 * Pi

      // calc new pos
      val len= time * speed
      val step = if (thrust) ahead * len else Vec2(0,0)
      // FIXME: pos=(pos + step).norm
      EntityDescription (pos, publicId, direction, speed, radius, entityType)
    }

    case barbraStreisand  => {
      println("this is the player unknown call received: " + barbraStreisand);
      EntityDescription (pos, publicId, direction, speed, radius, entityType)
    }

  }

  def ahead : Vec2 = Vec2(1, 0).rotate(direction)

}