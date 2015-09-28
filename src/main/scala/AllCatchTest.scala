/**
 * Created by hiroyuki.shirakawa on 2015/08/31.
 */

import scala.util.control.Exception.allCatch
import scala.util.{Failure, Success, Try}


object AllCatchTest {
  def main(args: Array[String]) {

    // Option
    val a1 = allCatch.opt {
      "foo".toInt
    }
    println(a1)

    // Either
    val a2 = allCatch.either {
      "foo".toInt
    }
    println(a2)

    // Eitherの処理の続き
    allCatch.either {
      "foo".toInt
    } match {
      case Right(n) => n
      case Left(e) => 0
    }

    // Try
    val a3 = allCatch.withTry {
      "foo".toInt
    }
    println(a3)

    // Tryの処理の例
    val a4 = Try {
      "foo".toInt
    } match {
      case Success(_) => println("good")
      case Failure(_) => println("bad")
    }

    println(f1)
  }

  def f1: Int = {
    Option(null) match {
      case Some(n) => 1
      case None => 0
    }
  }

}