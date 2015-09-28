/**
 * Created by hiroyuki.shirakawa on 2015/07/31.
 */
object ForComprehensionTest {
  val names = List("Alice", "Bob", "Carol", "Davidworth", "Erin", "Fabio")

  def main(args: Array[String]) {
    // Rightが続く限りfor式でまわしていって、最後Eitherで受け取る
    names map { name =>
      for {
        x <- e1(name).right
        x <- e2(name).right
      } yield x
    } foreach {
      case Left(n) => println(n)
      case Right(n) => println(s"Great! $n")
    }

    // Rightが続く限りfor式でまわしていって、最後fold
    names map { name =>
      (for {
        x <- e1(name).right
        x <- e2(name).right
      } yield x) fold(
        m => m,
        n => s"Nice! $n"
        )
    } foreach println

    // Left, Rightの型を捨てて中身をただ取り出すだけならmergeを呼び出す
    names map { name =>
      (for {
        x <- e1(name).right
        x <- e2(name).right
      } yield x).merge
    } foreach println
  }

  def forcon: Unit = {
    println(123)
    println(e1("1").left)

    val l = List("a", "b", "c")
    // for comprehension
    for (
      s <- Array("a", "b", "c")
    ) println(s)

    println(e1("john"))

    val o1 = Option(null)
    for {
      o <- o1
    } println(o)

    val o2 = Option(123)
    for {
      o <- o2
    } println(o)

    val l2 = List("Alice", "Bob", "Carol", "Davidworth", "Erin")

    l2 map { e =>
      (for {
        a <- e1(e).right
        b <- e2(e).right
      } yield b).merge
    } foreach println

    // List#mapはPartialFunctionを受け取れるのでパターンマッチをそのまま書ける
    l2 map {
      case n if n.length <= 4 => n
      case _ => "######"
    } foreach println
  }

  def e1(src: String): Either[String, String] = {
//    println("e1")
    src match {
      case n if n.length > 5 => Left("failed at e1, too long")
      case n if n.length % 2 == 0 => Left("failed at e1, even number")
      case n => Right(n)
    }
  }


  def e2(src: String): Either[String, String] = {
//    println("e2")
    src match {
      case n if n.startsWith("A") => Right(n)
      case n if n.startsWith("B") => Left(s"failed at e2, $n")
      case n if n.startsWith("C") => Right(n)
      case n => Left(s"failed at e2, $n")
    }
  }

}
