package de.tdng2011.game.kernel

import scala.math._
import com.twitter.json.JsonSerializable

case class Vec2(x:Double,y:Double) extends JsonSerializable {
	private def fix(a:Double) = ((a%WorldDefs.size)+WorldDefs.size)%WorldDefs.size
	
	def norm = Vec2( fix(x), fix(y))
	
	def +(v:Vec2)  = Vec2(x+v.x,y+v.y)
	def -(v:Vec2) = Vec2(x-v.x,y-v.y)
	def *(v:Vec2) = x*v.x + y*v.y
	def *(n:Double) = Vec2(x*n,y*n)
	def rotate(rad:Double) = Vec2( x*cos(rad) - y*sin(rad) , x*sin(rad) + y*cos(rad))
	
	def length = sqrt(x*x+y*y)
	
	def toJson() = "{\"x\":" + x + ",\"y\":" + y+ "}"
	
	override def toString = "("+x+","+y+")"
}