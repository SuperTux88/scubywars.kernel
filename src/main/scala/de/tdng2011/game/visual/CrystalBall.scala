package de.tdng2011.game.visual

import swing._
import de.tdng2011.game.kernel._
import java.awt.{Color, Graphics2D, Graphics, Font}
import math._
import scala.actors.threadpool._

object CrystalBall extends Runnable {
	
	val lineLength = WorldDefs.monsterSize 
	
	var currentMonsterList : List[Monster] = List[Monster]()
	
	var frame = new MainFrame {
		
		title = "Crystal Ball"
		var mainPanel = new Panel() {
			focusable = true
			background = Color.black
			preferredSize = new Dimension(WorldDefs.size + 400, WorldDefs.size)
		}
		
		var graphicsPanel =	new Panel {
			focusable = true
			background = Color.white
			preferredSize = new Dimension(WorldDefs.size + 300, WorldDefs.size)
			//bounds_=(new Rectangle(0,0,WorldDefs.size + 100, WorldDefs.size))
			
			override def paint(g: Graphics2D) {
				g.setColor(Color.black)
				g.fillRect(0, 0, size.width, size.height)
				
				g.setColor(Color.white)
				g.drawRect(0, 0, WorldDefs.size, WorldDefs.size-1)
				
				var i : Int = 0
				for (monster <- currentMonsterList) { 
					drawMonster(g, monster.pos , monster.dir, monster.name, monster.isShot)
					if(!monster.isShot) {
						g.drawString(monster.name + " "+ monster.score , WorldDefs.size + 60, i * 15)
						i+=1
					}
				}
			}
		}
		
		var statsPanel = new Panel() {
			focusable = true
			background = Color.black
			//preferredSize = new Dimension(300, WorldDefs.size)
			bounds_=(new Rectangle(WorldDefs.size + 100,0,300, WorldDefs.size))
		}
		
		//mainPanel.peer.setLayout(null)
		
		mainPanel.peer.add(graphicsPanel.peer)
		mainPanel.peer.add(statsPanel.peer)
		
		contents = graphicsPanel
		
		centerOnScreen
		resizable_=(false)
		visible_=(true)
	}
	
	def drawMonster (g :Graphics2D, pos : Vec2, rot : Double, name : String, isShot : Boolean) {
		if(!isShot) {
		val ahead=Vec2(1,0).rotate(rot)
		val posPeak = pos + ahead * (lineLength / 2)
		
		val aheadLeft = Vec2(1,0).rotate(rot+sin(60)+Pi)
		val aheadRight = Vec2(1,0).rotate(rot-sin(60)+Pi)
		
		val posLeft = posPeak + aheadLeft * lineLength
		val posRight = posPeak + aheadRight * lineLength
		
		val x1 = posPeak.x.toInt
		val y1 = posPeak.y.toInt
		
		val x2 = posRight.x.toInt
		val y2 = posRight.y.toInt
		
		val x3 = posLeft.x.toInt
		val y3 = posLeft.y.toInt
		
		val oldFont = g.getFont
		
		g.setColor(Color.YELLOW)
		g.setFont(new Font("Arial", Font.PLAIN, 20))
		g.drawString(name, x1+20, y1+20)
		
		g.setColor(Color.GREEN)
		g.setFont(oldFont)
		g.drawLine(x1, y1, x3, y3)
		g.drawLine(x1, y1, x2, y2)
		}
		else {
			g.setColor(Color.RED)
			g.fillOval(pos.x.toInt - (WorldDefs.monsterSize/2), pos.y.toInt - (WorldDefs.monsterSize/2), WorldDefs.monsterSize, WorldDefs.monsterSize)
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
