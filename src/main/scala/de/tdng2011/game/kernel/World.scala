package de.tdng2011.game.kernel

import com.twitter.json.Json
import com.twitter.json.JsonSerializable

case class World(monsters : List[Monster]) extends JsonSerializable {
	
	def add(m:Monster) = World(m::monsters)
	
	def findMonster(id:String) = monsters.find( _.publicId == id )		
	
	def think(time:Double) = {
		val w1=World((for (m<-monsters) yield m.think(time,this)).reduceRight(_:::_))
		
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
	
	def findShotFrom(id:String) = !monsters.find(_.isShotFrom(id)).isEmpty

	def toJson() = "{\"monsters\":" + Json.build(monsters.filter(!_.isShot)) + ",\"shots\":" + Json.build(monsters.filter(_.isShot)) + "}"
	
	override def toString = "Monsters:\n" + monsters
}
