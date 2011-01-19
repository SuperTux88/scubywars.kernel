package de.tdng2011.game.kernel

import actors.Actor


abstract class Entity extends Actor {
  var thinkHandler : PartialFunction[Any,Unit] = {
    case x => {
      println("warning, thinkHandler not implemented in child of Entity")
    }
  }
  def act = {
    loop {
      react {
        case 'think =>
          println("think method in generic handling received");

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