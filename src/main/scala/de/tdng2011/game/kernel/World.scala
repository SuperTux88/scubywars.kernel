package de.tdng2011.game.kernel

case class World(monsters : List[Monster]) {
	
	def add(m:Monster) = World(m::monsters)
	
	def findMonster(id:String) = monsters.find( _.publicId == id )		
	
	def think(time:Double) = {
		val w1=World(for (m<-monsters) yield m.think(time))
		
		for (a <- monsters)
			for (b <- monsters)
				if ((a.pos - b.pos).length < WorldDefs.monsterSize*2)
					if (a.publicId != b.publicId)
						printf("bang"+a.name+" "+b.name)
		w1
	}
	
	def updateMonster (monster : Monster) : World = {
		World(
			for(m <- monsters)
				yield if(m.publicId == monster.publicId) monster else m 
		)
		
	}
	
	override def toString = "Monsters:\n" + monsters
}