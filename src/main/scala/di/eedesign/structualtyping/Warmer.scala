package di.eedesign.structualtyping

import di.eedesign._

/**
 *
 * @param env あとからobjectでくるんだもので依存性を渡すためにenvにしている
 */
class CoffeeWarmer(env: {
  val potSensor: SensorDevice
  val heater: OnOffDevice}) extends Warmer {
  def trigger = {
    if (env.potSensor.isDrinkPresent) env.heater.on else env.heater.off
  }
}

/**
 * Warmerを使うもの
 * AliceService -> BobService -> MailService
 * みたいな依存性の連鎖のケースにおける、AliceServiceの役割のやつ
 *
 * @param env
 */
class Client(env: {val warmer: Warmer}) {
  def doSomething: Unit = {
    env.warmer.trigger
  }
}

object Runner {
  def main(args: Array[String]): Unit = {
    resolveAtOneShot()
  }

  /**
   * 逐次的に依存性を解決するパターン
   */
  def resolveSequentially: Unit = {
    object WarmerConfig {
      lazy val potSensor: SensorDevice = new MockPotSensor
      lazy val heater: OnOffDevice = new MockHeater
    }
    object ClientConfig {
      lazy val warmer = new CoffeeWarmer(WarmerConfig)
    }
    val c = new Client(ClientConfig)
    c.doSomething
  }

  /**
   * Clientへの依存性をすべて一度に解決する場合、こうする
   */
  def resolveAtOneShot(): Unit = {
    object Config {
      lazy val potSensor = new PotSensor
      lazy val heater = new Heater
      lazy val warmer = new CoffeeWarmer(this)
    }
    val c = new Client(Config)
    c.doSomething
  }


  /**
   * Warmerのための依存性解決object
   */
  def f3: Unit = {
    object MockWarmerConfig {
      lazy val potSensor: SensorDevice = new MockPotSensor
      lazy val heater: OnOffDevice = new MockHeater
    }
    val w = new CoffeeWarmer(MockWarmerConfig)
    w.trigger
  }
}