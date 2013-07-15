package za.co.yellowfire.margin.db;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.LocalDate;
import za.co.yellowfire.carat.securities.Identifiable;

import java.io.Serializable;
import java.math.BigDecimal;

@AllArgsConstructor @ToString
public class MarginPosition implements Identifiable<MarginPositionId>, Serializable, Comparable<MarginPosition> {

    @Getter
    private MarginPositionId id;

    @Getter @Setter
    private String broker;

    @Getter @Setter
    private String accountNumber;

    @Getter @Setter
    private String instrument;

    @Getter @Setter
    private BigDecimal quantity;

    @Getter @Setter
    private BigDecimal price;

    @Getter @Setter
    private BigDecimal money;

    @Getter @Setter
    private LocalDate tradeDate;

    @Override
    public int compareTo(MarginPosition o) {
        if (id == null && o == null) {
            return 0;
        } else if (id != null) {
            return id.compareTo(o.getId());
        } else {
            return o.getId().compareTo(id);
        }
    }
}
