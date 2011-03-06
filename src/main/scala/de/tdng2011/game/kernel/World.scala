package de.tdng2011.game.kernel

import actors.Actor
import Actor.State._
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
  var publicIds = 0
  var entityDescriptions : IndexedSeq[EntityDescription] = IndexedSeq()

  var entityList : IndexedSeq[Entity] = for(x <- 1 to 5) yield newPlayer
  entityList(2) !! PlayerActionMessage(true,false,true,true)
  entityList(1) !! PlayerActionMessage(false,false,true,true)

  var nameMap = Map[Long, String]()

  def act = {
    loop{
      react{
        case x : ThinkMessage => {
          entityList = entityList.filter(_.getState != Terminated)
          val thinkResults : IndexedSeq[Future[Any]] = for(p <- entityList) yield p !! ThinkMessage(x.time, entityList)
          entityDescriptions = for(x <- thinkResults) yield x.apply.asInstanceOf[Option[EntityDescription]].get
          CollisionHandler.handleCollisions(entityDescriptions)
          ConnectionHandler.event(entityDescriptions)
          reply {None}
        }

        case x : RemoveShotFromWorldMessage =>
          entityList = entityList.filter(_ != x.shot)

        case x : RemovePlayerFromWorldMessage =>
          entityList = entityList.filter(_ != x.player)
          ConnectionHandler.event(PlayerRemovedMessage(x.player))

        case x : AddPlayerMessage => {
          val player = newPlayer
          entityList = entityList :+ player
          nameMap = nameMap + (player.publicId -> x.name)
          ConnectionHandler.event(PlayerAddedMessage(player.publicId, x.name))
          reply { Some(player) }
        }

        case x : ShotCreatedMessage => {
          entityList = entityList :+ x.shot
          reply { Some(x.shot) }
        }

        case barbraStreisand => {
          println("[world] wuhuhuhu barbra streisand: " + barbraStreisand)
          reply {None}
        }
      }
    }
  }

  def nextPublicId = { publicIds+=1; publicIds }
  def newPlayer = new Player(Vec2(new Random().nextInt(500), new Random().nextInt(499)), nextPublicId).start.asInstanceOf[Player]
}
