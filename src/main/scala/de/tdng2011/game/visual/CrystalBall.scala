package de.tdng2011.game.visual

import swing._
import de.tdng2011.game.kernel._
import java.awt.{Color, Graphics2D, Graphics, Font}
import math._
import scala.actors.threadpool._

object CrystalBall extends Runnable {
	
	val lineLength = 20
	
	var currentMonsterList : List[Monster] = List[Monster]()
	
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
				for (monster <- currentMonsterList) 
					drawMonster(g, monster.pos , monster.dir, monster.name, monster.isShot)
				
			}
		}
		centerOnScreen
		resizable_=(false)
		visible_=(true)
	}
	
	def drawMonster (g :Graphics2D, pos : Vec2, rot : Double, name : String, isShot : Boolean) {
		if(!isShot) {
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
		
		g.setColor(Color.BLUE)
		g.setFont(new Font("Arial", Font.PLAIN, 20))
		g.drawString(name, x1+20, y1+20)
		
		g.setColor(Color.BLACK)
		g.setFont(oldFont)
		g.drawLine(x1, y1, x3, y3)
		g.drawLine(x1, y1, x2, y2)
		}
		else {
			g.setColor(Color.RED)
			g.fillOval(pos.x.toInt - 2, pos.y.toInt - 2, 4, 4)
			
		}
	}
	
	override def run {
		while(true) {
			Thread.sleep((1/120.0 * 1000).toLong)
			currentMonsterList = Game.getWorld.monsters
			frame.repaint()
		}
}
	
}
