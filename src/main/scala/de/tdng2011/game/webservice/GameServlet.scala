package de.tdng2011.game.webservice

import de.tdng2011.game.kernel.Game
import javax.swing.JFrame
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServlet
import com.twitter.json.Json

class GameServlet extends HttpServlet {

	override def service  (request : HttpServletRequest, response : HttpServletResponse) {
		response.getWriter().println(Json.build(Game.getWorld))
		response.flushBuffer()
	}
}
