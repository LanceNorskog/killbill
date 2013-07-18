/*
 * Copyright 2010-2013 Ning, Inc.
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

package com.ning.billing.beatrix.util;

import com.ning.billing.entitlement.api.BlockingSubscription;
import com.ning.billing.subscription.api.user.Subscription;
import com.ning.billing.subscription.api.user.SubscriptionBundle;
import com.ning.billing.subscription.api.user.SubscriptionData;
import com.ning.billing.subscription.api.user.SubscriptionTransition;
import com.ning.billing.subscription.api.user.SubscriptionTransitionData;
import com.ning.billing.subscription.api.user.SubscriptionUserApi;
import com.ning.billing.subscription.api.user.SubscriptionUserApiException;
import com.ning.billing.util.callcontext.CallContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;

import javax.inject.Inject;
import java.util.List;
import java.util.UUID;

public class SubscriptionChecker {


    private static final Logger log = LoggerFactory.getLogger(SubscriptionChecker.class);

    private final SubscriptionUserApi subscriptionApi;
    private final AuditChecker auditChecker;

    @Inject
    public SubscriptionChecker(final SubscriptionUserApi subscriptionApi, final AuditChecker auditChecker) {
        this.subscriptionApi = subscriptionApi;
        this.auditChecker = auditChecker;
    }

    public SubscriptionBundle checkBundleNoAudits(final UUID bundleId, final UUID expectedAccountId, final String expectedKey, final CallContext context) throws SubscriptionUserApiException {
        final SubscriptionBundle bundle = subscriptionApi.getBundleFromId(bundleId, context);
        Assert.assertNotNull(bundle);
        Assert.assertEquals(bundle.getAccountId(), expectedAccountId);
        Assert.assertEquals(bundle.getExternalKey(), expectedKey);
        return bundle;
    }

    public SubscriptionBundle checkBundleAuditUpdated(final UUID bundleId, final CallContext context) throws SubscriptionUserApiException {
        final SubscriptionBundle bundle = subscriptionApi.getBundleFromId(bundleId, context);
        auditChecker.checkBundleUpdated(bundle.getId(), context);
        return bundle;
    }

    public Subscription checkSubscriptionCreated(final UUID subscriptionId, final CallContext context) throws SubscriptionUserApiException {
        final Subscription subscription = subscriptionApi.getSubscriptionFromId(subscriptionId, context);
        Assert.assertNotNull(subscription);
        auditChecker.checkSubscriptionCreated(subscription.getBundleId(), subscriptionId, context);

        List<SubscriptionTransition> subscriptionEvents = getSubscriptionEvents(subscription);
        Assert.assertTrue(subscriptionEvents.size() >= 1);
        auditChecker.checkSubscriptionEventCreated(subscription.getBundleId(), ((SubscriptionTransitionData) subscriptionEvents.get(0)).getId(), context);

        auditChecker.checkBundleCreated(subscription.getBundleId(), context);
        return subscription;
    }

    private List<SubscriptionTransition> getSubscriptionEvents(final Subscription subscription) {
        return ((SubscriptionData) ((BlockingSubscription) subscription).getDelegateSubscription()).getAllTransitions();
    }


}