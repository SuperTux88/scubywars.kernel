package de.tdng2011.game.kernel

import scala.math.Pi
import java.util.Random
import de.tdng2011.game.library.util.ByteUtil
import de.tdng2011.game.library.EntityTypes
import actors.Actor
import Actor.State._

class Player(startPos : Vec2, publicId : Long) extends Entity(startPos, publicId) {

  protected val entityType = EntityTypes.Player

  direction = Player.defaultDirection
  radius    = Player.defaultRadius
  speed     = Player.defaultSpeed // m/s

	protected val rotSpeed : Float = Player.defaultRotSpeed //rad/s

  protected var turnLeft  = false
  protected var turnRight = false
  protected var thrust    = false
  protected var fire      = false

  protected var shot : Entity = null;

  think {
    case x : ThinkMessage => {
      updatePosition(x)
      createShot
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

  private def createShot {
    if(fire){
      if(shot == null || shot.getState == Terminated){
        val shotPos = pos + ahead * (radius + Shot.defaultRadius)
        shot = new Shot(direction, shotPos, World.nextPublicId, publicId)
        shot.start
        World !! ShotCreatedMessage(shot)
      }
    }
  }

  private def getEntityDescription = {
    val bytes = ByteUtil.toByteArray(entityType, publicId, pos.x, pos.y, direction, radius, speed, rotSpeed, turnLeft, turnRight, thrust, fire)
    Some(EntityDescription (pos, publicId, direction, speed, radius, entityType, this, bytes))
  }
}

object Player {
  val defaultDirection        = 2.0f
  val defaultRadius           = 15.shortValue
  val defaultSpeed            = 100.shortValue // m/s
  val defaultRotSpeed : Float = 2*Pi.floatValue //rad/s
}