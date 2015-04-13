/*
 * Copyright 2015
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

package com.inductiveautomation.opcua.sdk.client.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.sdk.client.OpcUaClientConfig;
import com.inductiveautomation.opcua.sdk.client.api.services.AttributeServices;
import com.inductiveautomation.opcua.sdk.client.api.services.SubscriptionServices;
import com.inductiveautomation.opcua.sdk.client.api.services.ViewServices;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteSubscriptionsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifySubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemModifyRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SignatureData;
import com.inductiveautomation.opcua.stack.core.types.structured.TransferSubscriptionsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.UserIdentityToken;

public interface UaClient extends AttributeServices, SubscriptionServices, ViewServices {

    OpcUaClientConfig getConfig();

    CompletableFuture<UaClient> connect();

    CompletableFuture<UaClient> disconnect();

    CompletableFuture<UaSession> getSession();

    <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request);

    CompletableFuture<CreateMonitoredItemsResponse> createMonitoredItems(UInteger subscriptionId,
                                                                         TimestampsToReturn timestampsToReturn,
                                                                         List<MonitoredItemCreateRequest> itemsToCreate);

    CompletableFuture<ModifyMonitoredItemsResponse> modifyMonitoredItems(UInteger subscriptionId,
                                                                         TimestampsToReturn timestampsToReturn,
                                                                         List<MonitoredItemModifyRequest> itemsToModify);

    CompletableFuture<DeleteMonitoredItemsResponse> deleteMonitoredItems(UInteger subscriptionId,
                                                                         List<UInteger> monitoredItemIds);

    CompletableFuture<SetMonitoringModeResponse> setMonitoringMode(UInteger subscriptionId,
                                                                   MonitoringMode monitoringMode,
                                                                   List<UInteger> monitoredItemIds);

    CompletableFuture<SetTriggeringResponse> setTriggering(UInteger subscriptionId,
                                                           UInteger triggeringItemId,
                                                           List<UInteger> linksToAdd,
                                                           List<UInteger> linksToRemove);


    interface ClientCallback {
        void onLateResponse(UaResponseMessage response);
    }

    interface IdentityTokenProvider {

        /**
         * Return the {@link UserIdentityToken} and {@link SignatureData} (if applicable for the token) to use when
         * activating a session.
         *
         * @param endpoint the {@link EndpointDescription} being connected to.
         * @return an Object[] containing the {@link UserIdentityToken} at index 0 and the {@link SignatureData} at index 1.
         */
        Object[] getIdentityToken(EndpointDescription endpoint) throws Exception;

    }

}
