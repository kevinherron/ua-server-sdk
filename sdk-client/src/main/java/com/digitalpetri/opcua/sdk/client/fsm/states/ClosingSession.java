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

package com.digitalpetri.opcua.sdk.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.sdk.client.fsm.SessionState;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateContext;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateEvent;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.structured.CloseSessionRequest;

public class ClosingSession implements SessionState {

    private final UaSession session;

    public ClosingSession(UaSession session) {
        this.session = session;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {
        OpcUaClient client = context.getClient();
        UaTcpStackClient stackClient = client.getStackClient();

        CloseSessionRequest request = new CloseSessionRequest(
                client.newRequestHeader(session.getAuthenticationToken()), true);

        stackClient.sendRequest(request).whenComplete((r, t) ->
                context.handleEvent(SessionStateEvent.DISCONNECT_SUCCEEDED));
    }

    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        switch (event) {
            case DISCONNECT_SUCCEEDED:
                return new Inactive();

            case SESSION_REQUESTED:
                return new CreatingSession(new CompletableFuture<>());
        }

        return this;
    }

    @Override
    public CompletableFuture<UaSession> getSessionFuture() {
        CompletableFuture<UaSession> f = new CompletableFuture<>();
        f.completeExceptionally(new UaException(StatusCodes.Bad_SessionClosed, "session is closed"));
        return f;
    }

}
