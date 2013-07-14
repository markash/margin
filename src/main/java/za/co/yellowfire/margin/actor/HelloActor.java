package za.co.yellowfire.margin.actor;

import akka.actor.UntypedActor;
import akka.actor.Props;
import akka.actor.ActorRef;

public class HelloActor extends UntypedActor {

    public void preStart() {
        final ActorRef greeter =
                getContext().actorOf(Props.create(Greeter.class), "greeter");
        // tell it to perform the greeting
        greeter.tell(Greeter.Msg.GREET, getSelf());
    }

    public void onReceive(Object message) {
        if (message == Greeter.Msg.DONE) {
            getContext().stop(getSelf());
        }
    }
}
