package de.tdng2011.game.kernel

import actors.Actor
import de.tdng2011.game.library.EntityTypes
import de.tdng2011.game.library.util.Vec2

case class EntityDescription(pos : Vec2,
                             publicId : Long,
                             direction : Float,
                             speed : Short,
                             radius : Short,
                             entityType : EntityTypes.Value,
                             actor : Actor,
                             bytes : Array[Byte])