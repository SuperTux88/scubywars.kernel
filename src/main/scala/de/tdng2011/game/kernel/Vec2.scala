package de.tdng2011.game.kernel

import scala.math._

case class Vec2(x:Double,y:Double) {
	private def fix(a:Double) = ((a%WorldDefs.size)+WorldDefs.size)%WorldDefs.size
	
	def norm = Vec2( fix(x), fix(y))
	def +(v:Vec2) = Vec2(x+v.x,y+v.y).norm
	def -(v:Vec2) = Vec2(x-v.x,y-v.y).norm
	def *(v:Vec2) = x*v.x + y*v.y
	def *(n:Double) = Vec2(x*n,y*n).norm
	def rotate(rad:Double) = Vec2( x*cos(rad) - y*sin(rad) , x*sin(rad) + y*cos(rad)).norm
	
	override def toString = "("+x+","+y+")"
}