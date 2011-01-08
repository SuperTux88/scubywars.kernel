package de.tdng2011.game.kernel
import scala.math._

class Monster(
		val name      :String,
		var pos       :Vec2,
		var dir       :Double, // Vec(1,0).rotate(dir) yields direction vector
		var score     :Int,
		val publicId  :String,
		val ip        :String,
		var turnLeft  :Boolean,
		var turnRight :Boolean,
		var thrust    :Boolean,
		var fire      :Boolean) {
	def think(time:Double) {
		val ahead=Vec2(1,0).rotate(dir)
		if (thrust) pos=pos+ahead*time*WorldDefs.speed
		if (turnLeft)  dir-=(time*WorldDefs.rotSpeed) 
		if (turnRight) dir+=time*WorldDefs.rotSpeed
			
		dir%=2*Pi
		if(dir < 0)
			dir+=2*Pi
	}
	override def toString="Monster ("+name+" "+pos+" "+dir+" "+score+" "+publicId+" "+ip+" "+turnLeft+" "+turnRight+" "+thrust+" "+fire+")"
	
}
		