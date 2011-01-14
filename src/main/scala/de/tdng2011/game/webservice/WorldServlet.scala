package de.tdng2011.game.webservice

import de.tdng2011.game.kernel.Action
import de.tdng2011.game.kernel.Monster
import java.io.InputStreamReader
import java.io.BufferedReader
import de.tdng2011.game.kernel.Game
import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServlet
import com.twitter.json.Json
import de.tdng2011.game.visual.CrystalBall

class WorldServlet extends HttpServlet {

	override def service(request : HttpServletRequest, response : HttpServletResponse) {
    while(true){
      Thread.sleep((1.0 / Game.framesPerSecond * 1000.0).toLong)
      response.getWriter.println(Json.build(Game.getWorld))
      response.flushBuffer
    }
	}
}