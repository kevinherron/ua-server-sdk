package com.inductiveautomation.opcua.sdk.client;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.sdk.client.api.UaClient;
import com.inductiveautomation.opcua.sdk.client.api.UaSession;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionStateContext;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionStateEvent;
import com.inductiveautomation.opcua.stack.client.UaTcpStackClient;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseNextRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseNextResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowsePath;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteSubscriptionsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteSubscriptionsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryReadDetails;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryReadRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryReadResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryReadValueId;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryUpdateDetails;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryUpdateRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryUpdateResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifyMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifySubscriptionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifySubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.MonitoredItemModifyRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;
import com.inductiveautomation.opcua.stack.core.types.structured.RegisterNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RegisterNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RepublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RepublishResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RequestHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SubscriptionAcknowledgement;
import com.inductiveautomation.opcua.stack.core.types.structured.TransferSubscriptionsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TransferSubscriptionsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.UnregisterNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.UnregisterNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ViewDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteValue;
import com.inductiveautomation.opcua.stack.core.util.LongSequence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.inductiveautomation.opcua.sdk.core.util.ConversionUtil.a;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class OpcUaClient implements UaClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final LongSequence requestHandles = new LongSequence(0, UInteger.MAX_VALUE);

    private final UaTcpStackClient stackClient;
    private final SessionStateContext stateContext;

    private final OpcUaClientConfig config;

    public OpcUaClient(OpcUaClientConfig config) {
        this.config = config;

        stackClient = config.getStackClient();
        stateContext = new SessionStateContext(this);
    }

    public OpcUaClientConfig getConfig() {
        return config;
    }

    public UaTcpStackClient getStackClient() {
        return stackClient;
    }

    public RequestHeader newRequestHeader() {
        return newRequestHeader(NodeId.NULL_VALUE);
    }

    public RequestHeader newRequestHeader(NodeId authToken) {
        return new RequestHeader(
                authToken,
                DateTime.now(),
                uint(requestHandles.getAndIncrement()),
                uint(0),
                null,
                uint((long) config.getRequestTimeout()),
                null
        );
    }

    public UInteger nextRequestHandle() {
        return uint(requestHandles.getAndIncrement());
    }

    @Override
    public CompletableFuture<UaClient> connect() {
        return getSession().thenApply(s -> OpcUaClient.this);
    }

    @Override
    public CompletableFuture<UaClient> disconnect() {
        stateContext.handleEvent(SessionStateEvent.CONNECTION_LOST);

        return CompletableFuture.completedFuture(this);
    }

    @Override
    public CompletableFuture<ReadResponse> read(double maxAge, TimestampsToReturn timestampsToReturn, List<ReadValueId> readValueIds) {

        return getSession().thenCompose(session -> {
            ReadRequest request = new ReadRequest(
                    newRequestHeader(session.getAuthToken()),
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
                    newRequestHeader(session.getAuthToken()),
                    a(writeValues, WriteValue.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<HistoryReadResponse> historyRead(HistoryReadDetails historyReadDetails, TimestampsToReturn timestampsToReturn, boolean releaseContinuationPoints, List<HistoryReadValueId> nodesToRead) {
        return getSession().thenCompose(session -> {
            HistoryReadRequest request = new HistoryReadRequest(
                    newRequestHeader(session.getAuthToken()),
                    new ExtensionObject(historyReadDetails),
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
                    .map(ExtensionObject::new)
                    .toArray(ExtensionObject[]::new);

            HistoryUpdateRequest request = new HistoryUpdateRequest(
                    newRequestHeader(session.getAuthToken()),
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
                    newRequestHeader(session.getAuthToken()),
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
                    newRequestHeader(session.getAuthToken()),
                    releaseContinuationPoints,
                    a(continuationPoints, ByteString.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<TranslateBrowsePathsToNodeIdsResponse> translateBrowsePaths(List<BrowsePath> browsePaths) {
        return getSession().thenCompose(session -> {
            TranslateBrowsePathsToNodeIdsRequest request = new TranslateBrowsePathsToNodeIdsRequest(
                    newRequestHeader(session.getAuthToken()),
                    a(browsePaths, BrowsePath.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<RegisterNodesResponse> registerNodes(List<NodeId> nodesToRegister) {
        return getSession().thenCompose(session -> {
            RegisterNodesRequest request = new RegisterNodesRequest(
                    newRequestHeader(session.getAuthToken()),
                    a(nodesToRegister, NodeId.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<UnregisterNodesResponse> unregisterNodes(List<NodeId> nodesToUnregister) {
        return getSession().thenCompose(session -> {
            UnregisterNodesRequest request = new UnregisterNodesRequest(
                    newRequestHeader(session.getAuthToken()),
                    a(nodesToUnregister, NodeId.class));

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
                    newRequestHeader(session.getAuthToken()),
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
                    newRequestHeader(session.getAuthToken()),
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
                    newRequestHeader(session.getAuthToken()),
                    a(subscriptionIds, UInteger.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<TransferSubscriptionsResponse> transferSubscriptions(List<UInteger> subscriptionIds,
                                                                                  boolean sendInitialValues) {

        return getSession().thenCompose(session -> {
            TransferSubscriptionsRequest request = new TransferSubscriptionsRequest(
                    newRequestHeader(session.getAuthToken()),
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
                    newRequestHeader(session.getAuthToken()),
                    publishingEnabled,
                    a(subscriptionIds, UInteger.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<PublishResponse> publish(List<SubscriptionAcknowledgement> subscriptionAcknowledgements) {
        return getSession().thenCompose(session -> {
            PublishRequest request = new PublishRequest(
                    newRequestHeader(session.getAuthToken()),
                    a(subscriptionAcknowledgements, SubscriptionAcknowledgement.class));

            return sendRequest(request);
        });
    }

    @Override
    public CompletableFuture<RepublishResponse> republish(UInteger subscriptionId, UInteger retransmitSequenceNumber) {
        return getSession().thenCompose(session -> {
            RepublishRequest request = new RepublishRequest(
                    newRequestHeader(session.getAuthToken()),
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
                    newRequestHeader(session.getAuthToken()),
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
                    newRequestHeader(session.getAuthToken()),
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
                    newRequestHeader(session.getAuthToken()),
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
                    newRequestHeader(session.getAuthToken()),
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
                    newRequestHeader(session.getAuthToken()),
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
        return stackClient.sendRequest(request);
    }

    @Override
    public void sendRequests(List<? extends UaRequestMessage> requests,
                             List<CompletableFuture<? extends UaResponseMessage>> futures) {

        stackClient.sendRequests(requests, futures);
    }

}
