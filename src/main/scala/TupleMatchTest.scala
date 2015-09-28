/**
 * Created by hiroyuki.shirakawa on 2015/09/03.
 */
object TupleMatchTest {
  def main(args: Array[String]) {
    val l = List("Alice", "Bob", "C")
    l map f foreach println
  }

  def f(src: String): Boolean = {
    (src, src.length) match {
      case (_, l) if l < 4 => false
      case (_, _) => true
    }
  }
}
