object MapMain {
  def main(args: Array[String]) {
    val l = List(Option(123), Option(null), Option("abc"))

    l foreach { e =>
      /* Option#mapで、Someのときだけ処理する、というのが書ける
      つまりmatchでの分岐が不要になる
      Noneのケースをケアしないのであれば、mapで書くのがすっきりするか？
      Noneを無視する、という意図を伝えたいとしても、それが正しく伝わるかな
      */
      e map { v =>
        println(v)
      }
    }
  }
}
