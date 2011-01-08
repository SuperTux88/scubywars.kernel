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
    
    var lastSleepTime : Double = 0

    while (true) {
      val frameStart = getTime
      Game.monsterAction(m1, false, false, false, false)
      Game.monsterAction(m2, false, true, true, false)
      Game.monsterAction(m3, true, true, true, true)
      Game.monsterAction(m4, false, false, true, false)     
      Game.monsterAction(m5, false, false, true, true)
      Game.monsterAction(m6, false, false, true, false)
      Game think(lastSleepTime/1000)
      
      
      val world : World = Game.getWorld
      
      CrystalBall setAllMonsters world
      val frameEnd = getTime
      val sleepTime =  frameDuration - (frameEnd - frameStart)
      if(sleepTime > 0){
    	  Thread sleep(sleepTime)
    	  lastSleepTime = sleepTime
      } else {
    	  lastSleepTime = 0
      }
      
      
    }
  }
	
	def getTime = new Date().getTime
}
