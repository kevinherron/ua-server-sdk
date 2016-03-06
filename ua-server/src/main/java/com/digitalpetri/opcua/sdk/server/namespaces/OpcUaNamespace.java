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

package com.digitalpetri.opcua.sdk.server.namespaces;

import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

import com.digitalpetri.opcua.sdk.core.NamespaceTable;
import com.digitalpetri.opcua.sdk.core.Reference;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.api.DataItem;
import com.digitalpetri.opcua.sdk.server.api.EventItem;
import com.digitalpetri.opcua.sdk.server.api.MethodInvocationHandler;
import com.digitalpetri.opcua.sdk.server.api.MethodInvocationHandler.NotImplementedHandler;
import com.digitalpetri.opcua.sdk.server.api.MonitoredItem;
import com.digitalpetri.opcua.sdk.server.api.Namespace;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.api.config.OpcUaServerConfigLimits;
import com.digitalpetri.opcua.sdk.server.model.DerivedVariableNode;
import com.digitalpetri.opcua.sdk.server.model.UaMethodNode;
import com.digitalpetri.opcua.sdk.server.model.UaNode;
import com.digitalpetri.opcua.sdk.server.model.UaObjectNode;
import com.digitalpetri.opcua.sdk.server.model.UaVariableNode;
import com.digitalpetri.opcua.sdk.server.model.methods.GetMonitoredItems;
import com.digitalpetri.opcua.sdk.server.model.methods.ResendData;
import com.digitalpetri.opcua.sdk.server.model.objects.OperationLimitsNode;
import com.digitalpetri.opcua.sdk.server.model.objects.ServerCapabilitiesNode;
import com.digitalpetri.opcua.sdk.server.model.objects.ServerNode;
import com.digitalpetri.opcua.sdk.server.model.variables.ServerStatusNode;
import com.digitalpetri.opcua.sdk.server.namespaces.loader.UaNodeLoader;
import com.digitalpetri.opcua.sdk.server.util.AnnotationBasedInvocationHandler;
import com.digitalpetri.opcua.sdk.server.util.SubscriptionModel;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;
import com.digitalpetri.opcua.stack.core.types.enumerated.RedundancySupport;
import com.digitalpetri.opcua.stack.core.types.enumerated.ServerState;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.WriteValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.ubyte;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;
import static com.google.common.collect.Lists.newArrayListWithCapacity;
import static java.util.stream.Collectors.toList;

public class OpcUaNamespace implements Namespace {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final UaNodeManager nodeManager;
    private final SubscriptionModel subscriptionModel;

    private final OpcUaServer server;

    public OpcUaNamespace(OpcUaServer server) {
        this.server = server;

        nodeManager = server.getNodeManager();
        subscriptionModel = new SubscriptionModel(server, this);

        loadNodes();
        configureServerObject();
    }

    @Override
    public UShort getNamespaceIndex() {
        return ushort(0);
    }

    @Override
    public String getNamespaceUri() {
        return NamespaceTable.OpcUaNamespace;
    }

    @Override
    public CompletableFuture<List<Reference>> getReferences(NodeId nodeId) {
        UaNode node = nodeManager.get(nodeId);

        if (node != null) {
            return CompletableFuture.completedFuture(node.getReferences());
        } else {
            CompletableFuture<List<Reference>> f = new CompletableFuture<>();
            f.completeExceptionally(new UaException(StatusCodes.Bad_NodeIdUnknown));
            return f;
        }
    }

    @Override
    public void read(ReadContext context, Double maxAge,
                     TimestampsToReturn timestamps,
                     List<ReadValueId> readValueIds) {

        List<DataValue> results = newArrayListWithCapacity(readValueIds.size());

        for (ReadValueId id : readValueIds) {
            DataValue value;

            UaNode node = nodeManager.get(id.getNodeId());

            if (node != null) {
                value = node.readAttribute(
                        id.getAttributeId().intValue(),
                        timestamps,
                        id.getIndexRange());
            } else {
                value = new DataValue(new StatusCode(StatusCodes.Bad_NodeIdUnknown));
            }

            results.add(value);
        }

        context.complete(results);
    }

    @Override
    public void write(WriteContext context, List<WriteValue> writeValues) {
        List<StatusCode> results = writeValues.stream().map(value -> {
            if (nodeManager.containsKey(value.getNodeId())) {
                return new StatusCode(StatusCodes.Bad_NotWritable);
            } else {
                return new StatusCode(StatusCodes.Bad_NodeIdUnknown);
            }
        }).collect(toList());

        context.complete(results);
    }

