package de.tdng2011.game.visual

import swing._
import de.tdng2011.game.kernel._
import java.awt.{Color, Graphics2D, Graphics}
import math._

object CrystalBall {
	
	val lineLength = 10
	
	var curMonsterStates : List[Monster] = List[Monster]()
	
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
					drawMonster(g, monsterState.pos , monsterState.dir)
				}
			}
		}
		centerOnScreen
		visible_=(true)
	}
	
	def drawMonster (g :Graphics2D, pos : Vec2, rot : Double) {
		
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
		
		g.drawLine(x1, y1, x3, y3)
		
		g.drawLine(x1, y1, x2, y2)
	}
	
	def setAllMonsters(w : World) {
		curMonsterStates = w.monsters
		frame repaint
	}
	
}
