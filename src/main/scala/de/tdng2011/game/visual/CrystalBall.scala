package de.tdng2011.game.visual

import swing._
import de.tdng2011.game.kernel._
import java.awt.{Color, Graphics2D, Graphics, Font}
import math._
import scala.actors.threadpool._

object CrystalBall extends Runnable {
	
	val lineLength = 20
	
	var curMonsterStates : List[Monster] = List[Monster]()
	
	val queue : LinkedBlockingQueue[List[Monster]] = new LinkedBlockingQueue[List[Monster]]()
	
	var frame = new MainFrame {
		
		title = "Crystal Ball"
		contents = new Panel {
			focusable = true
			background = Color.white
			preferredSize = new Dimension(WorldDefs.size , WorldDefs.size)
			
			override def paint(g: Graphics2D) {
				g.setColor(Color.white)
				g.fillRect(0, 0, size.width, size.height)
				g.setColor(Color.black)
				for (monsterState <- curMonsterStates) {
					drawMonster(g, monsterState.pos , monsterState.dir, monsterState.name)
				}
			}
		}
		centerOnScreen
		resizable_=(false)
		visible_=(true)
	}
	
	def drawMonster (g :Graphics2D, pos : Vec2, rot : Double, name : String) {
		
		val ahead=Vec2u(1,0).rotate(rot)
		val posU = Vec2u(pos.x,pos.y)
		val posPeak = posU + ahead * lineLength
		
		val aheadLeft = Vec2u(1,0).rotate(rot+sin(60)+Pi)
		val aheadRight = Vec2u(1,0).rotate(rot-sin(60)+Pi)
		
		val posLeft = posPeak + aheadLeft * lineLength
		val posRight = posPeak + aheadRight * lineLength
		
		val x1 = posPeak.x.toInt
		val y1 = posPeak.y.toInt
		
		val x2 = posRight.x.toInt
		val y2 = posRight.y.toInt
		
		val x3 = posLeft.x.toInt
		val y3 = posLeft.y.toInt
		
		val oldFont = g.getFont
		
		g.setColor(Color.RED)
		g.setFont(new Font("Arial", Font.PLAIN, 20))
		g.drawString(name, x1+20, y1+20)
		
		g.setColor(Color.BLACK)
		g.setFont(oldFont)
		g.drawLine(x1, y1, x3, y3)
		g.drawLine(x1, y1, x2, y2)
	}
	
	def setAllMonsters(w : World) {
		queue clear()
		var monList1 = List[Monster]()
		var monList2 = List[Monster]()
		
		for(monster <- w.monsters) {
			var mon1 = new Monster(monster.name,
			        Vec2(monster.pos.x/3,monster.pos.y/3),
			        monster.dir ,	
			        monster.score ,
			        monster.publicId ,
			        monster.ip ,
			        monster.turnLeft ,monster.turnRight,monster.thrust,monster.fire,monster.isShot,monster.parentId,monster.age)
			monList1::=mon1
				var mon2 = new Monster(monster.name,
			        Vec2(monster.pos.x*2/3,monster.pos.y*2/3),
			        monster.dir,
			        monster.score ,
			        monster.publicId ,
			        monster.ip ,
			        monster.turnLeft ,monster.turnRight,monster.thrust,monster.fire,monster.isShot,monster.parentId,monster.age)
			monList2::=mon2
		}
		queue add (monList1)
		queue add (monList2)
		queue add (w.monsters)
	}

	override def run {
		while(true) {
			curMonsterStates = queue take()
			frame.repaint()
		}
}
	
}
