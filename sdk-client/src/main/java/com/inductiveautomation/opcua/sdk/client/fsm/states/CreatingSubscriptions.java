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

import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.sdk.client.api.UaSession;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionState;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionStateContext;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionStateEvent;

public class CreatingSubscriptions implements SessionState {

    private final UaSession session;
    private final CompletableFuture<UaSession> sessionFuture;

    public CreatingSubscriptions(UaSession session, CompletableFuture<UaSession> sessionFuture) {
        this.session = session;
        this.sessionFuture = sessionFuture;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {
        context.handleEvent(SessionStateEvent.CREATE_SUBSCRIPTIONS_SUCCEEDED);
    }

    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        switch (event) {
            case CREATE_SUBSCRIPTIONS_FAILED:
                return new Inactive();

            case CREATE_SUBSCRIPTIONS_SUCCEEDED:
                return new Active(session, sessionFuture);
        }
        
        return this;
    }

    @Override
    public CompletableFuture<UaSession> sessionFuture() {
        return sessionFuture;
    }

}
