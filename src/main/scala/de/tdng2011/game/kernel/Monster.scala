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

  def think(time: Double, world:World): List[Monster] = {
    val ahead = Vec2(1, 0).rotate(dir)
    var newDir = dir;
    if (action.turnLeft) newDir -= (time * WorldDefs.rotSpeed)
    if (action.turnRight) newDir += time * WorldDefs.rotSpeed
    newDir %= 2 * Pi
    if (newDir < 0)
      newDir += 2 * Pi

    var ret=List[Monster]()
    
    if (action.fire) {
    	if (!world.findShotFrom(publicId))
    		ret=Monster(name+"_shot",pos + ahead*(WorldDefs.monsterSize*2.1), dir, 0, IdGen.getNext, ip, Action(false,false,true,false),true,publicId,0) :: ret
    }

    var alive = true
    
    if (isShot && age>WorldDefs.shotTimeToLife) alive=false
    
    if (alive) ret=Monster(name,
      if (action.thrust) pos + ahead * time * (if (isShot) WorldDefs.shotSpeed else WorldDefs.speed) else pos,
      newDir,
      score,
      publicId,
      ip,
      action,
      isShot,
      parentId,
      age+time)::ret
      
     ret
	}
  
  	def isShotFrom(id:String) =parentId==id && isShot
 	
	def toJson = "{" + getJsonString("id", publicId) + "," + getJsonString("name", name) + ",\"position\":" + Json.build(pos) + ",\"direction\":" + dir  + ",\"score\":" + score + ",\"actions\":" + Json.build(action) + "}"
	
	private def getJsonString(key : String, value : String) = "\""+key+"\":\""+value+"\""

	override def toString = toJson
}
