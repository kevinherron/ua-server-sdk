/*
 * OPC-UA SDK
 *
 * Copyright (C) 2014 Kevin Herron
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

package com.inductiveautomation.opcua.sdk.server.namespaces;

import javax.xml.bind.JAXBException;
import java.io.InputStream;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inductiveautomation.opcua.nodeset.UaNodeSet;
import com.inductiveautomation.opcua.nodeset.UaNodeSetParser;
import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.NamespaceTable;
import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.api.MonitoredItem;
import com.inductiveautomation.opcua.sdk.server.api.Namespace;
import com.inductiveautomation.opcua.sdk.server.api.Reference;
import com.inductiveautomation.opcua.sdk.server.api.SampledItem;
import com.inductiveautomation.opcua.sdk.server.api.nodes.Node;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaNode;
import com.inductiveautomation.opcua.sdk.server.util.SubscriptionModel;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpcUaNamespace implements Namespace {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<NodeId, UaNode> nodes = Maps.newConcurrentMap();

    private final OpcUaNamespaceModel model;

    private final SubscriptionModel subscriptionModel;

    public OpcUaNamespace(OpcUaServer server) {
        try {
            parseNodes();
        } catch (Throwable t) {
            logger.error("Error parsing NodeSet.", t);
        }

        model = new OpcUaNamespaceModel(server, nodes);
        subscriptionModel = new SubscriptionModel(this, server.getExecutorService());
    }

    @Override
    public int getNamespaceIndex() {
        return 0;
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
            if (id.getAttributeId().intValue() == AttributeIds.Value) {
                results.add(model.readValue(id.getNodeId()));
            } else {
                UaNode node = nodes.get(id.getNodeId());

                DataValue value = (node != null) ?
                        node.readAttribute(id.getAttributeId().intValue()) :
                        new DataValue(StatusCodes.Bad_NodeIdUnknown);

                results.add(value);
            }
        }

        future.complete(results);
    }

    @Override
    public void write(List<WriteValue> writeValues, CompletableFuture<List<StatusCode>> future) {
        List<StatusCode> results = Collections.nCopies(writeValues.size(), new StatusCode(StatusCodes.Bad_NotWritable));

        future.complete(results);
    }

    @Override
    public void onSampledItemsCreated(List<SampledItem> sampledItems) {
        sampledItems.stream().forEach(item -> {
            if (item.getSamplingInterval() < 100) item.setSamplingInterval(100.0);
        });

        subscriptionModel.onSampledItemsCreated(sampledItems);
    }

    @Override
    public void onSampledItemsModified(List<SampledItem> sampledItems) {
        sampledItems.stream().forEach(item -> {
            if (item.getSamplingInterval() < 100) item.setSamplingInterval(100.0);
        });

        subscriptionModel.onSampledItemsModified(sampledItems);
    }

    @Override
    public void onSampledItemsDeleted(List<SampledItem> sampledItems) {
        subscriptionModel.onSampledItemsDeleted(sampledItems);
    }

    @Override
    public void onMonitoringModeChanged(List<MonitoredItem> monitoredItems) {
        subscriptionModel.onMonitoringModeChanged(monitoredItems);
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

    private void parseNodes() throws JAXBException {
        logger.info("Parsing Opc.Ua.NodeSet2.xml...");
        InputStream nodeSetXml = getClass().getClassLoader().getResourceAsStream("Opc.Ua.NodeSet2.xml");
        UaNodeSetParser<UaNode, Reference> parser = new UaNodeSetParser<>(new UaNodeBuilder(), new UaReferenceBuilder());

        UaNodeSet<UaNode, Reference> nodeSet = parser.parse(nodeSetXml);

        nodes.putAll(nodeSet.getNodeMap());

        logger.info("...parsed {} nodes.", nodes.size());
    }

}
