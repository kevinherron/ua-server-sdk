/*
 * Copyright 2014 Inductive Automation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.inductiveautomation.opcua.sdk.server.services;

import java.util.List;
import java.util.Objects;

import com.google.common.collect.Lists;
import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.Session;
import com.inductiveautomation.opcua.sdk.server.items.MonitoredDataItem;
import com.inductiveautomation.opcua.sdk.server.subscriptions.Subscription;
import com.inductiveautomation.opcua.sdk.server.subscriptions.SubscriptionManager;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.application.services.SubscriptionServiceSet;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteSubscriptionsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteSubscriptionsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifySubscriptionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifySubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RepublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RepublishResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.TransferResult;
import com.inductiveautomation.opcua.stack.core.types.structured.TransferSubscriptionsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TransferSubscriptionsResponse;

import static com.inductiveautomation.opcua.sdk.server.util.ConversionUtil.a;

public class SubscriptionServices implements SubscriptionServiceSet {

    private final SubscriptionManager subscriptionManager;

    public SubscriptionServices(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    @Override
    public void onCreateSubscription(ServiceRequest<CreateSubscriptionRequest, CreateSubscriptionResponse> service) {
        subscriptionManager.createSubscription(service);
    }

    @Override
    public void onModifySubscription(ServiceRequest<ModifySubscriptionRequest, ModifySubscriptionResponse> service) {
        subscriptionManager.modifySubscription(service);
    }

    @Override
    public void onDeleteSubscriptions(ServiceRequest<DeleteSubscriptionsRequest, DeleteSubscriptionsResponse> service) {
        subscriptionManager.deleteSubscription(service);
    }

    @Override
    public void onSetPublishingMode(ServiceRequest<SetPublishingModeRequest, SetPublishingModeResponse> service) {
        subscriptionManager.setPublishingMode(service);
    }

    @Override
    public void onPublish(ServiceRequest<PublishRequest, PublishResponse> service) {
        subscriptionManager.publish(service);
    }

    @Override
    public void onRepublish(ServiceRequest<RepublishRequest, RepublishResponse> service) {
        subscriptionManager.republish(service);
    }

    @Override
    public void onTransferSubscriptions(ServiceRequest<TransferSubscriptionsRequest, TransferSubscriptionsResponse> service) {
        OpcUaServer server = service.attr(ServiceAttributes.ServerKey).get();
        Session session = service.attr(ServiceAttributes.SessionKey).get();

        TransferSubscriptionsRequest request = service.getRequest();
        UInteger[] subscriptionIds = request.getSubscriptionIds();

        if (subscriptionIds.length == 0) {
            service.setServiceFault(StatusCodes.Bad_NothingToDo);
            return;
        }

        List<TransferResult> results = Lists.newArrayList();

        for (UInteger subscriptionId : subscriptionIds) {
            Subscription subscription = server.getSubscriptions().get(subscriptionId);

            if (subscription == null) {
                results.add(new TransferResult(new StatusCode(StatusCodes.Bad_SubscriptionIdInvalid), new UInteger[0]));
            } else {
                Session otherSession = subscription.getSession();

                if (!sessionsHaveSameUser(session, otherSession)) {
                    results.add(new TransferResult(new StatusCode(StatusCodes.Bad_UserAccessDenied), new UInteger[0]));
                } else {
                    UInteger[] availableSequenceNumbers;

                    synchronized (subscription) {
                        otherSession.getSubscriptionManager().sendStatusChangeNotification(subscription);
                        otherSession.getSubscriptionManager().removeSubscription(subscriptionId);

                        subscription.setSubscriptionManager(session.getSubscriptionManager());
                        subscriptionManager.addSubscription(subscription);

                        availableSequenceNumbers = subscription.getAvailableSequenceNumbers();

                        if (request.getSendInitialValues()) {
                            subscription.getMonitoredItems().values().stream()
                                    .filter(item -> item instanceof MonitoredDataItem)
                                    .map(item -> (MonitoredDataItem) item)
                                    .forEach(MonitoredDataItem::clearLastValue);
                        }
                    }

                    results.add(new TransferResult(StatusCode.Good, availableSequenceNumbers));
                }
            }
        }

        service.setResponse(new TransferSubscriptionsResponse(
                service.createResponseHeader(),
                a(results, TransferResult.class),
                new DiagnosticInfo[0]
        ));
    }

    private boolean sessionsHaveSameUser(Session s1, Session s2) {
        Object identity1 = s1.getIdentityObject();
        Object identity2 = s2.getIdentityObject();

        return Objects.equals(identity1, identity2);
    }

}
