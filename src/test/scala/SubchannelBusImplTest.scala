import akka.actor.ActorSystem
import akka.testkit.{TestKit, ImplicitSender}
import org.scalatest.WordSpec

class SubchannelBusImplTest extends TestKit(ActorSystem("testSystem")) with ImplicitSender
with WordSpec {
  "SubchannelBusImplTest" must {
    "" in {
      val subchannelBus = new SubchannelBusImpl
      subchannelBus.subscribe(testActor, "abc")
      subchannelBus.publish(MsgEnvelope("xyzabc", "x"))
      subchannelBus.publish(MsgEnvelope("bcdef", "b"))
      subchannelBus.publish(MsgEnvelope("abc", "c"))
      expectMsg("c")
      subchannelBus.publish(MsgEnvelope("abcdef", "d"))
      expectMsg("d")
    }
  }
}