    @Override
    public void onDataItemsCreated(List<DataItem> dataItems) {
        subscriptionModel.onDataItemsCreated(dataItems);
    }

    @Override
    public void onDataItemsModified(List<DataItem> dataItems) {
        subscriptionModel.onDataItemsModified(dataItems);
    }

    @Override
    public void onDataItemsDeleted(List<DataItem> dataItems) {
        subscriptionModel.onDataItemsDeleted(dataItems);
    }

    @Override
    public void onMonitoringModeChanged(List<MonitoredItem> monitoredItems) {
        subscriptionModel.onMonitoringModeChanged(monitoredItems);
    }

    @Override
    public void onEventItemsCreated(List<EventItem> eventItems) {
        eventItems.stream()
                .filter(MonitoredItem::isSamplingEnabled)
                .forEach(item -> server.getEventBus().register(item));
    }

    @Override
    public void onEventItemsModified(List<EventItem> eventItems) {
        for (EventItem item : eventItems) {
            if (item.isSamplingEnabled()) {
                server.getEventBus().register(item);
            } else {
                server.getEventBus().unregister(item);
            }
        }
    }

    @Override
    public void onEventItemsDeleted(List<EventItem> eventItems) {
        eventItems.forEach(item -> server.getEventBus().unregister(item));
    }

    public void addReference(NodeId sourceNodeId,
                             NodeId referenceTypeId,
                             boolean forward,
                             ExpandedNodeId targetNodeId,
                             NodeClass targetNodeClass) throws UaException {

        UaNode node = nodeManager.get(sourceNodeId);

        if (node != null) {
            Reference reference = new Reference(
                    sourceNodeId,
                    referenceTypeId,
                    targetNodeId,
                    targetNodeClass,
                    forward);

            node.addReference(reference);
        } else {
            throw new UaException(StatusCodes.Bad_NodeIdUnknown);
        }
    }

    @Override
    public Optional<MethodInvocationHandler> getInvocationHandler(NodeId methodId) {
        return Optional.ofNullable(nodeManager.get(methodId))
                .filter(n -> n instanceof UaMethodNode)
                .map(n -> {
                    UaMethodNode m = (UaMethodNode) n;
                    return m.getInvocationHandler()
                            .orElse(new NotImplementedHandler());
                });
    }

    public UaObjectNode getObjectsFolder() {
        return (UaObjectNode) nodeManager.get(Identifiers.ObjectsFolder);
    }

    public ServerNode getServerNode() {
        return (ServerNode) nodeManager.get(Identifiers.Server);
    }

    private void loadNodes() {
        try {
            long startTime = System.nanoTime();

            new UaNodeLoader(nodeManager).loadNodes();

            long endTime = System.nanoTime();
            long deltaMs = TimeUnit.MILLISECONDS.convert(endTime - startTime, TimeUnit.NANOSECONDS);

            logger.info("Loaded nodes in {}ms.", deltaMs);
        } catch (Exception e) {
            logger.error("Error loading nodes.", e);
        }
    }

