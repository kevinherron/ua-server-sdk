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

package com.digitalpetri.opcua.sdk.server.model.methods;

import java.util.List;

import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.items.BaseMonitoredItem;
import com.digitalpetri.opcua.sdk.server.subscriptions.Subscription;
import com.digitalpetri.opcua.sdk.server.util.AnnotationBasedInvocationHandler.InvocationContext;
import com.digitalpetri.opcua.sdk.server.util.AnnotationBasedInvocationHandler.Out;
import com.digitalpetri.opcua.sdk.server.util.UaInputArgument;
import com.digitalpetri.opcua.sdk.server.util.UaMethod;
import com.digitalpetri.opcua.sdk.server.util.UaOutputArgument;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.google.common.collect.Lists;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class GetMonitoredItems {

    private final OpcUaServer server;

    public GetMonitoredItems(OpcUaServer server) {
        this.server = server;
    }

    @UaMethod
    public void invoke(
            InvocationContext context,

            @UaInputArgument(name = "subscriptionId")
            UInteger subscriptionId,

            @UaOutputArgument(name = "serverHandles")
            Out<UInteger[]> serverHandles,

            @UaOutputArgument(name = "clientHandles")
            Out<UInteger[]> clientHandles) throws UaException {

        Subscription subscription = server.getSubscriptions().get(subscriptionId);

        if (subscription == null) {
            throw new UaException(new StatusCode(StatusCodes.Bad_SubscriptionIdInvalid));
        }

        List<UInteger> serverHandleList = Lists.newArrayList();
        List<UInteger> clientHandleList = Lists.newArrayList();

        for (BaseMonitoredItem<?> item : subscription.getMonitoredItems().values()) {
            serverHandleList.add(item.getId());
            clientHandleList.add(uint(item.getClientHandle()));
        }

        serverHandles.set(serverHandleList.toArray(new UInteger[serverHandleList.size()]));
        clientHandles.set(clientHandleList.toArray(new UInteger[clientHandleList.size()]));
    }

}
