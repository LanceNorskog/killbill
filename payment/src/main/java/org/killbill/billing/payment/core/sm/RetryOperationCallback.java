/*
 * Copyright 2014 Groupon, Inc
 *
 * Groupon licenses this file to you under the Apache License, version 2.0
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

package org.killbill.billing.payment.core.sm;

import java.util.Set;

import javax.annotation.Nullable;

import org.joda.time.DateTime;
import org.killbill.automaton.Operation.OperationCallback;
import org.killbill.automaton.OperationException;
import org.killbill.automaton.OperationResult;
import org.killbill.billing.osgi.api.OSGIServiceRegistration;
import org.killbill.billing.payment.api.PaymentApiException;
import org.killbill.billing.payment.core.DirectPaymentProcessor;
import org.killbill.billing.payment.core.ProcessorBase.WithAccountLockCallback;
import org.killbill.billing.payment.dispatcher.PluginDispatcher;
import org.killbill.billing.retry.plugin.api.RetryPluginApi;
import org.killbill.billing.retry.plugin.api.RetryPluginApiException;
import org.killbill.commons.locker.GlobalLocker;

public abstract class RetryOperationCallback extends PluginOperation implements OperationCallback {

    protected final DirectPaymentProcessor directPaymentProcessor;
    private final OSGIServiceRegistration<RetryPluginApi> retryPluginRegistry;

    protected RetryOperationCallback(final GlobalLocker locker, final PluginDispatcher<OperationResult> paymentPluginDispatcher, final RetryableDirectPaymentStateContext directPaymentStateContext, final DirectPaymentProcessor directPaymentProcessor, final OSGIServiceRegistration<RetryPluginApi> retryPluginRegistry) {
        super(locker, paymentPluginDispatcher, directPaymentStateContext);
        this.directPaymentProcessor = directPaymentProcessor;
        this.retryPluginRegistry = retryPluginRegistry;
    }

    //
    // STEPH issue because externalKey namespace across plugin is not unique
    // If there is no plugin
    //
    private boolean isRetryAborted(final String externalKey, @Nullable final String pluginName) {

        if (pluginName != null) {
            final RetryPluginApi plugin = retryPluginRegistry.getServiceForName(pluginName);
            try {
                final boolean aborted = plugin.isRetryAborted(externalKey);
                return aborted;
            } catch (RetryPluginApiException e) {
                return true;
            }
        }

        final Set<String> allServices = retryPluginRegistry.getAllServices();
        for (String cur : allServices) {
            final RetryPluginApi plugin = retryPluginRegistry.getServiceForName(cur);
            try {
                final boolean aborted = plugin.isRetryAborted(externalKey);
                if (!aborted) {
                    return false;
                }
            } catch (RetryPluginApiException ignore) {
            }
        }
        return true;
    }

    private DateTime getNextRetryDate(final String externalKey, final String pluginName) {

        if (pluginName != null) {
            final RetryPluginApi plugin = retryPluginRegistry.getServiceForName(pluginName);
            try {
                final DateTime result = plugin.getNextRetryDate(externalKey);
                return result;
            } catch (RetryPluginApiException e) {
                return null;
            }
        }

        final Set<String> allServices = retryPluginRegistry.getAllServices();
        for (String cur : allServices) {
            final RetryPluginApi plugin = retryPluginRegistry.getServiceForName(cur);
            try {
                final DateTime result = plugin.getNextRetryDate(externalKey);
                if (result != null) {
                    return result;
                }
            } catch (RetryPluginApiException ignore) {
            }
        }
        return null;
    }

    @Override
    public OperationResult doOperationCallback() throws OperationException {
        return dispatchWithTimeout(new WithAccountLockCallback<OperationResult>() {
            @Override
            public OperationResult doOperation() throws OperationException {
                if (isRetryAborted(directPaymentStateContext.getDirectPaymentTransactionExternalKey(),
                                   ((RetryableDirectPaymentStateContext) directPaymentStateContext).getPluginName())) {
                    return OperationResult.EXCEPTION;
                }

                try {
                    doPluginOperation();
                } catch (PaymentApiException e) {
                    final DateTime nextRetryDate = getNextRetryDate(directPaymentStateContext.getDirectPaymentTransactionExternalKey(),
                                                                    ((RetryableDirectPaymentStateContext) directPaymentStateContext).getPluginName());
                    if (nextRetryDate == null) {
                        // Very hacky, we are using EXCEPTION result to transition to final ABORTED state.
                        throw new OperationException(e, OperationResult.EXCEPTION);
                    } else {
                        ((RetryableDirectPaymentStateContext) directPaymentStateContext).setRetryDate(nextRetryDate);
                        throw new OperationException(e, OperationResult.FAILURE);
                    }
                } catch (Exception e) {
                    // STEPH_RETRY this will abort the retry logic, is that really what we want?
                    throw new OperationException(e, OperationResult.EXCEPTION);
                }
                return OperationResult.SUCCESS;
            }
        });
    }
}
