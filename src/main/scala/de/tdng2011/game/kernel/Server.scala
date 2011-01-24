package de.tdng2011.game.kernel

import collision.CollisionHandler
import java.util.Random
import scala.actors.Future
import de.tdng2011.game.visual.Visualizer

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 22.01.11
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */

object Server {
  var entityDescriptions : IndexedSeq[EntityDescription] = IndexedSeq()
  def main(args : Array[String]){
    ScoreBoard.start
    new Thread(Visualizer).start
    val playerList = for(x <- 1 to 5) yield new Player(Vec2(new Random().nextInt(500), new Random().nextInt(499)), x).start
    playerList(2) !! PlayerActionMessage(true,false,true,false)
    playerList(1) !! PlayerActionMessage(false,false,true,false)
    while(true){
      Thread.sleep(100)
      val thinkResults : IndexedSeq[Future[Any]] = for(p <- playerList) yield p !! ThinkMessage(0.1)
      entityDescriptions = for(x <- thinkResults) yield x.apply.asInstanceOf[Option[EntityDescription]].get
      CollisionHandler.handleCollisions(entityDescriptions)
      ConnectionHandler.event(entityDescriptions)
      // send GetEntityDescription message and inform players

    }

    playerList.map(_ !? ActorKillMessage())

    System exit 0
  }
}