import akka.actor.{ActorSystem, FSM, Actor, Props}
import akka.testkit.{ImplicitSender, TestKit}
import org.scalatest.{WordSpec, BeforeAndAfterAll}
import scala.collection.immutable

class FSMDocSpec extends TestKit(ActorSystem("testSystem")) with ImplicitSender
with WordSpec with BeforeAndAfterAll
{
  "simple finite state machine" must {

    "demonstrate NullFunction" in {
      class A extends Actor with FSM[Int, Null] {
        val SomeState = 0
        when(SomeState)(FSM.NullFunction)
      }
    }

    "batch correctly" in {
      val buncher = system.actorOf(Props(new Buncher))
      buncher ! SetTarget(testActor)
      buncher ! Queue(42)
      buncher ! Queue(43)
      expectMsg(Batch(immutable.Seq(42, 43)))
      buncher ! Queue(44)
      buncher ! Flush
      buncher ! Queue(45)
      expectMsg(Batch(immutable.Seq(44)))
      expectMsg(Batch(immutable.Seq(45)))
    }

    "not batch if uninitialized" in {
      val buncher = system.actorOf(Props(new Buncher))
      buncher ! Queue(42)
      expectNoMsg
    }
  }
}
