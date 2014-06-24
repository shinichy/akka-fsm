import akka.actor.ActorSystem
import akka.testkit.{TestProbe, TestKit, ImplicitSender}
import org.scalatest.WordSpec
import scala.concurrent.duration._

class ActorBusImplTest extends TestKit(ActorSystem("testSystem")) with ImplicitSender
with WordSpec {
  "ActorBusImplTest" must {
    "" in {
      val observer1 = TestProbe().ref
      val observer2 = TestProbe().ref
      val probe1 = TestProbe()
      val probe2 = TestProbe()
      val subscriber1 = probe1.ref
      val subscriber2 = probe2.ref
      val actorBus = new ActorBusImpl
      actorBus.subscribe(subscriber1, observer1)
      actorBus.subscribe(subscriber2, observer1)
      actorBus.subscribe(subscriber2, observer2)
      actorBus.publish(Notification(observer1, 100))
      probe1.expectMsg(Notification(observer1, 100))
      probe2.expectMsg(Notification(observer1, 100))
      actorBus.publish(Notification(observer2, 101))
      probe2.expectMsg(Notification(observer2, 101))
      probe1.expectNoMsg(500.millis)
    }
  }
}


