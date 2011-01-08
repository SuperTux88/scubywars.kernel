package de.tdng2011.game.kernel

import scala.math._

case class Vec2u(x:Double,y:Double) {
	
	def +(v:Vec2u) = Vec2u(x+v.x,y+v.y)
	def -(v:Vec2u) = Vec2u(x-v.x,y-v.y)
	def *(v:Vec2u) = x*v.x + y*v.y
	def *(n:Double) = Vec2u(x*n,y*n)
	def rotate(rad:Double) = Vec2u( x*cos(rad) - y*sin(rad) , x*sin(rad) + y*cos(rad))
	
	override def toString = "("+x+","+y+")"
}