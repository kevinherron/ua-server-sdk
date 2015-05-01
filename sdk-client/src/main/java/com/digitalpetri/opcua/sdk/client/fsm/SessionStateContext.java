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

package com.digitalpetri.opcua.sdk.client.fsm;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.sdk.client.fsm.states.Active;
import com.digitalpetri.opcua.sdk.client.fsm.states.Inactive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SessionStateContext {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicReference<SessionState> state =
            new AtomicReference<>(new Inactive());

    private final OpcUaClient client;

    public SessionStateContext(OpcUaClient client) {
        this.client = client;
    }

    public synchronized void handleEvent(SessionStateEvent event) {
        SessionState currState = state.get();
        SessionState nextState = currState.transition(event, this);

        logger.debug("S({}) x E({}) = S'({})",
                currState.getClass().getSimpleName(), event, nextState.getClass().getSimpleName());

        if (nextState != currState) {
            state.set(nextState);
            nextState.activate(event, this);
        }
    }

    public synchronized CompletableFuture<UaSession> getSession() {
        if (!isActive()) {
            handleEvent(SessionStateEvent.CREATE_AND_ACTIVATE_REQUESTED);
        }

        return state.get().getSessionFuture();
    }

    public boolean isActive() {
        return state.get() instanceof Active;
    }

    public OpcUaClient getClient() {
        return client;
    }

}
