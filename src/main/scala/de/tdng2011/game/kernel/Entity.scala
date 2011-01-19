package de.tdng2011.game.kernel

import actors.Actor


abstract class Entity(var pos : Vec2, val publicId : Long) extends Actor {

  var direction : Double;
  var speed : Int  // m/s
  var radius : Int  // m

  var thinkHandler : PartialFunction[Any, Unit] = {
    case _ => {}
  }

  def act = {
    loop {
      react {
        case 'think =>
          println("think method in generic handling received");
          thinkHandler('think)

        case x => {
          thinkHandler(x);
        }
      }
    }
  }

   def think(handler : PartialFunction[Any,Unit]){
     thinkHandler = handler;
   }
}