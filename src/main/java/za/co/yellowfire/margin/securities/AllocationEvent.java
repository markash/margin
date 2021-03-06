/**
 * Copyright Yellowfire
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may
 * not use this file except in compliance with the License. You may obtain
 * a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package za.co.yellowfire.margin.securities;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.joda.time.LocalDate;
import za.co.yellowfire.carat.event.Event;
import za.co.yellowfire.carat.securities.Allocation;
import za.co.yellowfire.carat.securities.Deliverable;

import java.io.Serializable;
import java.math.BigDecimal;

@ToString
public class AllocationEvent implements Event, Allocation, Deliverable {
    /**
     * The allocation id is the identifier that makes the allocation unique across systems and is independent of db id.
     * @return The identifier
     */
    @Getter @Setter
    private String id;

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

    @Getter @Setter
    private LocalDate settlementDate;
}
