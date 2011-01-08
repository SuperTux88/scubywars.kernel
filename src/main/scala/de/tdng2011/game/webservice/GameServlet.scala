package de.tdng2011.game.webservice

import de.tdng2011.game.kernel.Game
import javax.swing.JFrame
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServlet
import com.twitter.json.Json

class GameServlet extends HttpServlet {

	override def service  (request : HttpServletRequest, response : HttpServletResponse) {
		
		val path = request getPathInfo()
		if (path != null) {
			val name = path substring 1
			var opt = Game.getWorld.findMonsterByName(name)
			if (opt.isEmpty) {
				Game.createMonster(name, request.getRemoteAddr())
				opt = Game.getWorld.findMonsterByName(name)
			}
			
			val monster = opt get
		}
		
		response.getWriter().println(Json.build(Game.getWorld))
		response.flushBuffer()
	}
}
