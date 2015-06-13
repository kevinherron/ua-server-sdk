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

package com.digitalpetri.opcua.sdk.client;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.api.ServiceFaultHandler;
import com.digitalpetri.opcua.sdk.client.api.UaClient;
import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.sdk.client.api.config.OpcUaClientConfig;
import com.digitalpetri.opcua.sdk.client.api.nodes.AddressSpace;
import com.digitalpetri.opcua.sdk.client.api.nodes.NodeCache;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateContext;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateEvent;
import com.digitalpetri.opcua.sdk.client.nodes.DefaultAddressSpace;
import com.digitalpetri.opcua.sdk.client.nodes.DefaultNodeCache;
import com.digitalpetri.opcua.sdk.client.subscriptions.OpcUaSubscriptionManager;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.UaServiceFaultException;
import com.digitalpetri.opcua.stack.core.serialization.UaRequestMessage;
import com.digitalpetri.opcua.stack.core.serialization.UaResponseMessage;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseDescription;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseNextRequest;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseNextResponse;
import com.digitalpetri.opcua.stack.core.types.structured.BrowsePath;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseRequest;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseResponse;
import com.digitalpetri.opcua.stack.core.types.structured.CallMethodRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CallRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CallResponse;
import com.digitalpetri.opcua.stack.core.types.structured.CreateMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSubscriptionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteSubscriptionsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteSubscriptionsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryReadDetails;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryReadRequest;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryReadResponse;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryUpdateDetails;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryUpdateRequest;
import com.digitalpetri.opcua.stack.core.types.structured.HistoryUpdateResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ModifyMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ModifySubscriptionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ModifySubscriptionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemModifyRequest;
import com.digitalpetri.opcua.stack.core.types.structured.PublishRequest;
import com.digitalpetri.opcua.stack.core.types.structured.PublishResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ReadRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ReadResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.RegisterNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RegisterNodesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.RepublishRequest;
import com.digitalpetri.opcua.stack.core.types.structured.RepublishResponse;
import com.digitalpetri.opcua.stack.core.types.structured.RequestHeader;
import com.digitalpetri.opcua.stack.core.types.structured.ServiceFault;
import com.digitalpetri.opcua.stack.core.types.structured.SetMonitoringModeRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SetPublishingModeRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetPublishingModeResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SetTriggeringRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetTriggeringResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SubscriptionAcknowledgement;
import com.digitalpetri.opcua.stack.core.types.structured.TransferSubscriptionsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TransferSubscriptionsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.UnregisterNodesRequest;
import com.digitalpetri.opcua.stack.core.types.structured.UnregisterNodesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ViewDescription;
import com.digitalpetri.opcua.stack.core.types.structured.WriteRequest;
import com.digitalpetri.opcua.stack.core.types.structured.WriteResponse;
import com.digitalpetri.opcua.stack.core.types.structured.WriteValue;
import com.digitalpetri.opcua.stack.core.util.ExecutionQueue;
import com.digitalpetri.opcua.stack.core.util.LongSequence;

import static com.digitalpetri.opcua.stack.core.util.ConversionUtil.a;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.google.common.collect.Lists.newCopyOnWriteArrayList;

public class OpcUaClient implements UaClient {

    private final LongSequence requestHandles = new LongSequence(0, UInteger.MAX_VALUE);

    private final List<ServiceFaultHandler> faultHandlers = newCopyOnWriteArrayList();
    private final ExecutionQueue faultNotificationQueue;

    private final AddressSpace addressSpace;
    private final NodeCache nodeCache = new DefaultNodeCache();
    private final OpcUaSubscriptionManager subscriptionManager;

    private final UaTcpStackClient stackClient;
    private final SessionStateContext stateContext;

    private final OpcUaClientConfig config;

    public OpcUaClient(OpcUaClientConfig config) {
        this.config = config;

        stateContext = new SessionStateContext(this);

        stackClient = new UaTcpStackClient(config);

        faultNotificationQueue = new ExecutionQueue(config.getExecutor());

        addressSpace = new DefaultAddressSpace(this);
        subscriptionManager = new OpcUaSubscriptionManager(this);
    }

    @Override
    public OpcUaClientConfig getConfig() {
        return config;
    }

    public UaTcpStackClient getStackClient() {
        return stackClient;
    }

    @Override
    public NodeCache getNodeCache() {
        return nodeCache;
    }

    @Override
    public AddressSpace getAddressSpace() {
        return addressSpace;
    }

    /**
     * Build a new {@link RequestHeader} using a null authentication token.
     *
     * @return a new {@link RequestHeader} with a null authentication token.
     */
    public RequestHeader newRequestHeader() {
        return newRequestHeader(NodeId.NULL_VALUE);
    }

    /**
     * Build a new {@link RequestHeader} using {@code authToken}.
     *
     * @param authToken the authentication token (from the session) to use.
     * @return a new {@link RequestHeader}.
     */
    public RequestHeader newRequestHeader(NodeId authToken) {
        return new RequestHeader(
                authToken,
                DateTime.now(),
                uint(requestHandles.getAndIncrement()),
                uint(0),
                null,
                config.getRequestTimeout(),
                null);
    }

