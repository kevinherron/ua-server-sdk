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

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.sdk.client.fsm.SessionState;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateContext;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateEvent;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSessionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSessionResponse;
import com.digitalpetri.opcua.stack.core.util.NonceUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreatingSession implements SessionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile ByteString clientNonce = ByteString.NULL_VALUE;
    private volatile CreateSessionResponse csr;

    private final CompletableFuture<UaSession> future;

    public CreatingSession(CompletableFuture<UaSession> future) {
        this.future = future;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {
        createSession(context).whenComplete((csr, ex) -> {
            if (csr != null) {
                this.csr = csr;
                context.handleEvent(SessionStateEvent.CREATE_SUCCEEDED);
            } else {
                context.handleEvent(SessionStateEvent.ERR_CREATE_FAILED);
                future.completeExceptionally(ex);
            }
        });
    }

    private CompletableFuture<CreateSessionResponse> createSession(SessionStateContext context) {
        OpcUaClient client = context.getClient();
        UaTcpStackClient stackClient = client.getStackClient();
        ClientSecureChannel secureChannel = stackClient.getSecureChannel();

        String serverUri = stackClient.getEndpoint().flatMap(e -> {
            String gatewayServerUri = e.getServer().getGatewayServerUri();
            if (gatewayServerUri != null && !gatewayServerUri.isEmpty()) {
                return Optional.ofNullable(e.getServer().getApplicationUri());
            } else {
                return Optional.empty();
            }
        }).orElse(null);

        clientNonce = NonceUtil.generateNonce(32);

        ByteString clientCertificate;
        try {
            clientCertificate = secureChannel.getLocalCertificateBytes();
        } catch (UaException e) {
            clientCertificate = ByteString.NULL_VALUE;
        }

        CreateSessionRequest request = new CreateSessionRequest(
                client.newRequestHeader(),
                stackClient.getApplication(),
                serverUri,
                stackClient.getEndpointUrl(),
                client.getConfig().getSessionName().get(),
                clientNonce,
                clientCertificate,
                client.getConfig().getSessionTimeout().doubleValue(),
                client.getConfig().getMaxResponseMessageSize());

        logger.debug("Sending CreateSessionRequest...");

        return stackClient.sendRequest(request);
    }


    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        switch (event) {
            case CREATE_SUCCEEDED:
                return new ActivatingSession(future, csr);

            case ERR_CREATE_FAILED:
            case DISCONNECT_REQUESTED:
                return new Inactive();
        }

        return this;
    }

    @Override
    public CompletableFuture<UaSession> getSessionFuture() {
        return future;
    }

}
