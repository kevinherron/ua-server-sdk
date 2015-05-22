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

package com.digitalpetri.opcua.sdk.client.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.api.config.OpcUaClientConfig;
import com.digitalpetri.opcua.sdk.client.api.nodes.AddressSpace;
import com.digitalpetri.opcua.sdk.client.api.nodes.NodeCache;
import com.digitalpetri.opcua.sdk.client.api.services.AttributeServices;
import com.digitalpetri.opcua.sdk.client.api.services.MethodServices;
import com.digitalpetri.opcua.sdk.client.api.services.MonitoredItemServices;
import com.digitalpetri.opcua.sdk.client.api.services.SubscriptionServices;
import com.digitalpetri.opcua.sdk.client.api.services.ViewServices;
import com.digitalpetri.opcua.sdk.client.api.subscriptions.UaSubscriptionManager;
import com.digitalpetri.opcua.sdk.client.subscriptions.OpcUaSubscriptionManager;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;

public interface UaClient extends AttributeServices, MethodServices, MonitoredItemServices, SubscriptionServices, ViewServices {

    /**
     * @return the {@link OpcUaClientConfig} for this client.
     */
    OpcUaClientConfig getConfig();

    /**
     * Connect to the configured endpoint.
     *
     * @return a {@link CompletableFuture} holding this client instance.
     */
    CompletableFuture<UaClient> connect();

    /**
     * Disconnect from the configured endpoint.
     *
     * @return a {@link CompletableFuture} holding this client instance.
     */
    CompletableFuture<UaClient> disconnect();

    /**
     * @return a {@link CompletableFuture} holding the {@link UaSession}.
     */
    CompletableFuture<UaSession> getSession();

    /**
     * @return the {@link AddressSpace}.
     */
    AddressSpace getAddressSpace();

    /**
     * @return the {@link NodeCache}.
     */
    NodeCache getNodeCache();

    /**
     * @return the {@link OpcUaSubscriptionManager} for this client.
     */
    UaSubscriptionManager getSubscriptionManager();

    /**
     * Send a {@link UaRequestMessage}.
     *
     * @param request the request to send.
     * @return a {@link CompletableFuture} holding the response.
     */
    <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request);

    /**
     * Send multiple {@link UaRequestMessage}s.
     *
     * @param requests the requests to send.
     * @param futures  the {@link CompletableFuture}s to complete when responses arrive.
     */
    void sendRequests(List<? extends UaRequestMessage> requests,
                      List<CompletableFuture<? extends UaResponseMessage>> futures);


}
