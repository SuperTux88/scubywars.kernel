package de.tdng2011.game.kernel.collision

import de.tdng2011.game.kernel.{Player, Shot}
import actors.Actor

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/22/11
 * Time: 9:39 PM
 * To change this template use File | Settings | File Templates.
 */

object TestMain {
  def main(args : Array[String]) {
    println("hello, world!");
    val shot = new Shot(1, null, 1)
    val player = new Player(null, 1)
    CollisionHandler.handleCollision(shot, player)
    CollisionHandler.handleCollision(player,shot)
  }
}