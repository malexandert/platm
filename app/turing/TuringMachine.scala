package turing


abstract class TuringMachine(
    Q: Set[State],
    Sigma: Alphabet,
    q0: State,
    qH: State) {
  require((Q contains q0) && (Q contains qH))

  var tape: String = ""

  def delta: Map[(State, Char), (State, Char, Direction)]

}
