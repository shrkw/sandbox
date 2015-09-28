import scala.util.{Try, Success, Failure}

/**
 * Javaのコードの呼び出しをどう安全に行うか
 * */
object TryOptionTest {
  def main(args: Array[String]): Unit = {
    val people = List("Alice", "Bob", "Carol", "Dave")
    people map entrance foreach {
      case Right(n) => println(s"Hello $n")
      case Left(m) => println(m)
    }
  }
  
  def entrance(name: String): Either[String, String] = {
    Try {
      Option(doorSecurity(name))
    } match {
      case Success(Some(n)) => Right(n)
      case Success(None) => Left("do not pass null")
      case Failure(e) => Left(e.getMessage)
    }
  }

  /** [値, null, 例外]という3種類の戻りがあるメソッド
    * 実際にはJavaのライブラリの呼び出し */
  def doorSecurity(name: String): String = {
    name match {
      case "Bob" => null
      case "Dave" => throw new RuntimeException("Dave is not allowed")
      case n => n
    }
  }
}
