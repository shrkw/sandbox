/**
 * Created by hiroyuki.shirakawa on 2015/08/31.
 */
object LazyTest {
  def main(args: Array[String]) {
    val o =  LazyValTest
    println("New Object")
    println(o.value)
    println(o.value)
    println(o.value)
    println(o.value)

    val a = { println("eval a"); "a" }

    // var や def には適用できない。 // 以下はコンパイルエラー
    // lazy var b = { "b" }
    // lazy def c = { "c" }

    println( "-- before --" )
    println( a ) // 初回の参照。このとき初めてaの値が評価され、"eval a"と"a"が表示される。
    println( a )

  }
}
// objectでもclassでも動きは変わらず
object LazyValTest {
  lazy val value: Int = {
    println("Initialize")
    val a = 5 * n
    println("Init finished")
    a
  }

  val n: Int = 3
}