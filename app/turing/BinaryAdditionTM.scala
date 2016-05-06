package turing

import scala.collection.mutable.Seq

object BinaryAdditionTM
    extends TM(Set(0,1,2,3,4,5), Set('0','1','#','+'), 2, 5) {
  val q0 = 2
  val qH = 5

  val delta = Map(
      (0, '0') -> (0, '1', Left),
      (0, '1') -> (1, '0', Left),
      (0, '+') -> (4, '#', Right),
      (1, '0') -> (1, '0', Left),
      (1, '1') -> (1, '1', Left),
      (1, '+') -> (3, '+', Left),
      (2, '0') -> (2, '0', Right),
      (2, '1') -> (2, '1', Right),
      (2, '+') -> (2, '+', Right),
      (2, '#') -> (0, '#', Left),
      (3, '0') -> (2, '1', Right),
      (3, '1') -> (3, '0', Left),
      (3, '#') -> (2, '1', Right),
      (4, '1') -> (4, '#', Right),
      (4, '#') -> (5, '#', Still)
    )

  def apply(i: Int, j: Int): Unit = {
    tape = tape ++ ("#" + i.toBinaryString + "+" + j.toBinaryString + "#").toSeq
    run(2, 1)
    val result = (tape mkString "") filter (c => c == '1' || c == '0')
    println(s"Result: $result (${Integer.parseInt(result, 2)})")
    tape = Seq()
  }
}
