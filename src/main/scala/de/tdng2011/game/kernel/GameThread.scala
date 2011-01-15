package de.tdng2011.game.kernel

import java.util.Date
import de.tdng2011.game.kernel._
import de.tdng2011.game.visual._
import javax.swing.JFrame
class GameThread extends Runnable {

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
		    val frameEnd = getTime   // 8024   / 8050
		    val sleepTime =  (1000.0/Game.framesPerSecond) - (frameEnd - frameStart)
		    if(sleepTime > 0)       // 1       /  -5
		    	Thread sleep(sleepTime.toLong)
		    val afterSleep = getTime      // 8027   / 8050
		    lastSleepTime = afterSleep-frameStart  //27  / 50
	    }
	}
	
	def getTime = System.currentTimeMillis
}