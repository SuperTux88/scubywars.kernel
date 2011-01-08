package de.tdng2011.game.visual

import swing._
import de.tdng2011.game.kernel._
import java.awt.{Color, Graphics2D, Graphics}

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
					val monsterPos = monsterState.pos 
					drawMonster(g, monsterPos, monsterState.dir)
				}
			}
		}
		centerOnScreen
		visible_=(true)
	}
	
	def drawMonster (g :Graphics2D, pos : Vec2, rot : Double) {
		println ("== Position: "+pos)
		println ("== Rotation: "+rot)
		
		val ahead=Vec2(1,0).rotate(rot)
		val pos2 = pos + ahead * lineLength
		
		val x1 = pos.x.toInt
		val y1 = pos.y.toInt
		val x2 = pos2.x.toInt
		val y2 = pos2.y.toInt
				
		println ("==== Positions: " +x1 +" " + y1+" "+ x2+" " +y2)
		
		g.drawLine(x1, y1, x2, y2)
		//drawLine(g, pos, pos2, rot)
	}
	
	def drawLine (g : Graphics2D, from : Vec2, to : Vec2, rot : Double) {
		val ahead = Vec2(1,0).rotate(rot)
		val x1 = from.x.toInt
		val y1 = from.y.toInt
		val x2 = to.x.toInt
		val y2 = to.y.toInt
		
		println ("=========== " +from.x+ " "+ahead.x+ " " + lineLength)
		println ("=========== " +(from.x + ahead.x * lineLength) + " " + WorldDefs.size)
		
		if(from.x + ahead.x * lineLength > WorldDefs.size) {
			println ("1")
		}
		else if (from.y + ahead.y * lineLength > WorldDefs.size) {
			println ("2")
		}
		else if (from.x + ahead.x + lineLength < 0) {
			println ("3")
		}
		else if (from.y + ahead.y * lineLength < 0) {
			println ("4")
		}
		else {
			println ("5")
			g.drawLine(x1, y1, x2, y2)
		}
	}
	
	def setAllMonsters(w : World) {
		curMonsterStates = w.monsters
		frame repaint
	}
	
}
