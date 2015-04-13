package com.inductiveautomation.opcua.sdk.client;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inductiveautomation.opcua.sdk.client.api.UaClient;
import com.inductiveautomation.opcua.sdk.client.api.UaSession;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionStateContext;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionStateEvent;
import com.inductiveautomation.opcua.stack.client.UaTcpClient;
import com.inductiveautomation.opcua.stack.core.Stack;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
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
import com.inductiveautomation.opcua.stack.core.types.structured.ReadRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;
import com.inductiveautomation.opcua.stack.core.types.structured.RegisterNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RegisterNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RequestHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.UnregisterNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.UnregisterNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ViewDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteValue;
import com.inductiveautomation.opcua.stack.core.util.LongSequence;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.inductiveautomation.opcua.sdk.core.util.ConversionUtil.a;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class OpcUaClient implements UaClient {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<UInteger, CompletableFuture<?>> pending = Maps.newConcurrentMap();
    private final HashedWheelTimer wheelTimer = Stack.sharedWheelTimer();

    private final LongSequence requestHandles = new LongSequence(0, UInteger.MAX_VALUE);
    private final List<OpcUaSubscription> subscriptions = Lists.newCopyOnWriteArrayList();

    private final UaTcpClient stackClient;
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

    public UaTcpClient getStackClient() {
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

        CompletableFuture<CreateSubscriptionResponse> future = new CompletableFuture<>();

        stateContext.getSession().whenComplete((session, ex) -> {
            if (session != null) {
                CreateSubscriptionRequest request = new CreateSubscriptionRequest(
                        newRequestHeader(session.getAuthToken()),
                        requestedPublishingInterval,
                        requestedLifetimeCount,
                        requestedMaxKeepAliveCount,
                        maxNotificationsPerPublish,
                        publishingEnabled,
                        priority
                );

                sendRequest(future, request, future::complete);
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<ModifySubscriptionResponse> modifySubscription(UInteger subscriptionId,
                                                                            double requestedPublishingInterval,
                                                                            UInteger requestedLifetimeCount,
                                                                            UInteger requestedMaxKeepAliveCount,
                                                                            UInteger maxNotificationsPerPublish,
                                                                            UByte priority) {

        CompletableFuture<ModifySubscriptionResponse> future = new CompletableFuture<>();

        stateContext.getSession().whenComplete((session, ex) -> {
            if (session != null) {
                ModifySubscriptionRequest request = new ModifySubscriptionRequest(
                        newRequestHeader(session.getAuthToken()),
                        subscriptionId,
                        requestedPublishingInterval,
                        requestedLifetimeCount,
                        requestedMaxKeepAliveCount,
                        maxNotificationsPerPublish,
                        priority
                );

                sendRequest(future, request, future::complete);
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<DeleteSubscriptionsResponse> deleteSubscriptions(List<UInteger> subscriptionIds) {
        CompletableFuture<DeleteSubscriptionsResponse> future = new CompletableFuture<>();

        stateContext.getSession().whenComplete((session, ex) -> {
            if (session != null) {
                DeleteSubscriptionsRequest request = new DeleteSubscriptionsRequest(
                        newRequestHeader(session.getAuthToken()),
                        a(subscriptionIds, UInteger.class)
                );

                sendRequest(future, request, future::complete);
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<SetPublishingModeResponse> setPublishingMode(boolean publishingEnabled,
                                                                          List<UInteger> subscriptionIds) {

        CompletableFuture<SetPublishingModeResponse> future = new CompletableFuture<>();

        stateContext.getSession().whenComplete((session, ex) -> {
            if (session != null) {
                SetPublishingModeRequest request = new SetPublishingModeRequest(
                        newRequestHeader(session.getAuthToken()),
                        publishingEnabled,
                        a(subscriptionIds, UInteger.class)
                );

                sendRequest(future, request, future::complete);
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<CreateMonitoredItemsResponse> createMonitoredItems(UInteger subscriptionId,
                                                                                TimestampsToReturn timestampsToReturn,
                                                                                List<MonitoredItemCreateRequest> itemsToCreate) {

        CompletableFuture<CreateMonitoredItemsResponse> future = new CompletableFuture<>();

        stateContext.getSession().whenComplete((session, ex) -> {
            if (session != null) {
                CreateMonitoredItemsRequest request = new CreateMonitoredItemsRequest(
                        newRequestHeader(session.getAuthToken()),
                        subscriptionId,
                        timestampsToReturn,
                        a(itemsToCreate, MonitoredItemCreateRequest.class)
                );

                sendRequest(future, request, future::complete);
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<ModifyMonitoredItemsResponse> modifyMonitoredItems(UInteger subscriptionId,
                                                                                TimestampsToReturn timestampsToReturn,
                                                                                List<MonitoredItemModifyRequest> itemsToModify) {
        CompletableFuture<ModifyMonitoredItemsResponse> future = new CompletableFuture<>();

        stateContext.getSession().whenComplete((session, ex) -> {
            if (session != null) {
                ModifyMonitoredItemsRequest request = new ModifyMonitoredItemsRequest(
                        newRequestHeader(session.getAuthToken()),
                        subscriptionId,
                        timestampsToReturn,
                        a(itemsToModify, MonitoredItemModifyRequest.class)
                );

                sendRequest(future, request, future::complete);
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<DeleteMonitoredItemsResponse> deleteMonitoredItems(UInteger subscriptionId,
                                                                                List<UInteger> monitoredItemIds) {
        CompletableFuture<DeleteMonitoredItemsResponse> future = new CompletableFuture<>();

        stateContext.getSession().whenComplete((session, ex) -> {
            if (session != null) {
                DeleteMonitoredItemsRequest request = new DeleteMonitoredItemsRequest(
                        newRequestHeader(session.getAuthToken()),
                        subscriptionId,
                        a(monitoredItemIds, UInteger.class)
                );

                sendRequest(future, request, future::complete);
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<SetMonitoringModeResponse> setMonitoringMode(UInteger subscriptionId,
                                                                          MonitoringMode monitoringMode,
                                                                          List<UInteger> monitoredItemIds) {

        CompletableFuture<SetMonitoringModeResponse> future = new CompletableFuture<>();

        stateContext.getSession().whenComplete((session, ex) -> {
            if (session != null) {
                SetMonitoringModeRequest request = new SetMonitoringModeRequest(
                        newRequestHeader(session.getAuthToken()),
                        subscriptionId,
                        monitoringMode,
                        a(monitoredItemIds, UInteger.class)
                );

                sendRequest(future, request, future::complete);
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<SetTriggeringResponse> setTriggering(UInteger subscriptionId,
                                                                  UInteger triggeringItemId,
                                                                  List<UInteger> linksToAdd,
                                                                  List<UInteger> linksToRemove) {

        CompletableFuture<SetTriggeringResponse> future = new CompletableFuture<>();

        stateContext.getSession().whenComplete((session, ex) -> {
            if (session != null) {
                SetTriggeringRequest request = new SetTriggeringRequest(
                        newRequestHeader(session.getAuthToken()),
                        subscriptionId,
                        triggeringItemId,
                        a(linksToAdd, UInteger.class),
                        a(linksToRemove, UInteger.class)
                );

                sendRequest(future, request, future::complete);
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<UaSession> getSession() {
        return stateContext.getSession();
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T extends UaResponseMessage> CompletableFuture<T> sendRequest(UaRequestMessage request) {
        CompletableFuture<T> future = new CompletableFuture<>();

        Timeout timeout = scheduleRequestTimeout(request, future);

        stackClient.sendRequest(request).whenComplete((response, ex) -> {
            timeout.cancel();

            if (response != null) {
                if (pending.remove(response.getResponseHeader().getRequestHandle()) != null) {
                    try {
                        future.complete((T) response);
                    } catch (Throwable t) {
                        if (!future.isDone()) future.completeExceptionally(t);
                    }
                } else {
                    logger.warn("Response arrived after timeout elapsed: {}", response);
                    // TODO log this, increment a count, notify a listener?
                }
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @SuppressWarnings("unchecked")
    <T extends UaResponseMessage> void sendRequest(CompletableFuture<?> future,
                                                   UaRequestMessage request,
                                                   Consumer<T> responseConsumer) {

        Timeout timeout = scheduleRequestTimeout(request, future);

        stackClient.sendRequest(request).whenComplete((response, ex) -> {
            timeout.cancel();

            if (response != null) {
                if (pending.remove(response.getResponseHeader().getRequestHandle()) != null) {
                    try {
                        responseConsumer.accept((T) response);
                    } catch (Throwable t) {
                        if (!future.isDone()) future.completeExceptionally(t);
                    }
                } else {
                    logger.warn("Response arrived after timeout elapsed: {}", response);
                    // TODO log this, increment a count, notify a listener?
                }
            } else {
                future.completeExceptionally(ex);
            }
        });
    }

    private Timeout scheduleRequestTimeout(UaRequestMessage request, CompletableFuture<?> future) {
        UInteger requestHandle = request.getRequestHeader().getRequestHandle();

        pending.put(requestHandle, future);

        return wheelTimer.newTimeout(t -> {
            if (!t.isCancelled()) {
                CompletableFuture<?> f = pending.remove(requestHandle);
                if (f != null) {
                    String message = "request timed out after " + config.getRequestTimeout() + "ms";
                    f.completeExceptionally(new UaException(StatusCodes.Bad_RequestTimeout, message));
                }
            }
        }, (long) config.getRequestTimeout(), TimeUnit.MILLISECONDS);
    }

    SessionStateContext getStateContext() {
        return stateContext;
    }

}
