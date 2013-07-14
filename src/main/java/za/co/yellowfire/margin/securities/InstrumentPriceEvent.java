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
import za.co.yellowfire.carat.event.Event;

import java.math.BigDecimal;

public class InstrumentPriceEvent implements Event {

    @Getter @Setter
    private String instrument;

    @Getter @Setter
    private BigDecimal price;
}
