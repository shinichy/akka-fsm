import akka.actor.{ Actor, FSM }
import scala.concurrent.duration._

class Buncher extends Actor with FSM[State, Data] {

  // initial state and data
  startWith(Idle, Uninitialized)

  when(Idle) {
    case Event(SetTarget(ref), Uninitialized) =>
      stay using Todo(ref, Vector.empty)
  }

  when(Active, stateTimeout = 1 second) {
    case Event(Flush | StateTimeout, t: Todo) =>
      goto(Idle) using t.copy(queue = Vector.empty)
  }

  whenUnhandled {
    // common code for both states
    case Event(Queue(obj), t @ Todo(_, v)) =>
      goto(Active) using t.copy(queue = v :+ obj)

    case Event(e, s) =>
      log.warning("received unhandled request {} in state {}/{}", e, stateName, s)
      stay
  }

  onTransition {
    case Active -> Idle =>
      stateData match {
        case Todo(ref, queue) => ref ! Batch(queue)
      }
  }


  initialize()
}
