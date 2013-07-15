/**
 * Copyright Yellowfire
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package za.co.yellowfire.carat.securities;

import java.math.BigDecimal;

/**
 *
 * @author Mark P Ashworth
 * @version 0.1.0
 */
public interface Allocation extends Identifiable<String>, Deliverable {

    String getBroker();
    void setBroker(String buyer);

    String getAccountNumber();
    void setAccountNumber(String accountNumber);

    String getInstrument();
    void setInstrument(String instrument);

    BigDecimal getQuantity();
    void setQuantity(BigDecimal quantity);

    BigDecimal getPrice();
    void setPrice(BigDecimal price);
    
    BigDecimal getMoney();
    void setMoney(BigDecimal money);
}
