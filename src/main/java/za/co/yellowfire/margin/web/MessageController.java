package za.co.yellowfire.margin.web;

import akka.dispatch.OnSuccess;
import akka.util.Timeout;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.joda.time.DurationFieldType;
import org.joda.time.LocalDate;
import org.omnifaces.util.Faces;
import scala.concurrent.ExecutionContext;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import za.co.yellowfire.carat.db.Dao;
import za.co.yellowfire.carat.db.DataAccessException;
import za.co.yellowfire.margin.actor.MarginCalculator;
import za.co.yellowfire.carat.web.MemoryPageableDataModel;
import za.co.yellowfire.margin.annotations.Actor;
import za.co.yellowfire.margin.annotations.Margin;
import za.co.yellowfire.margin.db.MarginPosition;
import za.co.yellowfire.margin.securities.AllocationEvent;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@ViewScoped @Slf4j @Named
public class MessageController  {

    @Getter @Setter
    private AllocationEvent allocationEvent;

    @Inject @Margin @Actor
    private MarginCalculator marginCalculator;
    @Inject @Actor
    private ExecutionContext executionContext;

    @Getter
    private MemoryPageableDataModel<MarginPosition> marginPositions;

    @PostConstruct
    public void init() {
        onReset();
        try {
            marginPositions = new MemoryPageableDataModel<MarginPosition>(new MarginPostionDao());
        } catch (DataAccessException e) {
            log.error("Unable to retrieve margin positions", e);
            Faces.getContext().addMessage(null, new FacesMessage("Page error", e.getMessage()));
        }
    }

    public String onSubmit() {
        Timeout timeout = new Timeout(Duration.create(5, "seconds"));
        marginCalculator.onPosition(allocationEvent);
        try {
            marginPositions.onRefresh();
        } catch (DataAccessException e) {
            log.error("Unable to refresh margin positions data model", e);
        }

        return null;
    }

    public String onReset() {
        /* Use defunct 1Time share as the instrument */
        /* For now perform a rudimentary T+3 settlement date calculation which includes weekends */
        allocationEvent = new AllocationEvent();
        allocationEvent.setTradeDate(new LocalDate());
        allocationEvent.setSettlementDate(new LocalDate().withFieldAdded(DurationFieldType.days(), 5));
        allocationEvent.setInstrument("1TM");
        allocationEvent.setQuantity(BigDecimal.ZERO);
        allocationEvent.setPrice(new BigDecimal("1.53"));
        allocationEvent.setMoney(BigDecimal.ZERO);
        return null;
    }

    private class MarginPostionDao implements Dao<MarginPosition> {
        @Override
        public List<MarginPosition> retrieve() throws DataAccessException {
            MarginPositionsResult result = new MarginPositionsResult();
            Future<MarginPosition[]> positionsFuture = marginCalculator.marginPositions();
            positionsFuture.onSuccess(result, executionContext);
            return result.results;
        }
    }

    private class MarginPositionsResult extends OnSuccess<MarginPosition[]> {
        private List<MarginPosition> results = new ArrayList<>();
        public void onSuccess(MarginPosition[] result) throws Throwable {
            results.clear();
            results.addAll(Arrays.asList(result));
        }
    }
}
