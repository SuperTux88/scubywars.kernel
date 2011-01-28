package de.tdng2011.game.kernel

import actors.Actor
import collision.CollisionHandler
import util.Random

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 28.01.11
 * Time: 00:12
 * To change this template use File | Settings | File Templates.
 */

object World extends Actor {

	val size = 1000 //m

  var entityDescriptions : IndexedSeq[EntityDescription] = IndexedSeq()
  var playerList = for(x <- 1 to 5) yield new Player(Vec2(new Random().nextInt(500), new Random().nextInt(499)), x).start
  playerList = playerList :+ new Shot(2,Vec2(10,10), 1337, 1338).start
  playerList(2) !! PlayerActionMessage(true,false,true,false)
  playerList(1) !! PlayerActionMessage(false,false,true,false)

  def act = {
    loop{
      react{
        case x : ThinkMessage => {
          val thinkResults : IndexedSeq[Future[Any]] = for(p <- playerList) yield p !! x
          entityDescriptions = for(x <- thinkResults) yield x.apply.asInstanceOf[Option[EntityDescription]].get
          CollisionHandler.handleCollisions(entityDescriptions)
          ConnectionHandler.event(entityDescriptions)
          reply {None}
        }

        case barbraStreisand => {
          println("[world] wuhuhuhu barbra streisand: " + barbraStreisand)
          reply {None}
        }
      }
    }
  }
}