package za.co.yellowfire.margin;

import akka.actor.ActorSystem;
import akka.actor.TypedActor;
import akka.actor.TypedProps;
import akka.util.Timeout;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.FiniteDuration;
import za.co.yellowfire.margin.actor.MarginActor;
import za.co.yellowfire.margin.actor.MarginCalculator;
import za.co.yellowfire.margin.annotations.Actor;
import za.co.yellowfire.margin.annotations.Margin;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Named;
import java.util.concurrent.TimeUnit;

@ApplicationScoped @Named
public class ApplicationController {
    private ActorSystem system;
    private MarginCalculator marginCalculator;

    @PostConstruct
    public void init() {
        Timeout timeout = new Timeout(FiniteDuration.create(5, TimeUnit.SECONDS));
        system = ActorSystem.create("MarginSystem");
        createMarginActor();
    }


    @Produces @Actor
    public ExecutionContext getExecutionContext() {
        return system.dispatcher();
    }

    @Produces @Margin @Actor
    public MarginCalculator getMarginActor() {
        if (this.marginCalculator == null) {
            createMarginActor();
        }
        return this.marginCalculator;
    }

    private void createMarginActor() {
        marginCalculator = TypedActor.get(system)
                .typedActorOf(new TypedProps<MarginActor>(MarginCalculator.class, MarginActor.class));
    }
}
