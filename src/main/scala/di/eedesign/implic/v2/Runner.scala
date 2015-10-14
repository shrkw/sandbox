package di.eedesign.implic.v2

import di.eedesign._

class CoffeeWarmer(implicit val sensor: SensorDevice, implicit val onOff: OnOffDevice) extends Warmer {
  def trigger = {
    if (sensor.isDrinkPresent) onOff.on else onOff.off
  }
}

class Client(implicit val warmer: Warmer) {
  def doSomething: Unit = {
    warmer.trigger
  }
}

/**
 * 理解しきれてないけど、Warmer作るにはFactoryが必要。なぜだ？
 * A. implicit valが定義されているのはServicesなので、Servicesとしてimportしないといけないから。
 * ということはRealServicesをServicesとして扱ってimportを呼べばそれでいい？ それでよかった。
 *
 * @param repos
 */
class Factory(repos: Services) {

  import repos._

  lazy val warmer = new CoffeeWarmer
}

trait Services {
  implicit val sensor: SensorDevice

  implicit val heater: OnOffDevice
}

object RealServices extends Services {
  lazy val sensor = new PotSensor
  lazy val heater = new Heater
}

object MockServices extends Services {
  lazy val sensor = new MockPotSensor
  lazy val heater = new MockHeater
}

object Runner {
  def main(args: Array[String]): Unit = {
    //    val f = new Factory(RealServices)
    //    f.warmer.trigger
    createWithoutFactory()
  }


  def runMock: Unit = {

    //    implicit val warmer = new CoffeeWarmer
    //
    //    val c = new Client()
    //    c.doSomething
  }

  /**
   * Factoryなしで直接importしてimplicitを解決させようとしても、解決できないので、
   * Servicesとして扱ってimportする
   *
   */
  def createWithoutFactory(): Unit = {
    val a = RealServices.asInstanceOf[Services]
    import a._
    val warmer = new CoffeeWarmer
    warmer.trigger
  }

  /**
    * Factory経由なら動く。Servicesとしてimportしてるから。
    */
  def createWithFactory: Unit = {
    val f = new Factory(RealServices)
    f.warmer.trigger
  }
}
