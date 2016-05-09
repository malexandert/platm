package turing

import scala.collection.mutable

/**
 * A TM object to add two numbers in binary. There are 6 states and 4 symbols
 */
object BinaryAdditionTM extends TM {
  val q0 = 2
  val qH = 5
  val blankSymbol = '#'

  val delta = Map(
    // State 0 subtracts 1 from the 2nd input
    (0, '0') -> (0, '1', Left),
    (0, '1') -> (1, '0', Left),
    (0, '+') -> (4, '#', Right),
    (0, '#') -> (0, '#', Right),

    // State 1 finds the plus sign
    (1, '0') -> (1, '0', Left),
    (1, '1') -> (1, '1', Left),
    (1, '+') -> (3, '+', Left),
    (1, '#') -> (1, '#', Left),

    // State 2 (the state state) finds the right end of the 2nd input
    (2, '0') -> (2, '0', Right),
    (2, '1') -> (2, '1', Right),
    (2, '+') -> (2, '+', Right),
    (2, '#') -> (0, '#', Left),

    // State 3 adds one to the first input
    (3, '0') -> (2, '1', Right),
    (3, '1') -> (3, '0', Left),
    (3, '#') -> (2, '1', Right),

    // State 4 cleans up by removing the 2nd input and leaving the sum intact
    (4, '1') -> (4, '#', Right),
    (4, '#') -> (5, '#', Still)

    // State 5 is the halting state
  )

  /**
   * Runs the TM object. Used like BinaryAdditionTM(i,j)
   */
  def apply(i: Int, j: Int): String = {
    // Format input onto tape
    tape = tape ++ ("#" + i.toBinaryString + "+" + j.toBinaryString + "#").toSeq

    // Run the TM and capture the output in a log
    val (_,_,log) = run(q0, 1, "")
    val result = (tape mkString "") filter (c => c == '1' || c == '0')
    val operationLog = log + s"\nResult: $result (${Integer.parseInt(result, 2)})"

    // Clear tape for next run
    tape = mutable.Seq.empty[Char]
    operationLog
  }
}
