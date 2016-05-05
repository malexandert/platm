
package object turing {
  sealed trait Direction
  case object Left extends Direction
  case object Right extends Direction
  case object Still extends Direction
  type State = Int

  type Alphabet = Set[Char]
  type Transition = ((State, Char), (State, Char, Direction))
}
