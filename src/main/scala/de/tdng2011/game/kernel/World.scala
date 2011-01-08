package de.tdng2011.game.kernel

class World {
	var monsters=List[Monster]()
	
	def add(m:Monster) : Unit =	monsters::=m
	def findMonster(id:String) = monsters.find( _.publicId == id )		
	def think(time:Double) = {
		for (m<-monsters) m.think(time)
		worldDescription
	}
	
	def worldDescription : WorldDescription = {
		WorldDescription(
				for(monster <- monsters) yield monster getState
		)
	}
	
	override def toString = "Monsters:\n" + monsters
}