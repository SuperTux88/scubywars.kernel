package de.tdng2011.game.kernel

import scala.math._
import com.twitter.json.Json
import com.twitter.json.JsonSerializable
import actors.Actor

class Monster (
	var name: String,
	var pos: Vec2,
	var dir: Double, // Vec(1,0).rotate(dir) yields direction vector
	var score: Int,
	var publicId: String,
	var ip: String,
	var action: Action,
	var isShot: Boolean,
	var parentId:String,
	var age:Double
) extends JsonSerializable with Actor {
	
	private def getSize = if(isShot) WorldDefs.shotRadius else WorldDefs.monsterRadius
  private def getType = if(isShot) MonsterType.Shot.id else MonsterType.Player.id
	private def getWeight = {
		var sum = score * 10000.0
		for(c <- name.toCharArray)
			sum+=c
		sum
	}
	
  private def ahead : Vec2 = Vec2(1, 0).rotate(dir)

  def act ={
    react {
      case ('think, time: Double, world:World, msgBox:Map[String,Msg]) => {
        Some(think(time, world, msgBox))
      }

      case ('toJSON) => {
        toJson
      }

      case 'monsterState => {
        MonsterState(pos, dir)
      }

      case _ => {
        println("warning: got invalid message! i don't know what to do know!")
        None
      }


    }
  }
	
  private def think(time: Double, world:World, msgBox:Map[String,Msg]): List[Monster] = {

    // calc new dir
    var newDir = dir;
    if (action.turnLeft) newDir -= (time * WorldDefs.rotSpeed)
    if (action.turnRight) newDir += time * WorldDefs.rotSpeed
    newDir %= 2 * Pi
    if (newDir < 0)
      newDir += 2 * Pi

    // calc new pos
    val len= time * (if (isShot) WorldDefs.shotSpeed else WorldDefs.speed)
    val step = if  (action.thrust) ahead * len else Vec2(0,0)
    var newPos=(pos + step).norm

    // create return list
    var ret=List[Monster]()
    
    var alive = true
    val myMsg = msgBox.get(publicId).getOrElse(Nop)
  
    var newScore=score
        
    myMsg match {
    	case Looser =>
    		if (isShot)
    			alive=false
   			else {
   				newScore-=1
   				newPos   =Vec2(WorldDefs.size*random,WorldDefs.size*random).norm
			    newDir   =random*2*Pi
			    wasdead  =true
			    deadpos  =pos
   			}
    	case Winner => newScore+=2
    	case _ => ;
    }
    
    if (isShot && age>WorldDefs.shotTimeToLife) alive=false

    if (action.fire) {
    	if (!world.findShotFrom(publicId))
    		// TODO send actor message (async): spawn monster
    		//ret=Monster(name+"_shot",pos + ahead * (WorldDefs.monsterRadius + WorldDefs.shotRadius) + step, dir, 0, IdGen.getNext, ip, Action(false,false,true,false),true,publicId,0) :: ret
    }
	}
  
  private def isShotFrom(id:String) =parentId==id && isShot
  private def toJson = "{" + getJsonString("id", publicId) + "," + getJsonString("name", name) + ",\"position\":" + Json.build(pos) + ",\"direction\":" + dir  + ",\"score\":" + score + ",\"actions\":" + Json.build(action) + "}"
	private def getJsonString(key : String, value : String) = "\""+key+"\":\""+value+"\""
	private var wasdead = false
	private var deadpos : Vec2 = Vec2(0,0)
	
	private def died() : (Boolean, Vec2 ) = {
  		val tmp = wasdead
  		if(tmp)wasdead = false
  		(tmp, deadpos)
  	}

  private def toProtocol = {
    pos.toProtocol + ";" + dir + ";" + score + ";" + publicId + ";" + getType + ";"
  }
}
