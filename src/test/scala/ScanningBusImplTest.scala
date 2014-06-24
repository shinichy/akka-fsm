import akka.actor.ActorSystem
import akka.testkit.{TestKit, ImplicitSender}
import org.scalatest.WordSpec

class ScanningBusImplTest extends TestKit(ActorSystem("testSystem")) with ImplicitSender
with WordSpec {
  "ScanningBusImplTest" must {
    "" in {
      val scanningBus = new ScanningBusImpl
      scanningBus.subscribe(testActor, 3)
      scanningBus.publish("xyzabc")
      scanningBus.publish("ab")
      expectMsg("ab")
      scanningBus.publish("abc")
      expectMsg("abc")
    }
  }
}

