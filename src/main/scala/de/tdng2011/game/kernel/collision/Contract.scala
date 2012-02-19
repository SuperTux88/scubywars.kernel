package de.tdng2011.game.kernel.collision

import actors.Actor

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/22/11
 * Time: 9:22 PM
 * To change this template use File | Settings | File Templates.
 */

abstract class Contract {

  private var collisionHandler: PartialFunction[Any, Unit] = {
    case _ => {}
  }

  final def handleCollision(a: Actor, b: Actor) = {
    collisionHandler(a, b);
  }

  final def onCollide(handler: PartialFunction[Any, Unit]) {
    collisionHandler = handler;
  }
}