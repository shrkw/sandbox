package di.eedesign

/**
 * http://eed3si9n.com/ja/real-world-scala-dependency-injection-di
 *
 * Structual Typing
 */
// Service interface
trait OnOffDevice {
  def on: Unit
  def off: Unit
}

trait SensorDevice {
  def isDrinkPresent: Boolean
}

trait Warmer {
  def trigger: Unit
}

// Service implementation
class Heater extends OnOffDevice {
  def on = println("turned on")
  def off = println("turned off")
}

class PotSensor extends SensorDevice {
  def isDrinkPresent = true
}

// Mock Service Implementation
class MockHeater extends OnOffDevice {
  def on = println("mocking, so not turn on")
  def off = println("mocking, so not turn on")
}

class MockPotSensor extends SensorDevice {
  def isDrinkPresent = true
}