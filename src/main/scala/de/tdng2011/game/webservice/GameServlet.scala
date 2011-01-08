package de.tdng2011.game.webservice

import de.tdng2011.game.kernel.Game
import javax.swing.JFrame
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServlet
import net.sf.json.JSONArray

class GameServlet extends HttpServlet {
//    protected void service(HttpServletRequest req, HttpServletResponse resp)
	override def service  (request : HttpServletRequest, response : HttpServletResponse) {
		// todo: game !? 'getWorldDescr.
//		val worldDescription = Game.getWorldDescription
//		
//		val monsterStates = JSONArray.fromObject(worldDescription.monsterStates)
		response.getWriter().println("foobar")
		response.flushBuffer()

	}
}