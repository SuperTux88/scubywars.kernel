package de.tdng2011.game.kernel

import actors.Actor
import Actor.State._
import collision.CollisionHandler
import util.Random
import de.tdng2011.game.library.util.ScubywarsLogger

object World extends Actor with ScubywarsLogger {

	val size = 1000 //m
  var publicIds = 0
  var entityDescriptions : IndexedSeq[EntityDescription] = IndexedSeq()

 var entityList : IndexedSeq[Entity] = IndexedSeq()

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

        case x : RemovePlayerFromWorldMessage => {
          entityList = entityList.filter(_ != x.player)
          nameMap = nameMap - x.player.publicId
          ConnectionHandler.event(PlayerRemovedMessage(x.player))
          ScoreBoard !! PlayerRemovedMessage(x.player)
          findShotFromPlayer(x.player) match {
            case x : Some[Shot] => World !! RemoveShotFromWorldMessage(x.get)
            case x => {}
          }
        }

        case x : AddPlayerMessage => {
          val player = newPlayer
          entityList = entityList :+ player
          nameMap = nameMap + (player.publicId -> x.name)
          ConnectionHandler.event(PlayerAddedMessage(player.publicId, x.name))
          ScoreBoard !! PlayerAddedMessage(player.publicId, x.name)
          reply { Some(player) }
        }

        case x : ShotCreatedMessage => {
          entityList = entityList :+ x.shot
          reply { Some(x.shot) }
        }

        case x => {
          logger.warn("Received unknown message " + x)
          reply {None}
        }
      }
    }
  }

  def nextPublicId = { publicIds+=1; publicIds }
  def newPlayer = new Player(Vec2(new Random().nextInt(500), new Random().nextInt(499)), nextPublicId).start.asInstanceOf[Player]

  def findShotFromPlayer(player : Player) : Option[Shot] = {
    entityList.filter(_.isInstanceOf[Shot]).asInstanceOf[IndexedSeq[Shot]].find(_.parentId == player.publicId)
  }
}
