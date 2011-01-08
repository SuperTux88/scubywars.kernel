package de.tdng2011.game.kernel

import scala.math._
import com.twitter.json.Json
import com.twitter.json.JsonSerializable

case class Monster (
	val name: String,
	val pos: Vec2,
	val dir: Double, // Vec(1,0).rotate(dir) yields direction vector
	val score: Int,
	val publicId: String,
	val ip: String,
	val turnLeft: Boolean,
	val turnRight: Boolean,
	val thrust: Boolean,
	val fire: Boolean,
	val isShot: Boolean,
	val parentId:String,
	val age:Double
)
  		extends JsonSerializable {

  def think(time: Double, world:World): List[Monster] = {
    val ahead = Vec2(1, 0).rotate(dir)
    var newDir = dir;
    if (turnLeft) newDir -= (time * WorldDefs.rotSpeed)
    if (turnRight) newDir += time * WorldDefs.rotSpeed
    newDir %= 2 * Pi
    if (newDir < 0)
      newDir += 2 * Pi

    var ret=List[Monster]()
    
    if (fire) {
    	if (!world.findShotFrom(publicId))
    		ret=Monster(name+"_shot",pos + ahead*(WorldDefs.monsterSize*2.1), dir, 0, IdGen.getNext, ip, false,false,true,false,true,publicId,0) :: ret
    }

    var alive = true
    
    if (isShot && age>WorldDefs.shotTimeToLife) alive=false
    
    if (alive) ret=Monster(name,
      if (thrust) pos + ahead * time * (if (isShot) WorldDefs.shotSpeed else WorldDefs.speed) else pos,
      newDir,
      score,
      publicId,
      ip,
      turnLeft,
      turnRight,
      thrust,
      fire,
      isShot,
      parentId,
      age+time)::ret
      
     ret
	}
  
  	def isShotFrom(id:String) =parentId==id && isShot
 	
	def toJson() = "{" + getJsonString("name", name) + ",\"pos\":" + Json.build(pos) + ",\"dir\":" + dir  + ",\"score\":" + score + "," + getJsonString("publicId", publicId) + "," +
		getJsonString("ip", ip) + ",\"turnLeft\":" + turnLeft + ",\"turnRight\":" + turnRight + ",\"thrust\":" + thrust + ",\"fire\":" + fire + "}"
	
	private def getJsonString(key : String, value : String) = "\""+key+"\":\""+value+"\""

	override def toString = "Monster (" + name + " " + pos + " " + dir + " " + score + " " + publicId + " " + ip + " " + turnLeft + " " + turnRight + " " + thrust + " " + fire + ")"
}
