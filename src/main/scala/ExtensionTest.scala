/**
 * Created by hiroyuki.shirakawa on 2015/09/28.
 */
object ExtensionTest {
  def main(args: Array[String]) {
    val c = new Child
    println(c.f1)
  }
}

trait Parent {
  def f1: Boolean
}

trait Functions {
  def f1: Boolean
}

class Child extends Parent with Functions {
  def f1 = false
}


