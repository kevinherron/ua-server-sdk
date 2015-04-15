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
import com.inductiveautomation.opcua.sdk.client.api.UaSession;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionState;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionStateContext;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionStateEvent;
import com.inductiveautomation.opcua.stack.client.UaTcpStackClient;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSessionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSessionResponse;
import com.inductiveautomation.opcua.stack.core.util.NonceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

public class CreatingSession implements SessionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicReference<CreateSessionResponse> response = new AtomicReference<>();

    private final CompletableFuture<UaSession> sessionFuture;

    public CreatingSession(CompletableFuture<UaSession> sessionFuture) {
        this.sessionFuture = sessionFuture;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {
        OpcUaClient client = context.getClient();
        UaTcpStackClient stackClient = client.getStackClient();

        String serverUri = stackClient.getEndpoint().flatMap(e -> {
            String gatewayServerUri = e.getServer().getGatewayServerUri();
            if (gatewayServerUri != null && !gatewayServerUri.isEmpty()) {
                return Optional.ofNullable(e.getServer().getApplicationUri());
            } else {
                return Optional.empty();
            }
        }).orElse(null);

        CreateSessionRequest request = new CreateSessionRequest(
                client.newRequestHeader(),
                stackClient.getApplication(),
                serverUri,
                stackClient.getEndpointUrl(),
                client.getConfig().getSessionName().get(),
                NonceUtil.generateNonce(16),
                ByteString.NULL_VALUE,
                client.getConfig().getSessionTimeout(),
                client.getConfig().getMaxResponseMessageSize());

        CompletableFuture<CreateSessionResponse> future = stackClient.sendRequest(request);

        future.whenComplete((r, ex) -> {
            if (r != null) {
                logger.debug("CreateSession succeeded, id={}, timeout={}",
                        r.getSessionId(), r.getRevisedSessionTimeout());

                response.set(r);
                context.handleEvent(SessionStateEvent.CREATE_SESSION_SUCCEEDED);
            } else {
                logger.debug("CreateSession failed: {}", ex.getMessage(), ex);

                context.handleEvent(SessionStateEvent.CREATE_SESSION_FAILED);
            }
        });
    }

    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        switch (event) {
            case CREATE_SESSION_FAILED:
                return new Inactive();

            case CREATE_SESSION_SUCCEEDED:
                return new ActivatingSession(sessionFuture, response.get());
        }

        return this;
    }

    @Override
    public CompletableFuture<UaSession> sessionFuture() {
        return sessionFuture;
    }

}
