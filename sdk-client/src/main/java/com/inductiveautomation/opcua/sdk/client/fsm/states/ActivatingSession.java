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
import com.inductiveautomation.opcua.stack.core.types.structured.*;

import java.util.concurrent.CompletableFuture;

public class ActivatingSession implements ClientState {

    private final CreateSessionResponse createSessionResponse;

    public ActivatingSession(CreateSessionResponse createSessionResponse) {
        this.createSessionResponse = createSessionResponse;
    }

    @Override
    public void activate(ClientStateEvent event, ClientStateContext context) {
        OpcUaClient client = context.getClient();
        UaTcpClient stackClient = client.getStackClient();

        ActivateSessionRequest request = new ActivateSessionRequest(
                client.newRequestHeader(),
                new SignatureData(null, null),
                new SignedSoftwareCertificate[0],
                new String[0],
                null,
                new SignatureData(null, null)
        );

        CompletableFuture<ActivateSessionResponse> future = stackClient.sendRequest(request);

        future.whenComplete((r, ex) -> {
            if (r != null) {

            } else {

            }
        });
    }

    @Override
    public ClientState transition(ClientStateEvent event, ClientStateContext context) {
        return null;
    }

}
