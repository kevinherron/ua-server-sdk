/*
 * Copyright 2014 Inductive Automation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.inductiveautomation.opcua.sdk.server.namespaces;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inductiveautomation.opcua.nodeset.UaNodeSet;
import com.inductiveautomation.opcua.nodeset.UaNodeSetParser;
import com.inductiveautomation.opcua.sdk.core.NamespaceTable;
import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.core.model.objects.OperationLimitsType;
import com.inductiveautomation.opcua.sdk.core.model.objects.ServerCapabilitiesType;
import com.inductiveautomation.opcua.sdk.core.model.variables.ServerStatusType;
import com.inductiveautomation.opcua.sdk.core.nodes.Node;
import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.api.DataItem;
import com.inductiveautomation.opcua.sdk.server.api.EventItem;
import com.inductiveautomation.opcua.sdk.server.api.MethodInvocationHandler;
import com.inductiveautomation.opcua.sdk.server.api.MonitoredItem;
import com.inductiveautomation.opcua.sdk.server.api.Namespace;
import com.inductiveautomation.opcua.sdk.server.api.OpcUaServerConfigLimits;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.model.DerivedVariableNode;
import com.inductiveautomation.opcua.sdk.server.model.UaMethodNode;
import com.inductiveautomation.opcua.sdk.server.model.UaNode;
import com.inductiveautomation.opcua.sdk.server.model.UaObjectNode;
import com.inductiveautomation.opcua.sdk.server.model.UaVariableNode;
import com.inductiveautomation.opcua.sdk.server.model.methods.GetMonitoredItems;
import com.inductiveautomation.opcua.sdk.server.model.objects.ServerNode;
import com.inductiveautomation.opcua.sdk.server.util.AnnotationBasedInvocationHandler;
import com.inductiveautomation.opcua.sdk.server.util.SubscriptionModel;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.types.enumerated.RedundancySupport;
import com.inductiveautomation.opcua.stack.core.types.enumerated.ServerState;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;

public class OpcUaNamespace implements Namespace, UaNodeManager {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<NodeId, UaNode> nodes = Maps.newConcurrentMap();

    private final SubscriptionModel subscriptionModel;

    private final OpcUaServer server;

    public OpcUaNamespace(OpcUaServer server) {
        this.server = server;

        try {
            parseNodes();
        } catch (Throwable t) {
            logger.error("Error parsing NodeSet.", t);
        }

        subscriptionModel = new SubscriptionModel(this, server.getExecutorService());

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
    public boolean containsNodeId(NodeId nodeId) {
        return nodes.containsKey(nodeId);
    }

    @Override
    public Optional<Node> getNode(NodeId nodeId) {
        return Optional.ofNullable(nodes.get(nodeId));
    }

    @Override
    public void addUaNode(UaNode node) {
        nodes.put(node.getNodeId(), node);
    }

    @Override
    public Optional<UaNode> getUaNode(NodeId nodeId) {
        return Optional.ofNullable(nodes.get(nodeId));
    }

    @Override
    public Optional<UaNode> getUaNode(ExpandedNodeId nodeId) {
        return nodeId.local().flatMap(this::getUaNode);
    }

    @Override
    public Optional<UaNode> removeUaNode(NodeId nodeId) {
        return Optional.ofNullable(nodes.remove(nodeId));
    }

    @Override
    public Optional<List<Reference>> getReferences(NodeId nodeId) {
        UaNode node = nodes.get(nodeId);

        if (node != null) {
            return Optional.of(node.getReferences());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void read(List<ReadValueId> readValueIds,
                     Double maxAge,
                     TimestampsToReturn timestamps,
                     CompletableFuture<List<DataValue>> future) {

        List<DataValue> results = Lists.newArrayListWithCapacity(readValueIds.size());

        for (ReadValueId id : readValueIds) {
            DataValue value;

            UaNode node = nodes.get(id.getNodeId());

            if (node != null) {
                value = node.readAttribute(
                        id.getAttributeId().intValue(),
                        timestamps,
                        id.getIndexRange()
                );
            } else {
                value = new DataValue(new StatusCode(StatusCodes.Bad_NodeIdUnknown));
            }

            results.add(value);
        }

        future.complete(results);
    }

    @Override
    public void write(List<WriteValue> writeValues, CompletableFuture<List<StatusCode>> future) {
        List<StatusCode> results = writeValues.stream().map(wv -> {
            if (nodes.containsKey(wv.getNodeId())) {
                return new StatusCode(StatusCodes.Bad_NotWritable);
            } else {
                return new StatusCode(StatusCodes.Bad_NodeIdUnknown);
            }
        }).collect(Collectors.toList());

        future.complete(results);
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

    @Override
    public void addReference(NodeId sourceNodeId,
                             NodeId referenceTypeId,
                             boolean forward,
                             String targetServerUri,
                             ExpandedNodeId targetNodeId,
                             NodeClass targetNodeClass) throws UaException {

        UaNode node = nodes.get(sourceNodeId);

        if (node != null) {
            Reference reference = new Reference(
                    sourceNodeId,
                    referenceTypeId,
                    targetNodeId,
                    targetNodeClass,
                    forward
            );

            node.addReference(reference);
        } else {
            throw new UaException(StatusCodes.Bad_NodeIdUnknown);
        }
    }

    @Override
    public Optional<MethodInvocationHandler> getInvocationHandler(NodeId methodId) {
        return Optional.ofNullable(nodes.get(methodId))
                .filter(n -> n instanceof UaMethodNode)
                .flatMap(n -> ((UaMethodNode) n).getInvocationHandler());
    }

    public UaObjectNode getObjectsFolder() {
        return (UaObjectNode) nodes.get(Identifiers.ObjectsFolder);
    }

    public ServerNode getServerNode() {
        return (ServerNode) nodes.get(Identifiers.Server);
    }

    private void parseNodes() throws JAXBException {
        logger.info("Parsing Opc.Ua.NodeSet2.xml...");
        InputStream nodeSetXml = getClass().getClassLoader().getResourceAsStream("Opc.Ua.NodeSet2.xml");

        UaNodeSetParser<UaNode, Reference> parser = new UaNodeSetParser<>(
                new UaNodeBuilder(this),
                new UaReferenceBuilder()
        );

        UaNodeSet<UaNode, Reference> nodeSet = parser.parse(nodeSetXml);

        nodes.putAll(nodeSet.getNodeMap());

        logger.info("...parsed {} nodes.", nodes.size());
    }

    private void configureServerObject() {
        OpcUaServerConfigLimits limits = server.getConfig().getLimits();

        ServerNode serverNode = (ServerNode) nodes.get(Identifiers.Server);

        replaceServerArrayNode();
        replaceNamespaceArrayNode();

        serverNode.setAuditing(false);
        serverNode.getServerDiagnostics().setEnabledFlag(false);

        ServerStatusType serverStatus = serverNode.getServerStatus();
        serverStatus.setBuildInfo(server.getConfig().getBuildInfo());
        serverStatus.setCurrentTime(DateTime.now());
        serverStatus.setSecondsTillShutdown(uint(0));
        serverStatus.setShutdownReason(LocalizedText.NullValue);
        serverStatus.setState(ServerState.Running);

        UaVariableNode currentTime = (UaVariableNode) nodes.get(Identifiers.Server_ServerStatus_CurrentTime);
        DerivedVariableNode derivedCurrentTime = new DerivedVariableNode(this, currentTime) {
            @Override
            public DataValue getValue() {
                return new DataValue(new Variant(DateTime.now()));
            }
        };
        nodes.put(Identifiers.Server_ServerStatus_CurrentTime, derivedCurrentTime);

        ServerCapabilitiesType serverCapabilities = serverNode.getServerCapabilities();
        serverCapabilities.setLocaleIdArray(new String[]{Locale.ENGLISH.getLanguage()});
        serverCapabilities.setMaxArrayLength(limits.getMaxArrayLength());
        serverCapabilities.setMaxBrowseContinuationPoints(limits.getMaxBrowseContinuationPoints());
        serverCapabilities.setMaxHistoryContinuationPoints(limits.getMaxHistoryContinuationPoints());
        serverCapabilities.setMaxQueryContinuationPoints(limits.getMaxQueryContinuationPoints());
        serverCapabilities.setMaxStringLength(limits.getMaxStringLength());
        serverCapabilities.setMinSupportedSampleRate(limits.getMinSupportedSampleRate());

        OperationLimitsType operationLimits = serverCapabilities.getOperationLimits();
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

        serverNode.getServerRedundancy().setRedundancySupport(RedundancySupport.None);

        try {
            UaMethodNode getMonitoredItems = (UaMethodNode) nodes.get(Identifiers.Server_GetMonitoredItems);

            AnnotationBasedInvocationHandler invocationHandler =
                    AnnotationBasedInvocationHandler.fromAnnotatedObject(this, new GetMonitoredItems(server));

            getMonitoredItems.setProperty(UaMethodNode.InputArguments, invocationHandler.getInputArguments());
            getMonitoredItems.setProperty(UaMethodNode.OutputArguments, invocationHandler.getOutputArguments());
            getMonitoredItems.setInvocationHandler(invocationHandler);
        } catch (Exception e) {
            logger.error("Error setting up GetMonitoredItems Method.", e);
        }
    }

    private void replaceServerArrayNode() {
        UaVariableNode originalNode = (UaVariableNode) nodes.get(Identifiers.Server_ServerArray);

        UaVariableNode derived = new DerivedVariableNode(this, originalNode) {
            @Override
            public DataValue getValue() {
                return new DataValue(new Variant(server.getServerTable().toArray()));
            }
        };

        nodes.put(derived.getNodeId(), derived);
    }

    private void replaceNamespaceArrayNode() {
        UaVariableNode originalNode = (UaVariableNode) nodes.get(Identifiers.Server_NamespaceArray);

        UaVariableNode derived = new DerivedVariableNode(this, originalNode) {
            @Override
            public DataValue getValue() {
                return new DataValue(new Variant(server.getNamespaceManager().getNamespaceTable().toArray()));
            }
        };

        nodes.put(derived.getNodeId(), derived);
    }

}
