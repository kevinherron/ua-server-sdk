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
            handleEvent(SessionStateEvent.SESSION_REQUESTED);
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
