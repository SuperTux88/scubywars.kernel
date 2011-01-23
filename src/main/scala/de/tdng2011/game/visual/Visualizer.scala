package de.tdng2011.game.visual

/**
 * Created by IntelliJ IDEA.
 * User: benjamin
 * Date: 23.01.11
 * Time: 00:56
 * To change this template use File | Settings | File Templates.
 */

object Visualizer extends Runnable {

  import java.io.File
  import de.tdng2011.game.kernel._
  import java.util.Random
  import javax.imageio.ImageIO
  import swing._
  import de.tdng2011.game.kernel._
  import java.awt.{ Color, Graphics2D, Graphics, Font, Toolkit }
  import math._
  import scala.actors.threadpool._


    val lineLength = 30 // todo monster / player radius * 2

    var currentEntityDescriptions: IndexedSeq[EntityDescription] = IndexedSeq[EntityDescription]()

    val stars = for(x <- 1 to 50) yield (new Random().nextInt(WorldDefs.size),new Random().nextInt(WorldDefs.size))
    var frame = new MainFrame {

      title = "Scubywars"
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
          for ((x,y) <- stars){
            g.setColor(Color.WHITE)
            g.drawString("*",x,y)
          }
          for (description <- currentEntityDescriptions) {
            drawMonster(g, description.pos, description.direction, description.publicId+"", false)
          }
        }
      }

      var statsPanel = new Panel() {
        focusable = true
        background = Color.BLACK

        preferredSize = new Dimension(mainPanel.size.width - gameFieldPanel.size.width, mainPanel.size.height)
      }

      mainPanel.peer.add(gameFieldPanel.peer)
      mainPanel.peer.add(statsPanel.peer)

      contents = mainPanel

      centerOnScreen
      resizable_=(false)
      visible_=(true)
    }

    def drawMonster(g: Graphics2D, pos: Vec2, rot: Double, name: String, isShot: Boolean) {

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
    }

    override def run {
      while (true) {
        // TODO implement last repaint time measurement for static frame rate
      //  Thread.sleep((1 / Game.framesPerSecond * 1000.0).toLong)
          Thread.sleep((1 / 4 * 1000.0).toLong)
        currentEntityDescriptions = Server.entityDescriptions
        frame.repaint()
      }
    }


}