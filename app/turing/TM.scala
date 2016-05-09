package turing

import scala.collection.mutable.Seq

/**
 * An abstract TM class - Marcus Todd
 *
 * The Turing Machines represented by this class are read-first, one tape
 * Turing Machines
 */
abstract class TM {

  def q0: State // The start state
  def qH: State // The end state
  var tape: Seq[Char] = Seq() // The work tape (these TM's only have one)
  def blankSymbol: Char // The abstract blank symbol for the TM

  /**
   * The abstract transition function, implemented by key-value pairs
   * The keys are state/read symbol pairs, and the values are
   * state/write symbol/head move direction. An unspoken convention
   * (TODO: enforce this) is that delta, for all transitions from states that
   * aren't the halting state, is total, but the halting state has no entries
   * in this Map
   */
  def delta: Map[(State, Char), (State, Char, Direction)]


  /**
   * The run function (can be overridden, but is essentially implemented here)
   * This function takes a state, a head position, and a log, and simulates
   * the TM operations from that point until (ideal) halting. To run the whole
   * computation, q = q0, and head = the initial head position, which varies
   * based on computation
   */
  def run(q: State, head: Int, log: String): (State, Int, String) = {
    // An operation log, returned at the end of the run
    val newLog = log + formatTape(head) + s"  (State: $q)\n"
    if (q == qH) { // If the
      (q, head, newLog)
    } else delta(q, tape(head)) match {
      case (qi, sym, Left) =>
        tape update (head, sym)
        if (head == 0) { // Make the left of the tape longer
          tape = blankSymbol +: tape
          run(qi, head, newLog)
        } else run(qi, head - 1, newLog)
      case (qi, sym, Right) =>
        tape update (head, sym)
        if (head == tape.length - 1) { // Make the right of the tape longer
          tape = tape :+ blankSymbol
        }
        run(qi, head + 1, newLog)
      case (qi, sym, Still) =>
        tape update (head, sym)
        run(qi, head, newLog)
    }
  }

  /**
   * A method to format the tape for printing. This simply adds brackets around
   * the element of the tape where the head is and adds tildes (~) to the end
   * to represent the tap extending infinitely in both directions
   */
  protected def formatTape(head: Int): String = {
    "~ " + tape.zipWithIndex.foldLeft(""){case (str, (chr, idx)) =>
      if (idx == head) str + s"[$chr]" else str + s" $chr "
    } + " ~"
  }

}
