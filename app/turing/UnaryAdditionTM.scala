package turing

import scala.collection.mutable.Seq

/**
 * A TM object to add two numbers in unary. There are 6 states and 2 symbols
 */
object UnaryAdditionTM
    extends TM(Set(0,1,2,3,4,5), Set('0','1'), 0, 5) {
  val q0 = 0
  val qH = 5
  val blankSymbol = '0'

  val delta = Map(
    // State 0 find the start of the first input
    (0, '0') -> (0, '0', Right),
    (0, '1') -> (1, '1', Right),

    // State 1 moves right until finding the middle blank (the "plus")
    (1, '0') -> (2, '1', Right),
    (1, '1') -> (1, '1', Right),

    // State 2 goes until the end of the inputs
    (2, '0') -> (3, '0', Left),
    (2, '1') -> (2, '1', Right),

    // State 3 says "turn around"
    (3, '0') -> (3, '0', Left),
    (3, '1') -> (4, '0', Left),

    // State 4 moves back across the combined input until hitting the left blank
    (4, '0') -> (5, '0', Still),
    (4, '1') -> (4, '1', Left)

    // State 5 is the halt state
  )

  /**
   * Runs the TM object. Used like UnaryAdditionTM(i,j)
   */
  def apply(i: Int, j: Int): String = {
    // Format input onto the tape
    tape = tape ++ ("0" + toUnary(i) + "0" + toUnary(j) + "0").toSeq

    // Run the TM and capture the output onto a log
    val (_,_,log) = run(q0, 0, "")
    val result = tape mkString ""
    val operationLog = log + s"\nResult: $result (${fromUnary(result)})"

    // Clear tape for next run
    tape = Seq()
    operationLog
  }

  // Helper functions for converting between unary and binary
  private def toUnary(i: Int): String =
    (Seq.tabulate(i)(n => '1')) mkString ""

  private def fromUnary(s: String): Int =
    s.foldLeft(0)((n, c) => n + c.asDigit)
}
