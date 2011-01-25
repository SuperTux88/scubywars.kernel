package de.tdng2011.game.kernel

import actors.Actor


abstract class Entity(var pos : Vec2, val publicId : Long) extends Actor {

  protected var direction : Float;
  protected var speed : Short  // m/s
  protected var radius : Short  // m
  protected val entityType : EntityTypes.Value

  var thinkHandler : PartialFunction[Any, Option[EntityDescription]] = { case _ => None }

  def act = {
    loop {
      react {

        case x : ActorKillMessage => {
          reply { None }
          exit
        }

        case x => {
          reply {
            thinkHandler(x);
          }
        }
      }
    }
  }

   def think(handler : PartialFunction[Any,Option[EntityDescription]]){
     thinkHandler = handler;
   }
}