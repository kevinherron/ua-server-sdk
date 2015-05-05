/*
 * digitalpetri OPC-UA SDK
 *
 * Copyright (C) 2015 Kevin Herron
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.digitalpetri.opcua.sdk.server.services;

import java.util.List;
import java.util.Objects;

import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.Session;
import com.digitalpetri.opcua.sdk.server.items.MonitoredDataItem;
import com.digitalpetri.opcua.sdk.server.subscriptions.Subscription;
import com.digitalpetri.opcua.sdk.server.subscriptions.SubscriptionManager;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequest;
import com.digitalpetri.opcua.stack.core.application.services.SubscriptionServiceSet;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSubscriptionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteSubscriptionsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteSubscriptionsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ModifySubscriptionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ModifySubscriptionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.PublishRequest;
import com.digitalpetri.opcua.stack.core.types.structured.PublishResponse;
import com.digitalpetri.opcua.stack.core.types.structured.RepublishRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RepublishResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SetPublishingModeRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetPublishingModeResponse;
import com.digitalpetri.opcua.stack.core.types.structured.TransferResult;
import com.digitalpetri.opcua.stack.core.types.structured.TransferSubscriptionsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TransferSubscriptionsResponse;
import com.google.common.collect.Lists;

import static com.digitalpetri.opcua.sdk.core.util.ConversionUtil.a;

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
        OpcUaServer server = service.attr(ServiceAttributes.SERVER_KEY).get();
        Session session = service.attr(ServiceAttributes.SESSION_KEY).get();

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

                    results.add(new TransferResult(StatusCode.GOOD, availableSequenceNumbers));
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
