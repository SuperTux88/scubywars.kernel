package de.tdng2011.game.kernel

import scala.math._
import com.twitter.json.JsonSerializable

case class Vec2(x:Double,y:Double) extends JsonSerializable {
	private def fix(a:Double) = ((a%WorldDefs.size)+WorldDefs.size)%WorldDefs.size
	private def fix2(a:Double) = if (a < - WorldDefs.size /2 ) a+WorldDefs.size else if (a > WorldDefs.size /2 ) a-WorldDefs.size else a
	
	def norm = Vec2( fix(x), fix(y))
	def normDelta = Vec2( fix2(x), fix2(y))  // if you want to get the shortest vector between a and b then use (a-b).normDelta
	
	def dist(v:Vec2) : Double= (this - v).normDelta.length // a dist b returns the shortest distance between a and b
	
	def +(v:Vec2)  = Vec2(x+v.x,y+v.y)
	def -(v:Vec2) = Vec2(x-v.x,y-v.y)
	def *(v:Vec2) = x*v.x + y*v.y
	def *(n:Double) = Vec2(x*n,y*n)
	def cross(v:Vec2) : Double = x*v.y - v.x*y   // only the z.component of the cross product  
	def rotate(rad:Double) = Vec2( x*cos(rad) - y*sin(rad) , x*sin(rad) + y*cos(rad))
	
	def length = sqrt(x*x+y*y)
	
	def toJson() = "{\"x\":" + x + ",\"y\":" + y+ "}"
	
	override def toString = "("+x+","+y+")"
}

/*

	1 - 999 -> -998 + 1000 -> 2
	
	999 - 1 -> 998 -1000 -> -2 
	
	2 -4 -> -2 
*/