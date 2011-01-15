package de.tdng2011.game.kernel

import scala.math._
import actors.Actor

object Game {
	private var world = new World(List[Actor]())
  val framesPerSecond = 40.0
	def createMonster(name:String, ip:String) : String = {
		val id=IdGen.getNext
		world = world.add(
			new Monster(name,
			        Vec2(WorldDefs.size*random,WorldDefs.size*random).norm,
			        random*2*Pi,	
			        0,
			        id,
			        ip,
			        Action(false,false,false,false),false,"",0).start)
		id
	}
			        
	def think(time:Double){
		world = world.think(seconds=time)
	}
		
	def monsterAction(id:String, action: Action) { //throws an exception if not found
		val m = world.findMonster(id).get
    m ! action
	}
	
	override def toString = world.toString
	
	def getWorld = world
}