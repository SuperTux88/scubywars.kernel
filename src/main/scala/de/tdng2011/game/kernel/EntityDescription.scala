package de.tdng2011.game.kernel

import actors.Actor

/**
 * Created by IntelliJ IDEA.
 * User: fbe
 * Date: 1/22/11
 * Time: 10:06 PM
 * To change this template use File | Settings | File Templates.
 */

case class EntityDescription(pos : Vec2, publicId : Long, direction : Double, speed : Int, radius : Int, entityType : EntityTypes.Value, actor : Actor)