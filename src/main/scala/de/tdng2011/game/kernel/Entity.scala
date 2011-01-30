package de.tdng2011.game.kernel

import actors.Actor
import de.tdng2011.game.library.EntityTypes

abstract class Entity(var pos : Vec2, val publicId : Long) extends Actor {

  protected val entityType : EntityTypes.Value

  protected var direction : Float = 0
  protected var speed : Short = 0 // m/s
  protected var radius : Short = 0  // m

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