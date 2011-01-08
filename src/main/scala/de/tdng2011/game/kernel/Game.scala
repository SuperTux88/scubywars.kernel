package de.tdng2011.game.kernel

import scala.math._

case class Game {
	private var world = new World
	private var worldDescription : WorldDescription = _
	def createMonster(name:String, ip:String) : String = {
		val id=IdGen.getNext
		world.add(
			new Monster(name,
			        Vec2(WorldDefs.size*random,WorldDefs.size*random).norm,
			        random*2*Pi,
			        0,
			        id,
			        ip,
			        false,false,false,false))
		id
	}
			        
	def think(time:Double){
		worldDescription = world.think(time) // time is in seconds. NOT millisceconds
	}
	
	def getWorldDescription = worldDescription
	
	def monsterAction(id:String, turnLeft:Boolean, turnRight:Boolean, thrust:Boolean, fire:Boolean) { //throws an exception if not found
		val m=world.findMonster(id).get
		m.turnLeft  = turnLeft
		m.turnRight = turnRight
		m.thrust    = thrust
		m.fire      = fire
	}
	
	
	
	override def toString = world.toString
	
}