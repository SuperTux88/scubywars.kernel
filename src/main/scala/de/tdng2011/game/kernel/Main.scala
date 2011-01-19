package de.tdng2011.game.kernel

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/19/11
 * Time: 12:56 AM
 * To change this template use File | Settings | File Templates.
 */

object Main {
  def main(args : Array[String]){
    val player = new Player(Vec2(0,0),133l).start
    player !! 'think
    player !! "hi"
  }
}