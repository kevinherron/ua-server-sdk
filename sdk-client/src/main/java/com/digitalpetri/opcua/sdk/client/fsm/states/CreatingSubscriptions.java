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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreatingSubscriptions implements SessionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UaSession session;
    private final CompletableFuture<UaSession> sessionFuture;
    private final boolean transferNeeded;

    public CreatingSubscriptions(UaSession session,
                                 CompletableFuture<UaSession> sessionFuture,
                                 boolean transferNeeded) {

        this.session = session;
        this.sessionFuture = sessionFuture;
        this.transferNeeded = transferNeeded;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {
        logger.info("transferNeeded={}", transferNeeded);

        OpcUaClient client = context.getClient();

        if (transferNeeded) {
            logger.debug("TransferSubscriptions call needed.");

            client.getSubscriptionManager().transferSubscriptions().whenComplete((r, ex) -> {
                if (r != null) {
                    logger.debug("TransferSubscriptions call succeeded");
                    context.handleEvent(SessionStateEvent.CREATE_SUBSCRIPTIONS_SUCCEEDED);
                } else {
                    logger.debug("TransferSubscriptions call failed: {}", ex.getMessage(), ex);

                    context.handleEvent(SessionStateEvent.ERR_CREATE_SUBSCRIPTIONS_FAILED);
                }
            });
        } else {
            context.handleEvent(SessionStateEvent.CREATE_SUBSCRIPTIONS_SUCCEEDED);
        }
    }

    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        switch (event) {
            case ERR_CREATE_SUBSCRIPTIONS_FAILED:
                return new Inactive(transferNeeded);

            case CREATE_SUBSCRIPTIONS_SUCCEEDED:
                return new Active(session, sessionFuture);
        }

        return this;
    }

    @Override
    public CompletableFuture<UaSession> getSessionFuture() {
        return sessionFuture;
    }

}
