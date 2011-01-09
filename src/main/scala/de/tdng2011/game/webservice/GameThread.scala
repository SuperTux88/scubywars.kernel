package de.tdng2011.game.webservice

import java.util.Date
import de.tdng2011.game.kernel._
import de.tdng2011.game.visual._
import javax.swing.JFrame
class GameThread extends Runnable {
	val frameDuration = 25 
	override def run() {
	    // server start
	    // visualizer start
	    new Thread(CrystalBall).start
	
	    val m1 = Game.createMonster("test1", "8.8.8.8")
	    val m2 = Game.createMonster("fun", "127.0.0.1")
	    
	    val m3 = Game.createMonster("felix", "123.123.123.123")
	    val m4 = Game.createMonster("thorsten", "10.10.10.10")
	    val m5 = Game.createMonster("supert0x", "18.18.18.18")
	    val m6 = Game.createMonster("makubi", "16.16.16.16")
	    val m7 = Game.createMonster("oliverSmart", "127.0.0.17")
	    
	    Game.monsterAction(m1, Action(false, false, false, false))
	    Game.monsterAction(m2, Action(false, true, true, true))
	    Game.monsterAction(m3, Action(true, true, true, true))
	    Game.monsterAction(m4, Action(false, false, true, true))
	    Game.monsterAction(m5, Action(false, false, true, true))
	    Game.monsterAction(m6, Action(false, false, true, true))
	    
	    var lastSleepTime : Double = 0
	
	    while (true) {
		    val frameStart = getTime  // 8000
		    
		    Game think(lastSleepTime/1000.0)
		    
		    val world : World = Game.getWorld
		    
		    val players = world.monsters.filter(!_.isShot )
		    for (m <- players.filter(_.name != "fun")){
		    	var turnLeft=false
		    	var turnRight=false
		    	
		    	val self = m
		    	
		    	def compare(a : Monster, b: Monster) = (a.pos dist self.pos) < (b.pos dist self.pos)
		    	
		    	val sortedList=players.sort( compare )
		    	val enemy = sortedList(2)
		    	
		    	//println("hunting "+enemy.name+" enemy.pos="+enemy.pos)
		    	
		    	val ahead : Vec2 = self.ahead
		    	val lineOfSight : Vec2 = (enemy.pos - self.pos).normDelta
		    	val crossProduct = ahead.cross(lineOfSight)
		    	
		    	if (crossProduct < 0) turnLeft =true
		    	if (crossProduct > 0) turnRight=true
		    	
		    	Game.monsterAction(m.publicId, Action(turnLeft, turnRight, true, true))
		    }
		    
		    val frameEnd = getTime   // 8024   / 8050
		    val sleepTime =  frameDuration - (frameEnd - frameStart)
		    if(sleepTime > 0)       // 1       /  -5
		    	Thread sleep(sleepTime)
		    
		    val afterSleep = getTime      // 8027   / 8050
		    lastSleepTime = afterSleep-frameStart  //27  / 50
	    }
	}
	
	def getTime = System.currentTimeMillis
}
