package de.tdng2011.game.kernel
import scala.math._

case class Monster(
  val name: String,
  val pos: Vec2,
  val dir: Double, // Vec(1,0).rotate(dir) yields direction vector
  val score: Int,
  val publicId: String,
  val ip: String,
  val turnLeft: Boolean,
  val turnRight: Boolean,
  val thrust: Boolean,
  val fire: Boolean) {

  def think(time: Double): Monster = {
    val ahead = Vec2(1, 0).rotate(dir)
    var newDir = dir;
    if (turnLeft) newDir -= (time * WorldDefs.rotSpeed)
    if (turnRight) newDir += time * WorldDefs.rotSpeed
    newDir %= 2 * Pi
    if (newDir < 0)
      newDir += 2 * Pi

    Monster(name,
      if (thrust) pos + ahead * time * WorldDefs.speed else pos,
      newDir,
      score,
      publicId,
      ip,
      turnLeft,
      turnRight,
      thrust,
      fire)

  }

  override def toString = "Monster (" + name + " " + pos + " " + dir + " " + score + " " + publicId + " " + ip + " " + turnLeft + " " + turnRight + " " + thrust + " " + fire + ")"
}
