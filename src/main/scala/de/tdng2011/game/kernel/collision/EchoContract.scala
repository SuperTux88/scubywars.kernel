package de.tdng2011.game.kernel.collision

import de.tdng2011.game.library.util.ScubywarsLogger

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 23.01.11
 * Time: 02:15
 * To change this template use File | Settings | File Templates.
 */

object EchoContract extends Contract with ScubywarsLogger {
  onCollide {
    case x => {
      logger.trace("EchoContract executed for: " +x)
    }
  }
}