    /**
     * @return the next {@link UInteger} to use as a request handle.
     */
    public UInteger nextRequestHandle() {
        return uint(requestHandles.getAndIncrement());
    }

    @Override
    public CompletableFuture<UaClient> connect() {
        return stackClient.connect().thenCompose(
                c -> getSession().thenApply(s -> OpcUaClient.this));
    }

    @Override
    public CompletableFuture<UaClient> disconnect() {
        stateContext.handleEvent(SessionStateEvent.DISCONNECT_REQUESTED);

        return CompletableFuture.completedFuture(this);
    }

    @Override
    public OpcUaSubscriptionManager getSubscriptionManager() {
        return subscriptionManager;
    }

    @Override
    public CompletableFuture<ReadResponse> read(double maxAge,
                                                TimestampsToReturn timestampsToReturn,
                                                List<ReadValueId> readValueIds) {

        return getSession().thenCompose(session -> {
            ReadRequest request = new ReadRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    maxAge,
                    timestampsToReturn,
                    a(readValueIds, ReadValueId.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<WriteResponse> write(List<WriteValue> writeValues) {
        return getSession().thenCompose(session -> {
            WriteRequest request = new WriteRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    a(writeValues, WriteValue.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<HistoryReadResponse> historyRead(HistoryReadDetails historyReadDetails,
                                                              TimestampsToReturn timestampsToReturn,
                                                              boolean releaseContinuationPoints,
                                                              List<HistoryReadValueId> nodesToRead) {

        return getSession().thenCompose(session -> {
            HistoryReadRequest request = new HistoryReadRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    ExtensionObject.encode(historyReadDetails),
                    timestampsToReturn,
                    releaseContinuationPoints,
                    a(nodesToRead, HistoryReadValueId.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<HistoryUpdateResponse> historyUpdate(List<HistoryUpdateDetails> historyUpdateDetails) {
        return getSession().thenCompose(session -> {
            ExtensionObject[] details = historyUpdateDetails.stream()
                    .map(ExtensionObject::encode)
                    .toArray(ExtensionObject[]::new);

            HistoryUpdateRequest request = new HistoryUpdateRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    details);

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<BrowseResponse> browse(ViewDescription viewDescription,
                                                    UInteger maxReferencesPerNode,
                                                    List<BrowseDescription> nodesToBrowse) {

        return getSession().thenCompose(session -> {
            BrowseRequest request = new BrowseRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    viewDescription,
                    maxReferencesPerNode,
                    a(nodesToBrowse, BrowseDescription.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<BrowseNextResponse> browseNext(boolean releaseContinuationPoints,
                                                            List<ByteString> continuationPoints) {

        return getSession().thenCompose(session -> {
            BrowseNextRequest request = new BrowseNextRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    releaseContinuationPoints,
                    a(continuationPoints, ByteString.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<TranslateBrowsePathsToNodeIdsResponse> translateBrowsePaths(List<BrowsePath> browsePaths) {
        return getSession().thenCompose(session -> {
            TranslateBrowsePathsToNodeIdsRequest request = new TranslateBrowsePathsToNodeIdsRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    a(browsePaths, BrowsePath.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<RegisterNodesResponse> registerNodes(List<NodeId> nodesToRegister) {
        return getSession().thenCompose(session -> {
            RegisterNodesRequest request = new RegisterNodesRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    a(nodesToRegister, NodeId.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<UnregisterNodesResponse> unregisterNodes(List<NodeId> nodesToUnregister) {
        return getSession().thenCompose(session -> {
            UnregisterNodesRequest request = new UnregisterNodesRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    a(nodesToUnregister, NodeId.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<CallResponse> call(List<CallMethodRequest> methodsToCall) {
        return getSession().thenCompose(session -> {
            CallRequest request = new CallRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    a(methodsToCall, CallMethodRequest.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<CreateSubscriptionResponse> createSubscription(double requestedPublishingInterval,
                                                                            UInteger requestedLifetimeCount,
                                                                            UInteger requestedMaxKeepAliveCount,
                                                                            UInteger maxNotificationsPerPublish,
                                                                            boolean publishingEnabled,
                                                                            UByte priority) {

        return getSession().thenCompose(session -> {
            CreateSubscriptionRequest request = new CreateSubscriptionRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    requestedPublishingInterval,
                    requestedLifetimeCount,
                    requestedMaxKeepAliveCount,
                    maxNotificationsPerPublish,
                    publishingEnabled,
                    priority);

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<ModifySubscriptionResponse> modifySubscription(UInteger subscriptionId,
                                                                            double requestedPublishingInterval,
                                                                            UInteger requestedLifetimeCount,
                                                                            UInteger requestedMaxKeepAliveCount,
                                                                            UInteger maxNotificationsPerPublish,
                                                                            UByte priority) {

        return getSession().thenCompose(session -> {
            ModifySubscriptionRequest request = new ModifySubscriptionRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    subscriptionId,
                    requestedPublishingInterval,
                    requestedLifetimeCount,
                    requestedMaxKeepAliveCount,
                    maxNotificationsPerPublish,
                    priority);

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<DeleteSubscriptionsResponse> deleteSubscriptions(List<UInteger> subscriptionIds) {
        return getSession().thenCompose(session -> {
            DeleteSubscriptionsRequest request = new DeleteSubscriptionsRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    a(subscriptionIds, UInteger.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<TransferSubscriptionsResponse> transferSubscriptions(List<UInteger> subscriptionIds,
                                                                                  boolean sendInitialValues) {

        return getSession().thenCompose(session -> {
            TransferSubscriptionsRequest request = new TransferSubscriptionsRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    a(subscriptionIds, UInteger.class),
                    sendInitialValues);

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<SetPublishingModeResponse> setPublishingMode(boolean publishingEnabled,
                                                                          List<UInteger> subscriptionIds) {

        return getSession().thenCompose(session -> {
            SetPublishingModeRequest request = new SetPublishingModeRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    publishingEnabled,
                    a(subscriptionIds, UInteger.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<PublishResponse> publish(List<SubscriptionAcknowledgement> subscriptionAcknowledgements) {
        return getSession().thenCompose(session -> {
            PublishRequest request = new PublishRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    a(subscriptionAcknowledgements, SubscriptionAcknowledgement.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<RepublishResponse> republish(UInteger subscriptionId, UInteger retransmitSequenceNumber) {
        return getSession().thenCompose(session -> {
            RepublishRequest request = new RepublishRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    subscriptionId,
                    retransmitSequenceNumber);

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<CreateMonitoredItemsResponse> createMonitoredItems(UInteger subscriptionId,
                                                                                TimestampsToReturn timestampsToReturn,
                                                                                List<MonitoredItemCreateRequest> itemsToCreate) {

        return getSession().thenCompose(session -> {
            CreateMonitoredItemsRequest request = new CreateMonitoredItemsRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    subscriptionId,
                    timestampsToReturn,
                    a(itemsToCreate, MonitoredItemCreateRequest.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<ModifyMonitoredItemsResponse> modifyMonitoredItems(UInteger subscriptionId,
                                                                                TimestampsToReturn timestampsToReturn,
                                                                                List<MonitoredItemModifyRequest> itemsToModify) {

        return getSession().thenCompose(session -> {
            ModifyMonitoredItemsRequest request = new ModifyMonitoredItemsRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    subscriptionId,
                    timestampsToReturn,
                    a(itemsToModify, MonitoredItemModifyRequest.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<DeleteMonitoredItemsResponse> deleteMonitoredItems(UInteger subscriptionId,
                                                                                List<UInteger> monitoredItemIds) {

        return getSession().thenCompose(session -> {
            DeleteMonitoredItemsRequest request = new DeleteMonitoredItemsRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    subscriptionId,
                    a(monitoredItemIds, UInteger.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<SetMonitoringModeResponse> setMonitoringMode(UInteger subscriptionId,
                                                                          MonitoringMode monitoringMode,
                                                                          List<UInteger> monitoredItemIds) {

        return getSession().thenCompose(session -> {
            SetMonitoringModeRequest request = new SetMonitoringModeRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    subscriptionId,
                    monitoringMode,
                    a(monitoredItemIds, UInteger.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<SetTriggeringResponse> setTriggering(UInteger subscriptionId,
                                                                  UInteger triggeringItemId,
                                                                  List<UInteger> linksToAdd,
                                                                  List<UInteger> linksToRemove) {

        return getSession().thenCompose(session -> {
            SetTriggeringRequest request = new SetTriggeringRequest(
                    newRequestHeader(session.getAuthenticationToken()),
                    subscriptionId,
                    triggeringItemId,
                    a(linksToAdd, UInteger.class),
                    a(linksToRemove, UInteger.class));

            return sendRequest(request);
        });
    }

    @Override
    public final CompletableFuture<UaSession> getSession() {
        return stateContext.getSession();
    }

    @Override
    public <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request) {
        CompletableFuture<T> f = stackClient.sendRequest(request);

        if (faultHandlers.size() > 0) {
            f.whenCompleteAsync(this::maybeHandleServiceFault, getConfig().getExecutor());
        }

        return f;
    }

    @Override
    public void sendRequests(List<? extends UaRequestMessage> requests,
                             List<CompletableFuture<? extends UaResponseMessage>> futures) {

        futures.forEach(f -> f.whenCompleteAsync(this::maybeHandleServiceFault, getConfig().getExecutor()));

        stackClient.sendRequests(requests, futures);
    }

    private void maybeHandleServiceFault(UaResponseMessage response, Throwable ex) {
        if (faultHandlers.isEmpty()) return;

        if (ex instanceof UaServiceFaultException) {
            UaServiceFaultException faultException = (UaServiceFaultException) ex;
            ServiceFault serviceFault = faultException.getServiceFault();

            faultNotificationQueue.submit(() ->
                    faultHandlers.stream().forEach(h -> h.handle(serviceFault)));
        }
    }

    public void addFaultHandler(ServiceFaultHandler faultHandler) {
        faultHandlers.add(faultHandler);
    }

    public void removeFaultHandler(ServiceFaultHandler faultHandler) {
        faultHandlers.remove(faultHandler);
    }

}
