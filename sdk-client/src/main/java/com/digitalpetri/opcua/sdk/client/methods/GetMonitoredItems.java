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

package com.digitalpetri.opcua.sdk.client.methods;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.api.UaClient;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import org.jooq.lambda.tuple.Tuple2;

public class GetMonitoredItems extends AbstractUaMethod {

    public GetMonitoredItems(UaClient client, NodeId objectId, NodeId methodId) {
        super(client, objectId, methodId);
    }

    /**
     * GetMonitoredItems is used to get information about monitored items of a subscription.
     *
     * @param subscriptionId identifier of the subscription.
     * @return a {@link Tuple2} containing the output arguments.
     * <p>
     * serverHandles (UInt32[]) - array of serverHandles for all MonitoredItems of the subscription identified by
     * subscriptionId.
     * <p>
     * clientHandles (UInt32[]) - array of clientHandles for all MonitoredItems of the subscription identified by
     * subscriptionId.
     */
    public CompletableFuture<Tuple2<UInteger[], UInteger[]>> invoke(UInteger subscriptionId) {
        Variant[] inputArguments = new Variant[]{
                new Variant(subscriptionId)
        };

        return invoke(inputArguments).thenCompose(outputArguments -> {
            try {
                UInteger[] v0 = (UInteger[]) outputArguments[0].getValue();
                UInteger[] v1 = (UInteger[]) outputArguments[1].getValue();

                return CompletableFuture.completedFuture(new Tuple2<>(v0, v1));
            } catch (Throwable t) {
                CompletableFuture<Tuple2<UInteger[], UInteger[]>> f = new CompletableFuture<>();
                f.completeExceptionally(new UaException(t));
                return f;
            }
        });
    }

}