    private void configureServerObject() {
        OpcUaServerConfigLimits limits = server.getConfig().getLimits();

        ServerNode serverNode = (ServerNode) nodeManager.get(Identifiers.Server);

        replaceServerArrayNode();
        replaceNamespaceArrayNode();

        serverNode.setAuditing(false);
        serverNode.getServerDiagnosticsNode().setEnabledFlag(false);
        serverNode.setServiceLevel(ubyte(255));

        ServerStatusNode serverStatus = serverNode.getServerStatusNode();
        serverStatus.setBuildInfo(server.getConfig().getBuildInfo());
        serverStatus.setCurrentTime(DateTime.now());
        serverStatus.setSecondsTillShutdown(uint(0));
        serverStatus.setShutdownReason(LocalizedText.NULL_VALUE);
        serverStatus.setState(ServerState.Running);
        serverStatus.setStartTime(DateTime.now());

        UaVariableNode currentTime = (UaVariableNode) nodeManager.get(Identifiers.Server_ServerStatus_CurrentTime);
        DerivedVariableNode derivedCurrentTime = new DerivedVariableNode(nodeManager, currentTime) {
            @Override
            public DataValue getValue() {
                return new DataValue(new Variant(DateTime.now()));
            }
        };
        nodeManager.put(Identifiers.Server_ServerStatus_CurrentTime, derivedCurrentTime);

        ServerCapabilitiesNode serverCapabilities = serverNode.getServerCapabilitiesNode();
        serverCapabilities.setLocaleIdArray(new String[]{Locale.ENGLISH.getLanguage()});
        serverCapabilities.setMaxArrayLength(limits.getMaxArrayLength());
        serverCapabilities.setMaxBrowseContinuationPoints(limits.getMaxBrowseContinuationPoints());
        serverCapabilities.setMaxHistoryContinuationPoints(limits.getMaxHistoryContinuationPoints());
        serverCapabilities.setMaxQueryContinuationPoints(limits.getMaxQueryContinuationPoints());
        serverCapabilities.setMaxStringLength(limits.getMaxStringLength());
        serverCapabilities.setMinSupportedSampleRate(limits.getMinSupportedSampleRate());

        OperationLimitsNode operationLimits = serverCapabilities.getOperationLimitsNode();
        operationLimits.setMaxMonitoredItemsPerCall(limits.getMaxMonitoredItemsPerCall());
        operationLimits.setMaxNodesPerBrowse(limits.getMaxNodesPerBrowse());
        operationLimits.setMaxNodesPerHistoryReadData(limits.getMaxNodesPerHistoryReadData());
        operationLimits.setMaxNodesPerHistoryReadEvents(limits.getMaxNodesPerHistoryReadEvents());
        operationLimits.setMaxNodesPerHistoryUpdateData(limits.getMaxNodesPerHistoryUpdateData());
        operationLimits.setMaxNodesPerHistoryUpdateEvents(limits.getMaxNodesPerHistoryUpdateEvents());
        operationLimits.setMaxNodesPerMethodCall(limits.getMaxNodesPerMethodCall());
        operationLimits.setMaxNodesPerNodeManagement(limits.getMaxNodesPerNodeManagement());
        operationLimits.setMaxNodesPerRead(limits.getMaxNodesPerRead());
        operationLimits.setMaxNodesPerRegisterNodes(limits.getMaxNodesPerRegisterNodes());
        operationLimits.setMaxNodesPerTranslateBrowsePathsToNodeIds(limits.getMaxNodesPerTranslateBrowsePathsToNodeIds());
        operationLimits.setMaxNodesPerWrite(limits.getMaxNodesPerWrite());

        serverNode.getServerRedundancyNode().setRedundancySupport(RedundancySupport.None);

        try {
            UaMethodNode getMonitoredItems = (UaMethodNode) nodeManager.get(Identifiers.Server_GetMonitoredItems);

            AnnotationBasedInvocationHandler handler =
                    AnnotationBasedInvocationHandler.fromAnnotatedObject(nodeManager, new GetMonitoredItems(server));

            getMonitoredItems.setInvocationHandler(handler);
            getMonitoredItems.setInputArguments(handler.getInputArguments());
            getMonitoredItems.setOutputArguments(handler.getOutputArguments());
        } catch (Exception e) {
            logger.error("Error setting up GetMonitoredItems Method.", e);
        }

        try {
            UaMethodNode resendData = (UaMethodNode) nodeManager.get(Identifiers.Server_ResendData);

            AnnotationBasedInvocationHandler handler =
                    AnnotationBasedInvocationHandler.fromAnnotatedObject(nodeManager, new ResendData(server));

            resendData.setInvocationHandler(handler);
            resendData.setInputArguments(handler.getInputArguments());
        } catch (Exception e) {
            logger.error("Error setting up ResendData Method.", e);
        }
    }

    private void replaceServerArrayNode() {
        UaVariableNode originalNode = (UaVariableNode) nodeManager.get(Identifiers.Server_ServerArray);

        UaVariableNode derived = new DerivedVariableNode(nodeManager, originalNode) {
            @Override
            public DataValue getValue() {
                return new DataValue(new Variant(server.getServerTable().toArray()));
            }
        };

        nodeManager.put(derived.getNodeId(), derived);
    }

    private void replaceNamespaceArrayNode() {
        UaVariableNode originalNode = (UaVariableNode) nodeManager.get(Identifiers.Server_NamespaceArray);

        UaVariableNode derived = new DerivedVariableNode(nodeManager, originalNode) {
            @Override
            public DataValue getValue() {
                return new DataValue(new Variant(server.getNamespaceManager().getNamespaceTable().toArray()));
            }
        };

        nodeManager.put(derived.getNodeId(), derived);
    }

}
