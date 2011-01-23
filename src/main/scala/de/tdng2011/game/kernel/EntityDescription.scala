package de.tdng2011.game.kernel

import actors.Actor

case class EntityDescription(pos : Vec2,
                             publicId : Long,
                             direction : Double,
                             speed : Int,
                             radius : Int,
                             entityType : EntityTypes.Value,
                             actor : Actor,
                             bytes : Array[Byte])