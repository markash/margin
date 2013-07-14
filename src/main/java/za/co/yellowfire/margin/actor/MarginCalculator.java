package za.co.yellowfire.margin.actor;

import scala.concurrent.Future;
import za.co.yellowfire.margin.securities.AllocationEvent;
import za.co.yellowfire.margin.securities.InstrumentPriceEvent;

import java.math.BigDecimal;

public interface MarginCalculator {
    Future<BigDecimal> onPosition(AllocationEvent event);

    Future<BigDecimal> onPrice(InstrumentPriceEvent event);
}
