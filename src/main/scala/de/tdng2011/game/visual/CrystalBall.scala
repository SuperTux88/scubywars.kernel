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

  var currentMonsterList: List[Monster] = List[Monster]()

  var frame = new MainFrame {

    val url = getClass().getClassLoader().getResource("/background.jpg")
    val bgImage = ImageIO.read(new File(url.getFile))

    title = "Crystal Ball"
    var mainPanel = new BoxPanel(scala.swing.Orientation.Horizontal) {
      focusable = true
      background = Color.black
      preferredSize = new Dimension(WorldDefs.size + 400, WorldDefs.size)
      //bounds_=(new Rectangle(0,0,WorldDefs.size + 100, WorldDefs.size))
    }

    var gameFieldPanel = new Panel {
      focusable = true
      background = Color.black
      preferredSize = new Dimension(mainPanel.size.width + 600, WorldDefs.size)
      //bounds_=(new Rectangle(0,0,WorldDefs.size + 100, WorldDefs.size))

      override def paint(g: Graphics2D) {
        super.paint(g)
        //g.setColor(Color.blue)
        //g.fillRect(0, 0, size.width + (WorldDefs.monsterRadius / 2), size.height)

        g.drawImage(bgImage, 0, 0, null);

        g.setColor(Color.white)
        g.drawRect(0, 0, size.width, size.height)

        for (monster <- currentMonsterList) {
          drawMonster(g, monster.pos, monster.dir, monster.name, monster.isShot)
        }
      }
    }

    var statsPanel = new Panel() {
      focusable = true
      //background = Color.BLACK

      preferredSize = new Dimension(mainPanel.size.width - gameFieldPanel.size.width, mainPanel.size.height)
      //bounds_=(new Rectangle(WorldDefs.size + 100, 0, 300, WorldDefs.size))

      override def paintComponent(g: Graphics2D) {
        super.paintComponent(g)
        g.setColor(Color.YELLOW)
        g.fillRect(0, 0, size.width, size.height)
      }

       opaque_=(false)
        background = new Color(0, 0, 255, 50)
      
      override def paint(g: Graphics2D) {
        super.paint(g)

        var i: Int = 1

        currentMonsterList = currentMonsterList.sort(_.getWeight > _.getWeight)

        val oldFont = g.getFont

        for (monster <- currentMonsterList) {
          if (!monster.isShot) {

            //g.fillRect(WorldDefs.size + 100, 0, 300, size.height)

            g.setFont(new Font("Arial", Font.PLAIN, 20))

            g.setColor(Color.GREEN)
            g.drawString(monster.name + " " + monster.score, 20, i * 25)
            g.setFont(font)

            i += 1
          }
        }
      }
    }

    //mainPanel.peer.setLayout(new BorderLayout)

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

  override def run {
    while (true) {
      Thread.sleep((1 / 120.0 * 1000).toLong)
      currentMonsterList = Game.getWorld.monsters
      frame.repaint()
    }
  }

}
