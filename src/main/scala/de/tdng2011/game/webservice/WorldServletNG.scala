package de.tdng2011.game.webservice

import javax.servlet.http.HttpServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServlet
import com.twitter.json.Json
import de.tdng2011.game.kernel.{Compressor, Game}

class WorldServletNG extends HttpServlet {

	override def service(request : HttpServletRequest, response : HttpServletResponse) {
    while(true){
      Thread.sleep((1.0 / Game.framesPerSecond * 1000.0).toLong)
      val world = Game.getWorld

      var description = ""
      for(m <- world.monsters){
        description += m.toProtocol
      }

      println("float 0.0: " + 0.0f.byteValue)
      println("float 109815,2052: " + 109815,2052f.byteValue)

      response.getOutputStream.write(Compressor.gZipString(description))
      response.flushBuffer
    }
	}
}