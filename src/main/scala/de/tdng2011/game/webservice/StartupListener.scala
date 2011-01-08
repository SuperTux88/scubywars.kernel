package de.tdng2011.game.webservice

import javax.swing.JFrame
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
class StartupListener extends ServletContextListener {
	var jFrame : JFrame = _
	
	
	def contextDestroyed(event : ServletContextEvent){
		
	}
	
	def contextInitialized(event : ServletContextEvent) {
		new Thread(new GameThread).start
	}
}