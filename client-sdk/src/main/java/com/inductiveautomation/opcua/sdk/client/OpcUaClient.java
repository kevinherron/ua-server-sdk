package com.inductiveautomation.opcua.sdk.client;

import com.inductiveautomation.opcua.sdk.client.api.UaClient;
import com.inductiveautomation.opcua.sdk.client.fsm.StateContext;
import com.inductiveautomation.opcua.stack.client.UaTcpClient;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.*;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class OpcUaClient implements UaClient {

    private final UaTcpClient stackClient;
    private final StateContext stateContext;

    private final OpcUaClientConfig config;

    public OpcUaClient(OpcUaClientConfig config) {
        this.config = config;

        stackClient = config.getStackClient();
        stateContext = new StateContext(this);
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
    public CompletableFuture<List<DataValue>> read(List<ReadValueId> readValueIds) {
        return null;
    }

    @Override
    public CompletableFuture<List<StatusCode>> write(List<WriteValue> writeValues) {
        return null;
    }

    @Override
    public CompletableFuture<List<BrowseResult>> browse(ViewDescription view, UInteger maxReferencesPerNode, List<BrowseDescription> nodesToBrowse) {
        return null;
    }

    @Override
    public CompletableFuture<List<BrowseResult>> browseNext(boolean releaseContinuationPoints, List<ByteString> continuationPoints) {
        return null;
    }

}
