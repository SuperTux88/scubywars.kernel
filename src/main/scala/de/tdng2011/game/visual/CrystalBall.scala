package de.tdng2011.game.visual

import java.io.File
import javax.imageio.ImageIO
import swing._
import de.tdng2011.game.kernel._
import java.awt.{Color, Graphics2D, Graphics, Font, Toolkit}
import math._
import scala.actors.threadpool._

object CrystalBall extends Runnable {
	
	val lineLength = WorldDefs.monsterRadius * 2 
	
	var currentMonsterList : List[Monster] = List[Monster]()
	
	var exploded : scala.collection.mutable.Queue[(Int, Int, Int)] = scala.collection.mutable.Queue[(Int,Int, Int)]()
	val explodDuration = 30
	
	
	var frame = new MainFrame {
		
		
		val url = getClass().getClassLoader().getResource("/background.jpg")
		val bgImage = ImageIO.read(new File(url.getFile))
		

		
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
				g.drawImage(bgImage, 0, 0, null);

				
				currentMonsterList = currentMonsterList.sort(_.getWeight > _.getWeight)
				
				var i : Int = 1
				for (monster <- currentMonsterList) { 
					println(monster)
					drawMonster(g, monster.pos , monster.dir, monster.name, monster.isShot)
					
					if(!monster.isShot) {
						val oldFont = g.getFont
		
						g.setFont(new Font("Arial", Font.PLAIN, 20))
						
						g.drawString(monster.name + " "+ monster.score , WorldDefs.size + 60, i * 25)
						g.setFont(font)
						i+=1
					}
					
					val died : (Boolean,Vec2) = monster.died
					println(died)
					if(died._1){
						exploded.enqueue((died._2.x.toInt,died._2.y.toInt,0))
					}
					
				}
				
				var tmpList : List[(Int,Int,Int )] = List[(Int,Int,Int)]()
					
				Iterator.iterate(exploded) { qi =>
				  val (x,y,s) = qi.dequeue
				  drawExplosion(g, Vec2(x,y),s)
				  if(s < explodDuration) tmpList::=((x,y,s+1))
				  qi
				}.takeWhile(! _.isEmpty).foreach(identity)
				
				for(tupel <- tmpList)
					exploded.enqueue(tupel)
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
		}	else {
			g.setColor(Color.RED)
			g.fillOval(pos.x.toInt - (WorldDefs.shotRadius/2), pos.y.toInt - (WorldDefs.shotRadius/2), WorldDefs.shotRadius*2, WorldDefs.shotRadius*2)
		}
	}
	
	def drawExplosion(g : Graphics2D, pos :  Vec2, step : Int){
		
		val ahead=Vec2(1,0)
		var startPos = pos
		var endPos = Vec2(0,0)
		
		for( i  <- 1 to 8){
			val ahead=Vec2(1,0).rotate((Pi/4)*i)
			startPos = pos + ahead * (step/explodDuration)
			endPos = pos + ahead * (lineLength / 2)
			g.setColor(Color.YELLOW)
			g.drawLine(startPos.x.toInt, startPos.y.toInt, endPos.x.toInt, endPos.y.toInt)
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
