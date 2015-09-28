/**
 * Created by hiroyuki.shirakawa on 2015/08/31.
 */
object NullMatch {
  def main(args: Array[String]) {
     1 to 10 map f1 foreach { i => i match {
         // pattern matchは上からみて、該当すればそこで処理して止まるのでnullを先に書かないといけない
         // まあ、これくらいはpattern matchの基本だから、使ってもいいか
         case null => println(0)
         case n => println(n + "!")
       }
     }
  }

  def f1(i: Int): String = {
    if (i < 5) {
      i.toString
    } else {
      null
    }
  }

}
