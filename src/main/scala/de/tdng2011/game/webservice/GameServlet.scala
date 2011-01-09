package de.tdng2011.game.webservice

import java.io.InputStreamReader
import java.io.BufferedReader
import de.tdng2011.game.kernel.Game
import javax.swing.JFrame
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServlet
import com.twitter.json.Json

class GameServlet extends HttpServlet {

	override def service(request : HttpServletRequest, response : HttpServletResponse) {
		
		val path = request getPathInfo()
		if (path != null) {
			val name = path substring 1
			var opt = Game.getWorld.findMonsterByName(name)
			if (opt.isEmpty) {
				Game.createMonster(name, request.getRemoteAddr())
				opt = Game.getWorld.findMonsterByName(name)
			}
			
			val monster = opt get
			
			println(getBody(request))
		}
		
		response.getWriter().println(Json.build(Game.getWorld))
		response.flushBuffer()
	}
	
	def getBody(request : HttpServletRequest) {
		val stringBuilder = new StringBuilder
		var bufferedReader : BufferedReader = null
		try {
			val inputStream = request.getInputStream
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				val charBuffer = new Array[Char](128)
				var bytesRead = bufferedReader.read(charBuffer);
				while (bytesRead > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
					bytesRead = bufferedReader.read(charBuffer);
				}
			}
		} finally {
			if (bufferedReader != null) {
				bufferedReader close
			}
		}
		stringBuilder.toString
	}
}
