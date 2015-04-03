package com.inductiveautomation.opcua.sdk.client;

import com.google.common.collect.Maps;
import com.inductiveautomation.opcua.sdk.client.api.UaClient;
import com.inductiveautomation.opcua.sdk.client.fsm.ClientStateContext;
import com.inductiveautomation.opcua.stack.client.UaTcpClient;
import com.inductiveautomation.opcua.stack.core.Stack;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.serialization.UaRequestMessage;
import com.inductiveautomation.opcua.stack.core.serialization.UaResponseMessage;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.*;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

import static com.google.common.collect.Lists.newArrayList;

public class OpcUaClient implements UaClient {

    private final Map<UInteger, CompletableFuture<?>> pending = Maps.newConcurrentMap();
    private final HashedWheelTimer wheelTimer = Stack.sharedWheelTimer();

    private final SessionManager sessionManager;
    private final UaTcpClient stackClient;
    private final ClientStateContext stateContext;

    private final OpcUaClientConfig config;

    public OpcUaClient(OpcUaClientConfig config) {
        this.config = config;

        stackClient = config.getStackClient();
        stateContext = new ClientStateContext(this);
        sessionManager = new SessionManager(this);
    }

    public OpcUaClientConfig getConfig() {
        return config;
    }

    public UaTcpClient getStackClient() {
        return stackClient;
    }

    public RequestHeader newRequestHeader() {
        return null;
    }

    public RequestHeader newRequestHeader(NodeId authToken) {
        return null;
    }

    @Override
    public CompletableFuture<UaClient> connect() {
        return null;
    }

    @Override
    public CompletableFuture<UaClient> disconnect() {
        return null;
    }

    @Override
    public CompletableFuture<List<DataValue>> read(List<ReadValueId> readValueIds,
                                                   double maxAge, TimestampsToReturn timestampsToReturn) {

        CompletableFuture<List<DataValue>> future = new CompletableFuture<>();

        sessionManager.getSession().whenComplete((session, ex) -> {
            if (session != null) {
                ReadRequest request = new ReadRequest(
                        newRequestHeader(session.getAuthToken()),
                        maxAge,
                        timestampsToReturn,
                        readValueIds.toArray(new ReadValueId[readValueIds.size()])
                );

                Consumer<ReadResponse> consumer = response -> {
                    List<DataValue> results = newArrayList(response.getResults());

                    future.complete(results);
                };

                sendRequest(future, request, consumer);
            } else {
                future.completeExceptionally(ex);
            }
        });

        return future;
    }

    @Override
    public CompletableFuture<List<StatusCode>> write(List<WriteValue> writeValues) {
        return null;
    }

    @Override
    public CompletableFuture<List<BrowseResult>> browse(ViewDescription view,
                                                        UInteger maxReferencesPerNode,
                                                        List<BrowseDescription> nodesToBrowse) {

        return null;
    }

    @Override
    public CompletableFuture<List<BrowseResult>> browseNext(boolean releaseContinuationPoints,
                                                            List<ByteString> continuationPoints) {

        return null;
    }

    @SuppressWarnings("unchecked")
    private <T extends UaResponseMessage> void sendRequest(CompletableFuture<?> future,
                                                           UaRequestMessage request,
                                                           Consumer<T> responseConsumer) {

        Timeout timeout = scheduleRequestTimeout(request, future);

        stackClient.sendRequest(request).whenComplete((response, ex2) -> {
            timeout.cancel();

            if (response != null) {
                if (pending.remove(response.getResponseHeader().getRequestHandle()) != null) {
                    try {
                        responseConsumer.accept((T) response);
                    } catch (Throwable t) {
                        if (!future.isDone()) future.completeExceptionally(t);
                    }
                } else {
                    // TODO log this, increment a count, notify a listener?
                }
            } else {
                future.completeExceptionally(ex2);
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

}
