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

package com.digitalpetri.opcua.sdk.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.sdk.client.ServiceFaultHandler;
import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.sdk.client.fsm.SessionState;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateContext;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateEvent;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.types.structured.ServiceFault;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Active implements SessionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UaSession session;
    private final CompletableFuture<UaSession> sessionFuture;

    public Active(UaSession session, CompletableFuture<UaSession> sessionFuture) {
        this.session = session;
        this.sessionFuture = sessionFuture;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {
        final OpcUaClient client = context.getClient();

        ServiceFaultHandler faultHandler = new ServiceFaultHandler() {
            @Override
            public boolean accept(ServiceFault serviceFault) {
                long statusCode = serviceFault.getResponseHeader().getServiceResult().getValue();

                return statusCode == StatusCodes.Bad_SessionIdInvalid;
            }

            @Override
            public void handle(ServiceFault serviceFault) {
                logger.warn("ServiceFault: {}", serviceFault.getResponseHeader().getServiceResult());
                client.removeFaultHandler(this);
                context.handleEvent(SessionStateEvent.CREATE_AND_ACTIVATE_REQUESTED);
            }
        };

        client.addFaultHandler(faultHandler);

        sessionFuture.complete(session);
    }

    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        switch (event) {
            case CREATE_AND_ACTIVATE_REQUESTED:
                return new CreateAndActivate(new CompletableFuture<>());

            case CLOSE_SESSION_REQUESTED:
                return new ClosingSession(sessionFuture);

            case ERR_CONNECTION_LOST:
                return new Reactivate(session);
        }
        
        return this;
    }

    @Override
    public CompletableFuture<UaSession> getSessionFuture() {
        return sessionFuture;
    }

}
