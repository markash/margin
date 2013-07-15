package za.co.yellowfire.margin.actor;

import akka.dispatch.Futures;
import lombok.extern.slf4j.Slf4j;
import scala.concurrent.Future;
import za.co.yellowfire.margin.db.MarginPosition;
import za.co.yellowfire.margin.db.MarginPositionId;
import za.co.yellowfire.margin.securities.AllocationEvent;
import za.co.yellowfire.margin.securities.InstrumentPriceEvent;

import java.math.BigDecimal;
import java.util.Set;
import java.util.TreeSet;

@Slf4j
public class MarginActor implements MarginCalculator {
    private BigDecimal marginPercentage = new BigDecimal("0.10");
    private BigDecimal required = BigDecimal.ZERO;
    private Set<MarginPosition> marginPositions = new TreeSet<>();

    @Override
    public Future<BigDecimal> onPosition(AllocationEvent event) {
        /* Convert the allocation to a margin position */
        MarginPosition marginPosition = convert(event);
        /* Determine if margin position id exists, i.e. an allocation id / settlement date combo cannot be accounted for twice */
        if (!marginPositions.contains(marginPosition)) {
            /* Use a simple 10% of money as the margin required (FOR NOW ;-) */
            required = required.add(marginPosition.getMoney().multiply(marginPercentage));
            /* Add the position to the set */
            marginPositions.add(marginPosition);
        }
        /* Return the total margin requirement */
        log.info("Margin required : {}", required);
        return Futures.successful(required);
    }

    @Override
    public Future<BigDecimal> onPrice(InstrumentPriceEvent event) {
        System.out.println("event = " + event);
        return Futures.successful(new BigDecimal("200"));
    }

    @Override
    public Future<MarginPosition[]> marginPositions() {
        return Futures.successful(marginPositions.toArray(new MarginPosition[marginPositions.size()]));
    }

    private MarginPosition convert(AllocationEvent event) {
        /* Convert the allocation to a margin position, this is simple for now ;-) */
        return new MarginPosition(
            new MarginPositionId(event.getId(), event.getSettlementDate()),
                event.getBroker(),
                event.getAccountNumber(),
                event.getInstrument(),
                event.getQuantity(),
                event.getPrice(),
                event.getMoney(),
                event.getTradeDate()
        );
    }
}