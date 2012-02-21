package de.tdng2011.game.kernel

/**
 * Created by IntelliJ IDEA.
 * Author: fbe und SuperTux88
 * Date: 22.01.11
 * Time: 23:16
 */
object Server {
  val framesPerSecond = 40.0

  val frameDuration = 1000.0 / framesPerSecond

  def main(args: Array[String]) {

    IdActor.start
    World.start
    ScoreBoard.start

    if (args contains "--startDefaultBots") startDefaultBots

    var lastSleepTime: Double = 0

    while (true) {
      val frameStart = getTime // 8000

      World !? ThinkMessage(lastSleepTime / 1000.0, null)

      val frameEnd = getTime // 8024  / 8050
      val sleepTime = (frameDuration) - (frameEnd - frameStart)
      if (sleepTime > 0) //    1  /  -25
        Thread sleep (sleepTime.toLong)
      val afterSleep = getTime // 8025  / 8050
      lastSleepTime = afterSleep - frameStart //   25  /   50
    }

    System exit 0
  }

  def getTime = System.currentTimeMillis

  def startDefaultBots {
    new Thread() {
      override def run {

        World !? AddPlayerMessage("1x")
        (World !? AddPlayerMessage("2x")).asInstanceOf[Some[Player]].get !! PlayerActionMessage(false, false, true, true)
        (World !? AddPlayerMessage("3x")).asInstanceOf[Some[Player]].get !! PlayerActionMessage(true, false, true, true)

        new de.tdng2011.game.killerclient.Client("localhost", "##OJ##")
      }
    }.start
  }
}