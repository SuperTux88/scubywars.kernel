package de.tdng2011.game.kernel

object IdGen {
	private var c=1
	def getNext= {
		c+=1
		c.toString
	}
}