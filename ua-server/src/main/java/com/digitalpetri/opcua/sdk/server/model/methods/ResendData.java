package com.digitalpetri.opcua.sdk.server.model.methods;

import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.subscriptions.Subscription;
import com.digitalpetri.opcua.sdk.server.util.AnnotationBasedInvocationHandler.InvocationContext;
import com.digitalpetri.opcua.sdk.server.util.UaInputArgument;
import com.digitalpetri.opcua.sdk.server.util.UaMethod;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public class ResendData {

    private final OpcUaServer server;

    public ResendData(OpcUaServer server) {
        this.server = server;
    }

    @UaMethod
    public void invoke(
            InvocationContext context,

            @UaInputArgument(name = "subscriptionId")
            UInteger subscriptionId) throws UaException {

        Subscription subscription = server.getSubscriptions().get(subscriptionId);

        if (subscription == null) {
            context.setFailure(new UaException(new StatusCode(StatusCodes.Bad_SubscriptionIdInvalid)));
        } else {
            // TODO
            // subscription.resendData();
        }
    }

}
