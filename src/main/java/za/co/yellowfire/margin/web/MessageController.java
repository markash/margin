package za.co.yellowfire.margin.web;

import akka.actor.ActorRef;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import za.co.yellowfire.margin.actor.MarginCalculator;
import za.co.yellowfire.margin.annotations.Actor;
import za.co.yellowfire.margin.annotations.Margin;
import za.co.yellowfire.margin.securities.AllocationEvent;

import javax.faces.bean.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

@RequestScoped @Slf4j @Named
public class MessageController  {

    @Getter @Setter
    private String name;

    @Inject @Margin @Actor
    private MarginCalculator marginCalculator;

    public String onSubmit() {
        System.out.println("name = " + name);
        marginCalculator.onPosition(new AllocationEvent());
        return "success";
    }
}
