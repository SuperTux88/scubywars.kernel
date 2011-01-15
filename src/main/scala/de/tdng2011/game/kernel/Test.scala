package de.tdng2011.game.kernel
import scala.actors.Actor._;
import scala.actors.Actor;
/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/15/11
 * Time: 1:44 PM
 * To change this template use File | Settings | File Templates.
 */

object Test {
  def main(args: Array[String]){
    val myActor = actor {
      //loop {
        receive {
          case s: String => println("I got a String: " + s)
          case i: Int => println("I got an Int: " + i.toString)
          case _ => println("I have no idea what I just got.")
        }
      //}
    }

    myActor ! 'zing
  //  myActor ! "loop"
   // myActor.trapExit = false


  }
}