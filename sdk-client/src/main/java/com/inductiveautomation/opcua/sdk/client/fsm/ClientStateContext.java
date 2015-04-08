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

package com.inductiveautomation.opcua.sdk.client.fsm;

import com.inductiveautomation.opcua.sdk.client.OpcUaClient;
import com.inductiveautomation.opcua.sdk.client.fsm.states.Inactive;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.atomic.AtomicReference;

public class ClientStateContext {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicReference<ClientState> state = new AtomicReference<>(new Inactive());

    private final OpcUaClient client;

    public ClientStateContext(OpcUaClient client) {
        this.client = client;
    }

    public synchronized ClientState handleEvent(ClientStateEvent event) {
        ClientState currState = state.get();
        ClientState nextState = currState.transition(event, this);

        state.set(nextState);

        logger.debug("S({}) x E({}) = S'({})", currState, event, nextState);

        return nextState;
    }

    public OpcUaClient getClient() {
        return client;
    }

}
