import scala.util.{Failure, Success, Try}


object MatchTest {
  def main(args: Array[String]) {
    val e = Try {
      Option("Bob")
    } match {
      case Success(Some(n)) => Right(n.toLowerCase)
      case Success(None) => Left("returned None")
      case Failure(e) => Left(e.getMessage)
    }
    println(e)
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
