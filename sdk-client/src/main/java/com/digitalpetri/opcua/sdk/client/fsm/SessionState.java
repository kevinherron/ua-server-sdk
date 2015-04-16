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

import com.digitalpetri.opcua.sdk.client.api.UaSession;

import java.util.concurrent.CompletableFuture;

public interface SessionState {

    /**
     * Activate this state.
     *
     * @param event   the {@link SessionStateEvent} that caused this state to be activated.
     * @param context the {@link SessionStateContext}.
     */
    void activate(SessionStateEvent event, SessionStateContext context);

    /**
     * Given {@code event}, return the next {@link SessionState}.
     *
     * @param event   the {@link SessionStateEvent}.
     * @param context the {@link SessionStateContext}.
     * @return the next {@link SessionState}.
     */
    SessionState transition(SessionStateEvent event, SessionStateContext context);

    /**
     * @return the {@link CompletableFuture} holding the {@link UaSession} for this client connection.
     */
    CompletableFuture<UaSession> sessionFuture();

}
