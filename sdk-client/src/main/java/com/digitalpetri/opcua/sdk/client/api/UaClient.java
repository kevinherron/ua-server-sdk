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

package com.digitalpetri.opcua.sdk.client.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.OpcUaClientConfig;
import com.digitalpetri.opcua.sdk.client.api.services.AttributeServices;
import com.digitalpetri.opcua.sdk.client.api.services.MonitoredItemServices;
import com.digitalpetri.opcua.sdk.client.api.services.ViewServices;
import com.digitalpetri.opcua.sdk.client.api.services.SubscriptionServices;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.SignatureData;
import com.digitalpetri.opcua.stack.core.types.structured.UserIdentityToken;

public interface UaClient extends AttributeServices, MonitoredItemServices, SubscriptionServices, ViewServices {

    OpcUaClientConfig getConfig();

    CompletableFuture<UaClient> connect();

    CompletableFuture<UaClient> disconnect();

    CompletableFuture<UaSession> getSession();

    <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request);

    void sendRequests(List<? extends UaRequestMessage> requests,
                      List<CompletableFuture<? extends UaResponseMessage>> futures);

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
