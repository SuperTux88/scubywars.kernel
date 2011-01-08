package de.tdng2011.game.kernel

case class World(monsters : List[Monster]) {
	
	def add(m:Monster) = World(m::monsters)
	
	def findMonster(id:String) = monsters.find( _.publicId == id )		
	
	def think(time:Double) = {
		World(for (m<-monsters) yield m.think(time))
	}
	
	def updateMonster (monster : Monster) : World = {
		World(
			for(m <- monsters)
				yield if(m.publicId == monster.publicId) monster else m 
		)
		
	}
	
	override def toString = "Monsters:\n" + monsters
}