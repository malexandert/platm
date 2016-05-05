package turing

import scala.collection.mutable.Seq

object AdditionTuringMachine
    extends TuringMachine(Set(0,1,2,3,4,5), Set(0,1), 0, 5) {
  val delta = Map(
      (0, '0') -> (0, '0', Right),
      (0, '1') -> (1, '1', Right),
      (1, '0') -> (2, '1', Right),
      (1, '1') -> (1, '1', Right),
      (2, '0') -> (3, '0', Left),
      (2, '1') -> (2, '1', Right),
      (3, '0') -> (3, '0', Left),
      (3, '1') -> (4, '0', Left),
      (4, '0') -> (5, '0', Still),
      (4, '1') -> (4, '1', Left)
    )

  def apply(i: Int, j: Int): Unit = {
    tape = tape ++ ("0" + toUnary(i) + "0" + toUnary(j) + "0").toSeq
    run(0, 0)
    println(s"Result: ${fromUnary(tape mkString)}")
    tape = Seq()
  }
}
