package de.tdng2011.game.visual

import swing._
import de.tdng2011.game.kernel._
import java.awt.{Color, Graphics2D, Graphics}

object CrystalBall extends SimpleSwingApplication {

	override def top = frame
	
	var frame = new MainFrame {
		
		title = "Crystal Ball"
		contents = new Panel {
			focusable = true
			background = Color.white
			preferredSize = new Dimension(500, 500)
	
			override def paint(g: Graphics2D) {
				g.setColor(Color.white)
				g.fillRect(0, 0, size.width, size.height)
				g.setColor(Color.black)
				g.drawString("test", 30, 30)
			}
		}
		centerOnScreen
	}
	
	def initRender(w : World) = render(w.monsters)
	
	def render(monsters : List[Monster]){
		
	}
	
	

}