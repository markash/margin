package za.co.yellowfire.margin.db;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.joda.time.LocalDate;

import java.io.Serializable;

@AllArgsConstructor @EqualsAndHashCode @ToString
public class MarginPositionId implements Serializable, Comparable<MarginPositionId> {
    @Getter
    private String allocationId;

    @Getter
    private LocalDate settlementDate;

    @Override
    public int compareTo(MarginPositionId o) {
        int result = 0;
        String otherId = allocationId;
        LocalDate otherSettleDate = settlementDate;

        if (o != null) {
            otherId = o.getAllocationId();
            otherSettleDate = o.getSettlementDate();
        }

        if (allocationId != null && otherId != null) {
            result = allocationId.compareTo(otherId);
        } else if (allocationId == null && otherId != null) {
            result = "".compareTo(otherId);
        } else if (allocationId != null && otherId == null) {
            result = allocationId.compareTo("");
        } else {
            result = 0;
        }

        if (settlementDate != null && otherSettleDate != null) {
            result = result + settlementDate.compareTo(otherSettleDate);
        } else if (settlementDate == null && otherSettleDate != null) {
            result = result - 1;
        } else if (settlementDate != null && otherSettleDate == null) {
            result = result + 1;
        } else {
            result = result + 0;
        }

        return result;
    }
}
