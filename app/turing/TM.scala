package turing

import scala.collection.mutable.Seq

abstract class TM(
    Q: Set[State],
    Sigma: Alphabet,
    q0: State,
    qH: State) {
  require((Q contains q0) && (Q contains qH))

  var tape: Seq[Char] = Seq()

  def delta: Map[(State, Char), (State, Char, Direction)]

  def run(q: State, head: Int): (State, Int) = {
    println(formatTape(head))
    if (q == qH) {
      (q, head)
    } else delta(q, tape(head)) match {
      case (qi, sym, Left) =>
        tape update (head, sym)
        if (head == 0) {
          tape = '0' +: tape
          run(qi, head)
        } else run(qi, head - 1)
      case (qi, sym, Right) =>
        tape update (head, sym)
        if (head == tape.length - 1) {
          tape = tape :+ '0'
        }
        run(qi, head + 1)
      case (qi, sym, Still) =>
        tape update (head, sym)
        run(qi, head)
    }
  }

  protected def toUnary(i: Int): String =
    (Seq.tabulate(i)(n => '1')) mkString ""

  protected def fromUnary(s: String): Int =
    s.foldLeft(0)((n, c) => n + c.asDigit)

  protected def formatTape(head: Int): String = {
    tape.zipWithIndex.foldLeft(""){case (str, (chr, idx)) =>
      if (idx == head) str + s"[$chr]" else str + s" $chr "
    }
  }

}
