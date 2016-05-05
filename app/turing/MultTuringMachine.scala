package turing

import scala.collection.mutable.Seq

object MultTuringMachine
    extends TuringMachine((0 to 17).toSet, Set(0,1), 0, 16) {
  val delta = Map(
      (0, '0') -> (1, '0', Right),
      (0, '1') -> (2, '0', Right),
      (1, '0') -> (14, '0', Right),
      (1, '1') -> (2, '0', Right),
      (2, '0') -> (3, '0', Right),
      (2, '1') -> (2, '1', Right),
      (3, '0') -> (15, '0', Left),
      (3, '1') -> (4, '0', Right),
      (4, '0') -> (5, '0', Right),
      (4, '1') -> (4, '1', Right)
    )

  def apply(i: Int, j: Int): Unit = {
    tape = tape ++ ("0" + toUnary(i) + "0" + toUnary(j) + "0").toSeq
    run(0, 0)
    println(s"Result: ${fromUnary(tape mkString)}")
    tape = Seq()
  }
}
