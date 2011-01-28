package de.tdng2011.game.kernel

import scala.math._

case class Vec2(x:Float,y:Float) {
	private def fix(a:Float) = ((a%World.size)+World.size)%World.size
	private def fix2(a:Float) = if (a < - World.size /2 ) a+World.size else if (a > World.size /2 ) a-World.size else a
	
	def norm = Vec2( fix(x), fix(y))

  /**
   * if you want to get the shortest vector between a and b then use (a-b).normDelta
   */
	def normDelta = Vec2( fix2(x), fix2(y))

  /**
   * a dist b returns the shortest distance between a and b
   */
	def dist(v:Vec2) : Float = (this - v).normDelta.length.floatValue
	
	def +(v:Vec2) = Vec2(x+v.x,y+v.y)
	def -(v:Vec2) = Vec2(x-v.x,y-v.y)
	def *(v:Vec2) = x*v.x + y*v.y
	def *(n:Float) = Vec2(x*n,y*n)
	def cross(v:Vec2) : Float = x*v.y - v.x*y   // only the z.component of the cross product
	def rotate(rad:Float) = Vec2((x*cos(rad) - y*sin(rad)).floatValue, (x*sin(rad) + y*cos(rad)).floatValue)
	
	def length = sqrt(x*x+y*y)
	
	override def toString = "("+x+","+y+")"
}