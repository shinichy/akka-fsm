import akka.actor.ActorRef
import scala.collection.immutable

/**
 * @author shinichi
 */
object Main extends App {
}

// received events
case class SetTarget(ref: ActorRef)
case class Queue(obj: Any)
case object Flush

// sent events
case class Batch(obj: immutable.Seq[Any])

// states
sealed trait State
case object Idle extends State
case object Active extends State

sealed trait Data
case object Uninitialized extends Data
case class Todo(target: ActorRef, queue: immutable.Seq[Any]) extends Data
