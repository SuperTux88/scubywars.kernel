package de.tdng2011.game.kernel.collision

import actors.Actor
import de.tdng2011.game.kernel.EntityDescription

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/22/11
 * Time: 9:25 PM
 * To change this template use File | Settings | File Templates.
 */

object CollisionHandler {

  val contracts = List(PlayerShotContract, EchoContract, PlayerCollisionContract);

  private def handleCollision(a : Actor, b : Actor){
    contracts.map(_.handleCollision(a,b));
  }

  def handleCollisions(entityDescriptions : IndexedSeq[EntityDescription]){
    var trackedCollisions = Set[(Actor,Actor)]()
    for(a <- entityDescriptions)
        for(b <- entityDescriptions)
          if((a.pos-b.pos).length < a.radius + b.radius && a.publicId != b.publicId && !trackedCollisions.contains((a.actor,b.actor)) && !trackedCollisions.contains((b.actor,a.actor))){
            CollisionHandler.handleCollision(a.actor,b.actor)
            trackedCollisions = trackedCollisions + ((a.actor,b.actor))
          }
  }

}