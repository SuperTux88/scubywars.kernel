package de.tdng2011.game.kernel

import actors.Actor

case class EntityDescription(pos : Vec2,
                             publicId : Long,
                             direction : Float,
                             speed : Short,
                             radius : Short,
                             entityType : EntityTypes.Value,
                             actor : Actor,
                             bytes : Array[Byte])