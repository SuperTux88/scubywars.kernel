package de.tdng2011.game.webservice

import de.tdng2011.game.kernel.WorldDescription
import de.tdng2011.game.kernel.Game
import javax.swing.JFrame
class GameThread extends Runnable {
  override def run() {
    // server start
    // visualizer start
    val jFrame = new JFrame

    val game = new Game
    val m1 = game.createMonster("test1", "8.8.8.8")
    val m2 = game.createMonster("fun", "127.0.0.1")
    val m3 = game.createMonster("felix", "123.123.123.123")

    jFrame setBounds (640, 480, 640, 480)
    jFrame setVisible true
    jFrame setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE)

    while (true) {
      Thread.sleep(100)
      game.monsterAction(m1, true, false, false, true)
      game.monsterAction(m2, false, true, true, false)
      game.monsterAction(m3, true, true, true, false)
      game think(100 / 1000.0)
      
      println(game getWorldDescription)
    }
  }
}