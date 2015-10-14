package di.eedesign.implic.v1

import di.eedesign._

class CoffeeWarmer(implicit val sensor: SensorDevice, implicit val onOff: OnOffDevice) extends Warmer {
  def trigger = {
    if (sensor.isDrinkPresent) onOff.on else onOff.off
  }
}

class Client(implicit val warmer: Warmer) {
  def doSomething: Unit ={
    warmer.trigger
  }
}

trait Config {
}

/**
 * lazyにしたいなら、implicit lazyで書けばいいみたい
 */
object RealConfig extends Config {
  implicit lazy val sensor = new PotSensor
  implicit lazy val heater = new Heater
}

object MockConfig extends Config {
  implicit lazy val sensor = new MockPotSensor
  implicit lazy val heater = new MockHeater
}

object Runner {
  def main(args: Array[String]): Unit = {
    runMock

  }


  def runMock: Unit = {
    import MockConfig._
    implicit val warmer = new CoffeeWarmer
    val c = new Client()
    c.doSomething
  }

  def runProd(): Unit = {
    import RealConfig._
    val warmer = new CoffeeWarmer
    warmer.trigger
  }
}