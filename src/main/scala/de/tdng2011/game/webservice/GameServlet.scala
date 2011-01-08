package de.tdng2011.game.webservice

import javax.swing.JFrame
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServlet

class GameServlet extends HttpServlet {
//    protected void service(HttpServletRequest req, HttpServletResponse resp)
	override def service  (request : HttpServletRequest, response : HttpServletResponse) {
		response.getWriter().println("hallo welt")
		response.flushBuffer()
	}
}