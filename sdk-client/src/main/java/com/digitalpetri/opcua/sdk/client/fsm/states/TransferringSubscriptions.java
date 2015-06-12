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

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.sdk.client.api.subscriptions.UaSubscription;
import com.digitalpetri.opcua.sdk.client.fsm.SessionState;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateContext;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateEvent;
import com.digitalpetri.opcua.sdk.client.subscriptions.OpcUaSubscriptionManager;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.TransferResult;
import com.digitalpetri.opcua.stack.core.types.structured.TransferSubscriptionsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TransferSubscriptionsResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TransferringSubscriptions implements SessionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UaSession session;
    private final CompletableFuture<UaSession> future;

    public TransferringSubscriptions(UaSession session, CompletableFuture<UaSession> future) {
        this.session = session;
        this.future = future;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {
        OpcUaClient client = context.getClient();
        OpcUaSubscriptionManager subscriptionManager = client.getSubscriptionManager();
        List<UaSubscription> subscriptions = subscriptionManager.getSubscriptions();

        if (subscriptions.size() > 0) {
            logger.debug("Transferring {} subscriptions.", subscriptions.size());

            transferSubscriptions(client, subscriptions).whenComplete((tsr, ex) -> {
                if (tsr != null) {
                    TransferResult[] results = tsr.getResults();

                    for (int i = 0; i < results.length; i++) {
                        TransferResult result = results[i];

                        if (!result.getStatusCode().isGood()) {
                            UaSubscription subscription = subscriptions.get(i);

                            subscriptionManager.transferFailed(
                                    subscription.getSubscriptionId(),
                                    result.getStatusCode());
                        }
                    }

                    context.handleEvent(SessionStateEvent.TRANSFER_SUCCEEDED);
                } else {
                    StatusCode statusCode = UaException.extract(ex)
                            .map(UaException::getStatusCode)
                            .orElse(StatusCode.BAD);

                    if (statusCode.getValue() == StatusCodes.Bad_ServiceUnsupported ||
                            statusCode.getValue() == StatusCodes.Bad_NotSupported ||
                            statusCode.getValue() == StatusCodes.Bad_OutOfService) {

                        context.handleEvent(SessionStateEvent.ERR_TRANSFER_UNSUPPORTED);
                    } else {
                        context.handleEvent(SessionStateEvent.ERR_TRANSFER_FAILED);

                        future.completeExceptionally(ex);
                    }
                }
            });
        }
    }

    private CompletableFuture<TransferSubscriptionsResponse> transferSubscriptions(OpcUaClient client,
                                                                                   List<UaSubscription> subscriptions) {

        UaTcpStackClient stackClient = client.getStackClient();

        UInteger[] subscriptionIds = subscriptions.stream()
                .map(UaSubscription::getSubscriptionId)
                .toArray(UInteger[]::new);

        TransferSubscriptionsRequest request = new TransferSubscriptionsRequest(
                client.newRequestHeader(session.getAuthenticationToken()),
                subscriptionIds,
                true);

        return stackClient.<TransferSubscriptionsResponse>sendRequest(request);
    }

    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        switch (event) {
            case TRANSFER_SUCCEEDED:
            case ERR_TRANSFER_UNSUPPORTED:
                return new Active(session, future);

            case ERR_TRANSFER_FAILED:
            case DISCONNECT_REQUESTED:
                return new ClosingSession(session);
        }

        return this;
    }

    @Override
    public CompletableFuture<UaSession> getSessionFuture() {
        return future;
    }

}
