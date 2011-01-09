package de.tdng2011.game.kernel

import scala.math._
import com.twitter.json.Json
import com.twitter.json.JsonSerializable

case class Monster (
	name: String,
	pos: Vec2,
	dir: Double, // Vec(1,0).rotate(dir) yields direction vector
	score: Int,
	publicId: String,
	ip: String,
	action: Action,
	isShot: Boolean,
	parentId:String,
	age:Double
)
  		extends JsonSerializable {
	
	def getSize = if(isShot) WorldDefs.shotRadius else WorldDefs.monsterRadius 

	def getWeight = {
		var sum = score * 10000.0
		for(c <- name.toCharArray)
			sum+=c
		sum
	}
	
  def think(time: Double, world:World, msgBox:Map[String,Msg]): List[Monster] = {
    val ahead = Vec2(1, 0).rotate(dir)

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
    val myMsg = msgBox.get(publicId).getOrElse(Nop())
  
    var newScore=score
        
    myMsg match {
    	case Looser() => 
    		if (isShot)
    			alive=false
   			else {
   				newScore-=1
   				newPos   =Vec2(WorldDefs.size*random,WorldDefs.size*random).norm
			    newDir   =random*2*Pi
			    wasdead  =true
   			}
    	case Winner() => newScore+=1
    	case _ => ;
    }
    
    if (isShot && age>WorldDefs.shotTimeToLife) alive=false
          
    if (alive) ret=Monster(name,
      newPos,
      newDir,
      newScore,
      publicId,
      ip,
      action,
      isShot,
      parentId,
      age+time)::ret
      
    if (action.fire) {
    	if (!world.findShotFrom(publicId))
    		ret=Monster(name+"_shot",pos + ahead * (WorldDefs.monsterRadius + WorldDefs.shotRadius) + step, dir, 0, IdGen.getNext, ip, Action(false,false,true,false),true,publicId,0) :: ret
    }

     ret
	}
  
  	def isShotFrom(id:String) =parentId==id && isShot
 	
	def toJson = "{" + getJsonString("id", publicId) + "," + getJsonString("name", name) + ",\"position\":" + Json.build(pos) + ",\"direction\":" + dir  + ",\"score\":" + score + ",\"actions\":" + Json.build(action) + "}"
	
	private def getJsonString(key : String, value : String) = "\""+key+"\":\""+value+"\""

	override def toString = toJson
	
	var wasdead = false
	
	def died : Boolean {
  		val tmp = wasdead
  		if(tmp)wasdead = false
  		tmp
  	}
}
