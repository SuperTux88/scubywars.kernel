package de.tdng2011.game.kernel

import com.twitter.json.JsonSerializable

case class Action(
	turnLeft: Boolean,
	turnRight: Boolean,
	thrust: Boolean,
	fire: Boolean)
		extends JsonSerializable {

	def toJson() = "{\"turnLeft\":" + turnLeft + ",\"turnRight\":" + turnRight + ",\"thrust\":" + thrust + ",\"fire\":" + fire + "}"
	
}