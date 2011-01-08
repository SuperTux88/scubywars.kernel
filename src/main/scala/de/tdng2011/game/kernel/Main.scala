package de.tdng2011.game.kernel

object Main {
  def main(args: Array[String]) = {  
	  println("tdng 2011")
	  
	  val game=Game
	  
	  val m1=game.createMonster("test1", "8.8.8.8")
	  val m2=game.createMonster("fun", "127.0.0.1")
	  val m3=game.createMonster ("felix", "123.123.123.123")
	  
	  while (true) {
	 	  Thread.sleep(100)
	 	  game.monsterAction(m1,true,false,false,true)
	 	  game.monsterAction(m2,false,true,true,false)
	 	  game.monsterAction(m3, true, true, true, false)
	 	  
	 	  game.think(100/1000.0)
	 	  println(game getWorld)
	  }	  
  }
}