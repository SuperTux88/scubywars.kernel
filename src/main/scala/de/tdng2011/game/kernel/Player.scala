package de.tdng2011.game.kernel

import scala.math.Pi
class Player(pos : Vec2, publicId : Long) extends Entity(pos, publicId) {

  var direction = 2.0d
  var radius = 15
  var speed = 100 // m/s
	val rotSpeed =2*Pi //rad/s

  think {
    case x : String => {
      println("this is the player! received string " + x)
    }

    case y  => {
      println("this is the player unknown call received: " + y);
    }
  }

}