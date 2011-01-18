package de.tdng2011.game.kernel

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/19/11
 * Time: 12:53 AM
 * To change this template use File | Settings | File Templates.
 */

class Player extends Entity {
  think {
    case x : String => {
      println("this is the player! received string " + x)
    }

    case y  => {
      println("this is the player unknown call received: " + y);
    }
  }

}