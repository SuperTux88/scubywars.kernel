package de.tdng2011.game.kernel.collision

import actors.Actor

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/22/11
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */

object CollisionHandler {

  val contracts = List(PlayerShotContract);

  def handleCollision(a : Actor, b : Actor){
    contracts.map(_.handleCollision(a,b));
  }
}