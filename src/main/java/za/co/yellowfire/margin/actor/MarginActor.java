package za.co.yellowfire.margin.actor;

import akka.dispatch.Futures;
import scala.concurrent.Future;
import za.co.yellowfire.margin.securities.AllocationEvent;
import za.co.yellowfire.margin.securities.InstrumentPriceEvent;

import java.math.BigDecimal;

public class MarginActor implements MarginCalculator {

    @Override
    public Future<BigDecimal> onPosition(AllocationEvent event) {
        System.out.println("event = " + event);
        return Futures.successful(new BigDecimal("100"));
    }

    @Override
    public Future<BigDecimal> onPrice(InstrumentPriceEvent event) {
        System.out.println("event = " + event);
        return Futures.successful(new BigDecimal("200"));
    }
}