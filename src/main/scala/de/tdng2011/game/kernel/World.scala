package de.tdng2011.game.kernel

import com.twitter.json.Json
import com.twitter.json.JsonSerializable
import actors.Actor

abstract class Msg
case object Die extends Msg
case object Looser extends Msg
case object Winner extends Msg
case object Nop extends Msg
	

class World(var monsters : List[Actor]) extends Actor {


  def act() {
    loop {
      react {
        case 'think => {
          think()
        }

        case ('add, monster : Actor) => {
          monsters = monster::monsters
        }
      }
    }
  }

	def add(m:Monster) = World(m::monsters)
	
	def findMonster(id:String) = monsters.find( _.publicId == id )
	
	def findMonsterByName(name:String) = monsters.find(_.name == name)
	
	private def think(seconds:Double) = {
		var msgBox=Map[String,Msg]()
		for (a <- monsters)
			for (b <- monsters)
				if ((a.pos - b.pos).length < a.getSize + b.getSize)
					if (a.publicId != b.publicId) 
						if (! (a.publicId == b.parentId ))
							if (!(b.publicId == a.parentId)) {
						if ( a.isShot && !b.isShot )  msgBox=msgBox ++ Map[String,Msg](a.parentId -> Winner()) 
						if (!a.isShot &&  b.isShot )  msgBox=msgBox ++ Map[String,Msg](b.parentId -> Winner()) 
						
						msgBox=msgBox ++ Map[String,Msg](a.publicId -> Looser())
						msgBox=msgBox ++ Map[String,Msg](b.publicId -> Looser())
					}
						
		for (m<-monsters) m ! ('think,seconds,this,msgBox)

	}
	
	def findShotFrom(id:String) = !monsters.find(_.isShotFrom(id)).isEmpty

	override def toString = "Monsters:\n" + monsters
}
