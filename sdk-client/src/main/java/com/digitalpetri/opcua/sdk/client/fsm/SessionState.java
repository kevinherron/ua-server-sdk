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

import com.digitalpetri.opcua.sdk.client.api.UaSession;

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
    CompletableFuture<UaSession> getSessionFuture();

}
