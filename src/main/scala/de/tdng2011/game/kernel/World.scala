package de.tdng2011.game.kernel

import actors.Actor
import Actor.State._
import collision.CollisionHandler
import util.Random
import de.tdng2011.game.library.util.{ ScubywarsLogger, Vec2 }

object World extends Actor with ScubywarsLogger {

  val size = 1000 //m
  var entityDescriptions: IndexedSeq[EntityDescription] = IndexedSeq()

  var entityList: IndexedSeq[Entity] = IndexedSeq()

  var nameMap = Map[Long, String]()

  def act = {
    loop {
      react {
        case x: ThinkMessage => {
          entityList = entityList.filter(_.getState != Terminated)
          val thinkResults: IndexedSeq[Future[Any]] = for (p <- entityList) yield p !! ThinkMessage(x.time, entityList)
          entityDescriptions = for (x <- thinkResults) yield x.apply.asInstanceOf[Option[EntityDescription]].get
          CollisionHandler.handleCollisions(entityDescriptions)
          ConnectionHandler.event(entityDescriptions)
          reply { None }
        }

        case x: RemoveShotFromWorldMessage =>
          entityList = entityList.filter(_ != x.shot)

        case x: RemovePlayerFromWorldMessage => {
          entityList = entityList.filter(_ != x.player)
          nameMap = nameMap - x.player.publicId
          ConnectionHandler.event(PlayerRemovedMessage(x.player))
          ScoreBoard !! PlayerRemovedMessage(x.player)
          findShotFromPlayer(x.player) match {
            case x: Some[Shot] => World !! RemoveShotFromWorldMessage(x.get)
            case x => {}
          }
        }

        case x: AddPlayerMessage => {
          val player = newPlayer
          entityList = entityList :+ player
          val name = findFreeName(x.name)
          logger.info("Player " + name + " connected with id " + player.publicId)
          nameMap = nameMap + (player.publicId -> name)
          ConnectionHandler.event(PlayerAddedMessage(player.publicId, name))
          ScoreBoard !! PlayerAddedMessage(player.publicId, name)
          reply { Some(player) }
        }

        case x: ShotCreatedMessage => {
          entityList = entityList :+ x.shot
          ConnectionHandler.event(ShotSpawnedMessage(x.shot.publicId, x.shot.parentId, x.shot.pos))
          reply { Some(x.shot) }
        }

        case x => {
          logger.warn("Received unknown message " + x)
          reply { None }
        }
      }
    }
  }

  def newPlayer = new Player(Vec2(new Random().nextInt(500), new Random().nextInt(499)), IdActor.getNextId).start.asInstanceOf[Player]

  def findShotFromPlayer(player: Player): Option[Shot] = {
    entityList.filter(_.isInstanceOf[Shot]).asInstanceOf[IndexedSeq[Shot]].find(_.parentId == player.publicId)
  }

  def findFreeName(name: String): String = {
    var curName = name
    var i = 0
    while (!nameMap.find(_._2 == curName).isEmpty) {
      i += 1
      curName = name + i
    }
    curName
  }
}
