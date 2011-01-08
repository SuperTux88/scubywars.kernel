package de.tdng2011.game.kernel

import com.twitter.json.Json
import com.twitter.json.JsonSerializable

abstract class Msg
case class Die extends Msg
case class Looser extends Msg
case class Winner extends Msg
case class Nop extends Msg
	

case class World(monsters : List[Monster]) extends JsonSerializable {
	
	def add(m:Monster) = World(m::monsters)
	
	def findMonster(id:String) = monsters.find( _.publicId == id )
	
	
	def think(time:Double) = {
		var msgBox=Map[String,Msg]()
		for (a <- monsters)
			for (b <- monsters)
				if ((a.pos - b.pos).length < WorldDefs.monsterSize*2)
					if (a.publicId != b.publicId) 
						if (! (a.publicId == b.parentId ))
							if (!(b.publicId == a.parentId)) {
						
						println("Collision between "+a.name+" "+b.name)
						if ( a.isShot && !b.isShot )  msgBox=msgBox ++ Map[String,Msg](a.parentId -> Winner()) 
						if (!a.isShot &&  b.isShot )  msgBox=msgBox ++ Map[String,Msg](b.parentId -> Winner()) 
						
						msgBox=msgBox ++ Map[String,Msg](a.publicId -> Looser())
						msgBox=msgBox ++ Map[String,Msg](b.publicId -> Looser())
					}
						
		val w1=World((for (m<-monsters) yield m.think(time,this,msgBox)).flatten)
		
		w1
	}
	
	def updateMonster (monster : Monster) : World = {
		World(
			for(m <- monsters)
				yield if(m.publicId == monster.publicId) monster else m 
		)
	}
	
	def findShotFrom(id:String) = !monsters.find(_.isShotFrom(id)).isEmpty

	def toJson = "{\"players\":" + Json.build(monsters.filter(!_.isShot)) + ",\"shots\":" + Json.build(monsters.filter(_.isShot)) + "}"
	
	override def toString = "Monsters:\n" + monsters
}
