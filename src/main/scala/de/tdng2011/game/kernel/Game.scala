package de.tdng2011.game.kernel

import scala.math._

object Game {
	private var world = new World(List())

	def createMonster(name:String, ip:String) : String = {
		val id=IdGen.getNext
		world = world.add(
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
		world = world.think(time) // time is in seconds. NOT millisceconds
	}
		
	def monsterAction(id:String, turnLeft:Boolean, turnRight:Boolean, thrust:Boolean, fire:Boolean) { //throws an exception if not found
		val m = world.findMonster(id).get
		world = world.updateMonster(Monster(m.name, m.pos, m.dir, m.score, m.publicId, m.ip, turnLeft, turnRight, thrust, fire))
	}
	
	override def toString = world.toString
	
	def getWorld = world
}