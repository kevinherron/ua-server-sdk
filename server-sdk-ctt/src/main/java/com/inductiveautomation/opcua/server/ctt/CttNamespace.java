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

package com.inductiveautomation.opcua.server.ctt;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.inductiveautomation.opcua.sdk.core.AccessLevel;
import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.api.MonitoredItem;
import com.inductiveautomation.opcua.sdk.server.api.Namespace;
import com.inductiveautomation.opcua.sdk.server.api.Reference;
import com.inductiveautomation.opcua.sdk.server.api.SampledItem;
import com.inductiveautomation.opcua.sdk.server.api.nodes.Node;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaObjectNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaVariableNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaVariableNode.UaVariableNodeBuilder;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaVariableNode.ValueDelegate;
import com.inductiveautomation.opcua.sdk.server.util.SubscriptionModel;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.OverloadedType;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.XmlElement;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CttNamespace implements Namespace {

    public static final String NamespaceUri = "ctt";
    public static final int NamespaceIndex = 2;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<NodeId, UaNode> nodes = Maps.newConcurrentMap();

    private final UaNode folderNode;
    private final SubscriptionModel subscriptionModel;

    public CttNamespace(OpcUaServer server) {
        NodeId folderNodeId = new NodeId(NamespaceIndex, "CttNodes");

        folderNode = UaObjectNode.builder()
                .setNodeId(folderNodeId)
                .setBrowseName(new QualifiedName(NamespaceIndex, "CttNodes"))
                .setDisplayName(LocalizedText.english("CttNodes"))
                .setTypeDefinition(Identifiers.FolderType)
                .build();

        nodes.put(folderNodeId, folderNode);

        try {
            server.getUaNamespace().addReference(
                    Identifiers.ObjectsFolder,
                    Identifiers.Organizes,
                    true, server.getServerTable().getUri(0),
                    folderNodeId.expanded(), NodeClass.Object);
        } catch (UaException e) {
            logger.error("Error adding reference to Connections folder.", e);
        }

        subscriptionModel = new SubscriptionModel(this, server.getExecutorService());

        addStaticScalarNodes();
    }


    private static final Object[][] StaticScalarNodes = new Object[][]{
            {"Bool", Identifiers.Boolean, new Variant(false)},
            {"Byte", Identifiers.Byte, new Variant((short) 0x00, OverloadedType.UByte)},
            {"ByteString", Identifiers.ByteString, new Variant(new ByteString(new byte[]{0x01, 0x02, 0x03, 0x04}))},
            {"Double", Identifiers.Double, new Variant(3.14d)},
            {"Float", Identifiers.Float, new Variant(3.14f)},
            {"Guid", Identifiers.Guid, new Variant(UUID.randomUUID())},
            {"Int16", Identifiers.Int16, new Variant((short) 16)},
            {"Int32", Identifiers.Int32, new Variant(32)},
            {"Int64", Identifiers.Int64, new Variant(64L)},
            {"LocalizedText", Identifiers.LocalizedText, new Variant(LocalizedText.english("localized text"))},
            {"NodeId", Identifiers.NodeId, new Variant(new NodeId(1234, "abcd"))},
            {"QualifiedName", Identifiers.QualifiedName, new Variant(new QualifiedName(1234, "defg"))},
            {"SByte", Identifiers.SByte, new Variant((byte) 0x00)},
            {"String", Identifiers.String, new Variant("string value")},
            {"UtcTime", Identifiers.UtcTime, new Variant(DateTime.now())},
            {"UInt16", Identifiers.UInt16, new Variant(16, OverloadedType.UInt16)},
            {"UInt32", Identifiers.UInt32, new Variant(32L, OverloadedType.UInt32)},
            {"UInt64", Identifiers.UInt64, new Variant(64L, OverloadedType.UInt64)},
            {"XmlElement", Identifiers.XmlElement, new Variant(new XmlElement("<a>hello</a>"))},
    };

    private void addStaticScalarNodes() {
        for (Object[] os : StaticScalarNodes) {
            String name = (String) os[0];
            NodeId typeId = (NodeId) os[1];
            Variant variant = (Variant) os[2];

            UaVariableNode node = new UaVariableNodeBuilder()
                    .setNodeId(new NodeId(NamespaceIndex, name))
                    .setAccessLevel(AccessLevel.getMask(AccessLevel.ReadWrite))
                    .setBrowseName(new QualifiedName(NamespaceIndex, name))
                    .setDisplayName(LocalizedText.english(name))
                    .setDataType(typeId)
                    .setTypeDefinition(Identifiers.BaseDataVariableType)
                    .build();

            node.setValueDelegate(new ValueDelegate() {
                private final AtomicReference<Variant> value = new AtomicReference<>(variant);

                @Override
                public void accept(DataValue dataValue) {
                    value.set(dataValue.getValue());
                }

                @Override
                public DataValue get() {
                    return new DataValue(value.get());
                }
            });

            Reference reference = new Reference(
                    folderNode.getNodeId(),
                    Identifiers.Organizes,
                    node.getNodeId().expanded(),
                    node.getNodeClass(),
                    true
            );

            nodes.put(node.getNodeId(), node);
            folderNode.addReference(reference);
        }
    }


    @Override
    public int getNamespaceIndex() {
        return NamespaceIndex;
    }

    @Override
    public String getNamespaceUri() {
        return NamespaceUri;
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
            UaNode node = nodes.get(id.getNodeId());

            DataValue value = (node != null) ?
                    node.readAttribute(id.getAttributeId()) :
                    new DataValue(StatusCodes.Bad_NodeIdUnknown);

            value = DataValue.derived(value, timestamps);

            results.add(value);
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

}
