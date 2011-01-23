package de.tdng2011.game.kernel.collision

import actors.Actor

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 23.01.11
 * Time: 02:15
 * To change this template use File | Settings | File Templates.
 */

object EchoContract extends Contract {
  onCollide {
    case x => {
      println("EchoContract executed for: " +x)
    }
  }
}