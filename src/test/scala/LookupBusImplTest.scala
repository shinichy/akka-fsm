import akka.actor.ActorSystem
import akka.testkit.{TestKit, ImplicitSender}
import org.scalatest.WordSpec

class LookupBusImplTest extends TestKit(ActorSystem("testSystem")) with ImplicitSender
with WordSpec {
  "LookupBusImpl" must {
    "" in {
      val lookupBus = new LookupBusImpl
      lookupBus.subscribe(testActor, "greetings")
      lookupBus.publish(MsgEnvelope("time", System.currentTimeMillis()))
      lookupBus.publish(MsgEnvelope("greetings", "hello"))
      expectMsg("hello")
    }
  }
}
