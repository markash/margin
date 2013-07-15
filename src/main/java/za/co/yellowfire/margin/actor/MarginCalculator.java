package za.co.yellowfire.margin.actor;

import scala.concurrent.Future;
import za.co.yellowfire.margin.db.MarginPosition;
import za.co.yellowfire.margin.securities.AllocationEvent;
import za.co.yellowfire.margin.securities.InstrumentPriceEvent;

import java.math.BigDecimal;

public interface MarginCalculator {
    /**
     * Handles new allocation and allocation cancellations into the actor
     * @param event The allocation event
     * @return The aggregate margin calculated value
     */
    Future<BigDecimal> onPosition(AllocationEvent event);

    /**
     * Handles new price messages which are used to mark-to-market the margin
     * @param event The price event
     * @return The aggregate margin calculated value
     */
    Future<BigDecimal> onPrice(InstrumentPriceEvent event);

    Future<MarginPosition[]> marginPositions();
}
