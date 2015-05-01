package com.digitalpetri.opcua.sdk.client.fsm.states;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.sdk.client.fsm.SessionState;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateContext;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateEvent;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.types.structured.CloseSessionRequest;

public class ClosingSession implements SessionState {

    private final CompletableFuture<UaSession> sessionFuture;

    public ClosingSession(CompletableFuture<UaSession> sessionFuture) {
        this.sessionFuture = sessionFuture;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {
        sessionFuture.whenComplete((session, ex) -> {
            if (session != null) {
                OpcUaClient client = context.getClient();
                UaTcpStackClient stackClient = client.getStackClient();

                CloseSessionRequest request = new CloseSessionRequest(
                        client.newRequestHeader(session.getAuthenticationToken()), true);

                stackClient.sendRequest(request).whenComplete((r, t) ->
                        context.handleEvent(SessionStateEvent.CLOSE_SESSION_SUCCEEDED));
            } else {
                context.handleEvent(SessionStateEvent.ERR_CLOSE_SESSION_FAILED);
            }
        });
    }

    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        switch (event) {
            case ERR_CLOSE_SESSION_FAILED:
            case CLOSE_SESSION_SUCCEEDED:
                return new Disconnecting(sessionFuture);

            case CREATE_AND_ACTIVATE_REQUESTED:
                return new CreateAndActivate(new CompletableFuture<>());
        }

        return this;
    }

    @Override
    public CompletableFuture<UaSession> getSessionFuture() {
        return sessionFuture;
    }

}
