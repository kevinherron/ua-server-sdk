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
import com.digitalpetri.opcua.sdk.client.api.ServiceFaultHandler;
import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.sdk.client.fsm.SessionState;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateContext;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateEvent;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Active implements SessionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private volatile ServiceFaultHandler faultHandler;
    private volatile ChannelInboundHandlerAdapter channelHandler;

    private final UaSession session;
    private final CompletableFuture<UaSession> future;

    public Active(UaSession session, CompletableFuture<UaSession> future) {
        this.session = session;
        this.future = future;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {
        OpcUaClient client = context.getClient();
        UaTcpStackClient stackClient = client.getStackClient();

        client.addFaultHandler(faultHandler = serviceFault -> {
            long statusCode = serviceFault.getResponseHeader().getServiceResult().getValue();

            if (statusCode == StatusCodes.Bad_SessionIdInvalid) {
                logger.warn("ServiceFault: {}", serviceFault.getResponseHeader().getServiceResult());
                context.handleEvent(SessionStateEvent.ERR_SESSION_INVALID);
            }
        });

        stackClient.getChannelFuture().thenAccept(ch -> {
            ch.pipeline().addLast(channelHandler = new ChannelInboundHandlerAdapter() {
                @Override
                public void channelInactive(ChannelHandlerContext ctx) throws Exception {
                    context.handleEvent(SessionStateEvent.ERR_CONNECTION_LOST);
                }
            });
        });

        client.getSubscriptionManager().restartPublishing();

        future.complete(session);
    }

    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        OpcUaClient client = context.getClient();
        UaTcpStackClient stackClient = client.getStackClient();

        client.removeFaultHandler(faultHandler);

        switch (event) {
            case DISCONNECT_REQUESTED:
                return new ClosingSession(session);

            case ERR_CONNECTION_LOST:
                return new Reactivating(session, 0);

            case ERR_SESSION_INVALID:
                return new CreatingSession(new CompletableFuture<>());
        }

        return this;
    }

    @Override
    public CompletableFuture<UaSession> getSessionFuture() {
        return future;
    }


}
