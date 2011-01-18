package de.tdng2011.game.kernel

import scala.math._

object  WorldDefs {
	val size           =1000 //m
	val speed          =100  //m/s
	val shotSpeed      =speed*4
	val shotTimeToLife =size/shotSpeed*0.5 
	val rotSpeed       =2*Pi //rad/s
	val monsterRadius  =15
	val shotRadius	   =5
}

