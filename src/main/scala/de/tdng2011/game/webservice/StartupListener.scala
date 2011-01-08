package de.tdng2011.game.webservice

import javax.swing.JFrame
import javax.servlet.ServletContextEvent
import javax.servlet.ServletContextListener
class StartupListener extends ServletContextListener {
	var jFrame : JFrame = _
	
	
	def contextDestroyed(event : ServletContextEvent){
		
	}
	
	def contextInitialized(event : ServletContextEvent) {
		// server start
		// visualizer start
		jFrame = new JFrame
		jFrame setBounds(640, 480, 640, 480)
		jFrame setVisible true
		jFrame setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
		
	}
}