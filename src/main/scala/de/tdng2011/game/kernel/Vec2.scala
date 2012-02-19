package de.tdng2011.game.kernel

import scala.math._
import de.tdng2011.game.library.util.Vec2

case class Vec2Internal(v:Vec2) extends Vec2(v.x, v.y) {
  private def fix(a:Float) = ((a%World.size)+World.size)%World.size
  private def fix2(a:Float) = if (a < - World.size /2 ) a+World.size else if (a > World.size /2 ) a-World.size else a
	
  def norm = Vec2(fix(x), fix(y))

  /**
   * if you want to get the shortest vector between a and b then use (a-b).normDelta
   */
  def normDelta = Vec2(fix2(x), fix2(y))

  /**
   * a dist b returns the shortest distance between a and b
   */
  def dist(v:Vec2) : Float = Vec2Internal(this - v).normDelta.length.floatValue
}