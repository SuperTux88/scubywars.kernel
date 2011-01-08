package de.tdng2011.game.kernel

import scala.math._

object WorldDefs {
	val size           =1000 //m
	val speed          =100  //m/s
	val shotSpeed      =speed*2
	val shotTimeToLife =size/shotSpeed*0.2 
	val rotSpeed       =2*Pi //rad/s
	val monsterSize    =10

}

