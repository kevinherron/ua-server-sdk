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

package com.inductiveautomation.opcua.sdk.client.fsm.states;

import com.inductiveautomation.opcua.sdk.client.OpcUaClient;
import com.inductiveautomation.opcua.sdk.client.fsm.ClientState;
import com.inductiveautomation.opcua.sdk.client.fsm.ClientStateContext;
import com.inductiveautomation.opcua.sdk.client.fsm.ClientStateEvent;
import com.inductiveautomation.opcua.stack.client.UaTcpClient;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSessionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSessionResponse;
import com.inductiveautomation.opcua.stack.core.util.NonceUtil;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class CreatingSession implements ClientState {

    private final AtomicReference<CreateSessionResponse> response = new AtomicReference<>();

    @Override
    public void activate(ClientStateEvent event, ClientStateContext context) {
        OpcUaClient client = context.getClient();
        UaTcpClient stackClient = client.getStackClient();

        CreateSessionRequest request = new CreateSessionRequest(
                client.newRequestHeader(),
                stackClient.getApplication(),
                null,
                stackClient.getEndpointUrl(),
                client.getConfig().getSessionName().get(),
                NonceUtil.generateNonce(16),
                ByteString.NULL_VALUE,
                client.getConfig().getSessionTimeout(),
                client.getConfig().getMaxResponseMessageSize());

        CompletableFuture<CreateSessionResponse> future = stackClient.sendRequest(request);

        future.whenComplete((r, ex) -> {
            if (r != null) {
                response.set(r);
                context.handleEvent(ClientStateEvent.CREATE_SESSION_SUCCEEDED);
            } else {
                context.handleEvent(ClientStateEvent.CREATE_SESSION_FAILED);
            }
        });
    }

    @Override
    public ClientState transition(ClientStateEvent event, ClientStateContext context) {
        switch (event) {
            case CREATE_SESSION_FAILED:
                return new Inactive();

            case CREATE_SESSION_SUCCEEDED:
                return new ActivatingSession(response.get());
        }

        return this;
    }

}
