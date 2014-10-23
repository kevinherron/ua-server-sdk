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

import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.subscriptions.SubscriptionManager;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.application.services.SubscriptionServiceSet;
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
import com.inductiveautomation.opcua.stack.core.types.structured.TransferSubscriptionsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TransferSubscriptionsResponse;

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
        TransferSubscriptionsRequest request = service.getRequest();

        OpcUaServer server = service.attr(ServiceAttributes.ServerKey).get();
        request.getSubscriptionIds();

        service.setServiceFault(StatusCodes.Bad_ServiceUnsupported);
    }

}
