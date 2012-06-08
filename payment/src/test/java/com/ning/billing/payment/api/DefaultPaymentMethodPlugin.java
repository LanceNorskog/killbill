/* 
 * Copyright 2010-2011 Ning, Inc.
 *
 * Ning licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.ning.billing.payment.api;

import java.util.List;
import java.util.UUID;

public class DefaultPaymentMethodPlugin implements PaymentMethodPlugin {

    private String externalId;
    private boolean isDefault;
    private List<PaymentMethodKVInfo> props;
    
    public DefaultPaymentMethodPlugin(PaymentMethodPlugin src) {
        this.externalId = UUID.randomUUID().toString();
        this.isDefault = src.isDefaultPaymentMethod();
        this.props = src.getProperties();
    }
    
    public DefaultPaymentMethodPlugin(String externalId, boolean isDefault,
            List<PaymentMethodKVInfo> props) {
        super();
        this.externalId = externalId;
        this.isDefault = isDefault;
        this.props = props;
    }

    @Override
    public String getExternalPaymentMethodId() {
        return externalId;
    }

    @Override
    public boolean isDefaultPaymentMethod() {
        return isDefault;
    }

    @Override
    public List<PaymentMethodKVInfo> getProperties() {
        return props;
    }

    public void setExternalId(String externalId) {
        this.externalId = externalId;
    }

    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    public void setProps(List<PaymentMethodKVInfo> props) {
        this.props = props;
    }

    @Override
    public String getValueString(String key) {
        if (props == null) {
            return null;
        }
        for (PaymentMethodKVInfo cur : props) {
            if (cur.getKey().equals(key)) {
                return cur.getValue().toString();
            }
        }
        return null;
    }
}