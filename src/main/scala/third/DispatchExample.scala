package third

import dispatch.Defaults._
import dispatch._

import scala.concurrent.Await
import scala.concurrent.duration._
import scala.util.{Failure, Success}

object DispatchExample {
  def main(args: Array[String]) {
    val svc = url("http://api.hostip.info/country.php")
    val country = Http(svc OK as.String)
    country.onComplete {
      case Success(msg) => println("succeed!"); println(msg)
      case Failure(ex) => println("failed!"); println(ex)
    }
    val duration = Duration(5, SECONDS)
    Await.ready(country, duration)
  }

  def use_await(country: Future[String]): Unit = {
    val duration = Duration(5, SECONDS)
    Await.ready(country, duration)
    // value.getをそのまま呼び出してるのがなんかやな感じ
    country.value.get match {
      case Success(msg) => println(msg)
      case Failure(ex) => println("failure"); println(ex)
    }
  }

  def simple(country: Future[String]): Unit = {
    val length = for (c <- country) yield c.length
    for (l <- length) yield println(l)
  }
}
