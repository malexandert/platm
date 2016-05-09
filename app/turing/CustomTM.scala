package turing

import scala.collection.mutable

case class CustomTM(
    delta: Map[(State, Char), (State, Char, Direction)],
    q0: State,
    qH: State,
    blankSymbol: Char,
    startPosition: Int)
  extends TM {

  def apply(in: String): String = {
    // Add input to tape
    tape = tape ++ (blankSymbol + in + blankSymbol).toSeq

    // Run the TM and capture the output onto a log
    val (_,_,log) = run(q0, startPosition, "")
    val result = tape mkString ""
    val operationLog = log + s"\nResult: $result"

    // Clear tape for next run
    tape = mutable.Seq.empty[Char]
    operationLog
  }

}
