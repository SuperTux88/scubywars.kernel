package de.tdng2011.game.kernel
import java.util.Random
import scala.actors.Future
/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 22.01.11
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */

object Server {

  def main(args : Array[String]){
    val playerList = for(x <- 1 to 50000) yield new Player(Vec2(new Random().nextInt(500), new Random().nextInt(499)), x).start



    val thinkResults : IndexedSeq[Future[Any]] = for(p <- playerList) yield p !! ThinkMessage(1)
    thinkResults.map(x => println(x.apply))
    // re
  //  for(x <- 1 to 10){
  //       Thread.sleep(1000);
  //       println(player !? new ThinkMessage(1))
  //  }

    playerList.map(_ !? ActorKillMessage())

    System exit 0
  }
}