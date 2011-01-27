package de.tdng2011.game.kernel

import collision.CollisionHandler
import java.util.Random
import actors.{Actor, Future}

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 22.01.11
 * Time: 23:16
 * To change this template use File | Settings | File Templates.
 */

object Server {

  def main(args : Array[String]){

    World.start
    ScoreBoard.start

    while(true){
      World !! ThinkMessage(0.025)
      Thread.sleep(25)
    }

    System exit 0
  }
}