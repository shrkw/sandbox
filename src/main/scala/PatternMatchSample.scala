object PatternMatchSample {
  def main(args: Array[String]) {
    val l = Map("ID" -> 1, "AT" -> 5, "RU" -> 10, "BR" -> 20)
    l foreach { e =>
      val a = e._1 match {
        // ifはORのあとに実行されるようだ
        case "ID" | "AT" if e._2 > 4 => "nice"
        case "RU" => "so so"
        case "BR" => "not bad"
        case _ => "boo"
      }
      println(a)
    }
  }
}
