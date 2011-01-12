package de.tdng2011.game.visual

import java.io.File
import javax.imageio.ImageIO
import swing._
import de.tdng2011.game.kernel._
import java.awt.{ Color, Graphics2D, Graphics, Font, Toolkit }
import math._
import scala.actors.threadpool._

object CrystalBall extends Runnable {
	
  val lineLength = WorldDefs.monsterRadius * 2

  var exploded: scala.collection.mutable.Queue[(Int, Int, Int)] = scala.collection.mutable.Queue[(Int, Int, Int)]()
  val explodDuration = 30
  val fileSeparator = System getProperty("file.separator")
  var currentMonsterList: List[Monster] = List[Monster]()

  var frame = new MainFrame {

    val url = getClass().getClassLoader().getResource(fileSeparator+"background.jpg")
    val bgImage = ImageIO.read(new File(url.getFile))
    val urlTransparent = getClass().getClassLoader().getResource(fileSeparator+"background_transparent.jpg")
    val bgImageTransparent = ImageIO.read(new File(urlTransparent.getFile))

    title = "Crystal Ball"
    var mainPanel = new BoxPanel(scala.swing.Orientation.Horizontal) {
      focusable = true
      background = Color.BLACK
      preferredSize = new Dimension(WorldDefs.size + 400, WorldDefs.size)
    }

    var gameFieldPanel = new Panel {
      focusable = true
      background = Color.BLACK
      preferredSize = new Dimension(mainPanel.size.width + 600, WorldDefs.size)

      override def paintComponent(g: Graphics2D) {
        super.paintComponent(g)

        g.drawImage(bgImage, 0, 0, null);

        for (monster <- currentMonsterList) {
          drawMonster(g, monster.pos, monster.dir, monster.name, monster.isShot)

          val died: (Boolean, Vec2) = monster.died

          if (died._1) {
            exploded.enqueue((died._2.x.toInt, died._2.y.toInt, 0))
          }

          var tmpList: List[(Int, Int, Int)] = List[(Int, Int, Int)]()

          Iterator.iterate(exploded) { qi =>
            val (x, y, s) = qi.dequeue
            drawExplosion(g, Vec2(x, y), s)
            if (s < explodDuration) tmpList ::= ((x, y, s + 1))
            qi
          }.takeWhile(!_.isEmpty).foreach(identity)

          for (tupel <- tmpList)
            exploded.enqueue(tupel)
        }
      }
    }

    var statsPanel = new Panel() {
      focusable = true
      background = Color.BLACK

      preferredSize = new Dimension(mainPanel.size.width - gameFieldPanel.size.width, mainPanel.size.height)

      override def paintComponent(g: Graphics2D) {
        super.paintComponent(g)

        g.drawImage(bgImageTransparent, 0, 0, null);
        var i: Int = 1

        currentMonsterList = currentMonsterList.sort(_.getWeight > _.getWeight)

        val oldFont = g.getFont

        for (monster <- currentMonsterList) {
          if (!monster.isShot) {

            g.setFont(new Font("Arial", Font.PLAIN, 20))

            g.setColor(Color.GREEN)
            g.drawString(monster.name + " " + monster.score, 20, i * 25)
            g.setFont(font)

            i += 1
          }
        }
      }
    }

    mainPanel.peer.add(gameFieldPanel.peer)
    mainPanel.peer.add(statsPanel.peer)

    contents = mainPanel

    centerOnScreen
    resizable_=(false)
    visible_=(true)
  }

  def drawMonster(g: Graphics2D, pos: Vec2, rot: Double, name: String, isShot: Boolean) {
    if (!isShot) {
      val ahead = Vec2(1, 0).rotate(rot)
      val posPeak = pos + ahead * (lineLength / 2)

      val aheadLeft = Vec2(1, 0).rotate(rot + sin(60) + Pi)
      val aheadRight = Vec2(1, 0).rotate(rot - sin(60) + Pi)

      val posLeft = posPeak + aheadLeft * lineLength
      val posRight = posPeak + aheadRight * lineLength

      val x1 = posPeak.x.toInt
      val y1 = posPeak.y.toInt

      val x2 = posRight.x.toInt
      val y2 = posRight.y.toInt

      val x3 = posLeft.x.toInt
      val y3 = posLeft.y.toInt

      val oldFont = g.getFont

      g.setColor(Color.YELLOW)
      g.setFont(new Font("Arial", Font.PLAIN, 20))
      g.drawString(name, x1 + 20, y1 + 20)

      g.setColor(Color.GREEN)
      g.setFont(oldFont)
      g.drawLine(x1, y1, x3, y3)
      g.drawLine(x1, y1, x2, y2)
    } else {
      g.setColor(Color.RED)
      g.fillOval(pos.x.toInt - (WorldDefs.shotRadius / 2), pos.y.toInt - (WorldDefs.shotRadius / 2), WorldDefs.shotRadius * 2, WorldDefs.shotRadius * 2)
    }
  }

  def drawExplosion(g: Graphics2D, pos: Vec2, step: Int) {

    val ahead = Vec2(1, 0)
    var startPos = pos
    var endPos = Vec2(0, 0)

    for (i <- 1 to 8) {
      val ahead = Vec2(1, 0).rotate((Pi / 4) * i)
      startPos = pos + ahead * (step / explodDuration)
      endPos = pos + ahead * (lineLength / 2)
      g.setColor(Color.YELLOW)
      g.drawLine(startPos.x.toInt, startPos.y.toInt, endPos.x.toInt, endPos.y.toInt)
    }

  }

  override def run {
    while (true) {
      Thread.sleep((1 / 120.0 * 1000).toLong)
      currentMonsterList = Game.getWorld.monsters
      frame.repaint()
    }
  }
}
