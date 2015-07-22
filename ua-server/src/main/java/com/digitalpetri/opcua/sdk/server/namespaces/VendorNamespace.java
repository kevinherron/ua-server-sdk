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

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.core.Reference;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.api.DataItem;
import com.digitalpetri.opcua.sdk.server.api.MonitoredItem;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.model.UaNode;
import com.digitalpetri.opcua.sdk.server.model.UaObjectNode;
import com.digitalpetri.opcua.sdk.server.model.UaVariableNode;
import com.digitalpetri.opcua.sdk.server.util.SubscriptionModel;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.WriteValue;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.sun.management.OperatingSystemMXBean;
import com.sun.management.UnixOperatingSystemMXBean;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.ushort;
import static java.util.stream.Collectors.toList;

public class VendorNamespace implements UaNamespace {

    public static final UShort NAMESPACE_INDEX = ushort(1);

    private final Map<NodeId, UaNode> nodes = Maps.newConcurrentMap();

    private final SubscriptionModel subscriptionModel;

    private final OpcUaServer server;
    private final String namespaceUri;

    public VendorNamespace(OpcUaServer server, String namespaceUri) {
        this.server = server;
        this.namespaceUri = namespaceUri;

        subscriptionModel = new SubscriptionModel(server, this);

        addVendorServerInfoNodes();
    }

    @Override
    public UShort getNamespaceIndex() {
        return NAMESPACE_INDEX;
    }

    @Override
    public String getNamespaceUri() {
        return namespaceUri;
    }

    @Override
    public void addNode(UaNode node) {
        nodes.put(node.getNodeId(), node);
    }

    @Override
    public Optional<UaNode> getNode(NodeId nodeId) {
        return Optional.ofNullable(nodes.get(nodeId));
    }

    @Override
    public Optional<UaNode> getNode(ExpandedNodeId nodeId) {
        return nodeId.local().flatMap(this::getNode);
    }

    @Override
    public Optional<UaNode> removeNode(NodeId nodeId) {
        return Optional.ofNullable(nodes.remove(nodeId));
    }

    @Override
    public CompletableFuture<List<Reference>> getReferences(NodeId nodeId) {
        UaNode node = nodes.get(nodeId);

        if (node != null) {
            return CompletableFuture.completedFuture(node.getReferences());
        } else {
            CompletableFuture<List<Reference>> f = new CompletableFuture<>();
            f.completeExceptionally(new UaException(StatusCodes.Bad_NodeIdUnknown));
            return f;
        }
    }

    @Override
    public void read(ReadContext context, Double maxAge, TimestampsToReturn timestamps, List<ReadValueId> readValueIds) {
        List<DataValue> results = Lists.newArrayListWithCapacity(readValueIds.size());

        for (ReadValueId id : readValueIds) {
            UaNode node = nodes.get(id.getNodeId());

            DataValue value = (node != null) ?
                    node.readAttribute(id.getAttributeId().intValue()) :
                    new DataValue(StatusCodes.Bad_NodeIdUnknown);

            results.add(value);
        }

        context.complete(results);
    }

    @Override
    public void write(WriteContext context, List<WriteValue> writeValues) {
        List<StatusCode> results = writeValues.stream().map(value -> {
            if (nodes.containsKey(value.getNodeId())) {
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

    private void addVendorServerInfoNodes() {
        server.getUaNamespace().getNode(Identifiers.Server_VendorServerInfo).ifPresent(node -> {
            UaObjectNode vendorServerInfo = (UaObjectNode) node;

            OperatingSystemMXBean osBean = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            MemoryMXBean memoryBean = ManagementFactory.getMemoryMXBean();

            UaVariableNode processCpuLoad = new UaVariableNode(
                    this,
                    new NodeId(1, "VendorServerInfo/ProcessCpuLoad"),
                    new QualifiedName(1, "ProcessCpuLoad"),
                    LocalizedText.english("ProcessCpuLoad")) {

                @Override
                public DataValue getValue() {
                    return new DataValue(new Variant(osBean.getProcessCpuLoad()));
                }
            };
            processCpuLoad.setDataType(Identifiers.Double);

            UaVariableNode systemCpuLoad = new UaVariableNode(
                    this,
                    new NodeId(1, "VendorServerInfo/SystemCpuLoad"),
                    new QualifiedName(1, "SystemCpuLoad"),
                    LocalizedText.english("SystemCpuLoad")) {
                @Override
                public DataValue getValue() {
                    return new DataValue(new Variant(osBean.getSystemCpuLoad()));
                }
            };
            systemCpuLoad.setDataType(Identifiers.Double);

            UaVariableNode usedMemory = new UaVariableNode(
                    this,
                    new NodeId(1, "VendorServerInfo/UsedMemory"),
                    new QualifiedName(1, "UsedMemory"),
                    LocalizedText.english("UsedMemory")) {
                @Override
                public DataValue getValue() {
                    return new DataValue(new Variant(memoryBean.getHeapMemoryUsage().getUsed()));
                }
            };
            usedMemory.setDataType(Identifiers.Int64);

            UaVariableNode maxMemory = new UaVariableNode(
                    this,
                    new NodeId(1, "VendorServerInfo/MaxMemory"),
                    new QualifiedName(1, "MaxMemory"),
                    LocalizedText.english("MaxMemory")) {
                @Override
                public DataValue getValue() {
                    return new DataValue(new Variant(memoryBean.getHeapMemoryUsage().getMax()));
                }
            };
            maxMemory.setDataType(Identifiers.Int64);

            UaVariableNode osName = new UaVariableNode(
                    this,
                    new NodeId(1, "VendorServerInfo/OsName"),
                    new QualifiedName(1, "OsName"),
                    LocalizedText.english("OsName")) {
                @Override
                public DataValue getValue() {
                    return new DataValue(new Variant(osBean.getName()));
                }
            };
            osName.setDataType(Identifiers.String);


            UaVariableNode osArch = new UaVariableNode(
                    this,
                    new NodeId(1, "VendorServerInfo/OsArch"),
                    new QualifiedName(1, "OsArch"),
                    LocalizedText.english("OsArch")) {
                @Override
                public DataValue getValue() {
                    return new DataValue(new Variant(osBean.getArch()));
                }
            };
            osArch.setDataType(Identifiers.String);

            UaVariableNode osVersion = new UaVariableNode(
                    this,
                    new NodeId(1, "VendorServerInfo/OsVersion"),
                    new QualifiedName(1, "OsVersion"),
                    LocalizedText.english("OsVersion")) {
                @Override
                public DataValue getValue() {
                    return new DataValue(new Variant(osBean.getVersion()));
                }
            };
            osVersion.setDataType(Identifiers.String);

            vendorServerInfo.addComponent(processCpuLoad);
            vendorServerInfo.addComponent(systemCpuLoad);
            vendorServerInfo.addComponent(usedMemory);
            vendorServerInfo.addComponent(maxMemory);
            vendorServerInfo.addComponent(osName);
            vendorServerInfo.addComponent(osArch);
            vendorServerInfo.addComponent(osVersion);

            if (osBean instanceof UnixOperatingSystemMXBean) {
                UnixOperatingSystemMXBean unixBean = (UnixOperatingSystemMXBean) osBean;

                UaVariableNode openFileDescriptors = new UaVariableNode(
                        this,
                        new NodeId(1, "VendorServerInfo/OpenFileDescriptors"),
                        new QualifiedName(1, "OpenFileDescriptors"),
                        LocalizedText.english("OpenFileDescriptors")) {
                    @Override
                    public DataValue getValue() {
                        return new DataValue(new Variant(unixBean.getOpenFileDescriptorCount()));
                    }
                };
                openFileDescriptors.setDataType(Identifiers.Int64);

                UaVariableNode maxFileDescriptors = new UaVariableNode(
                        this,
                        new NodeId(1, "VendorServerInfo/MaxFileDescriptors"),
                        new QualifiedName(1, "MaxFileDescriptors"),
                        LocalizedText.english("MaxFileDescriptors")) {
                    @Override
                    public DataValue getValue() {
                        return new DataValue(new Variant(unixBean.getMaxFileDescriptorCount()));
                    }
                };
                maxFileDescriptors.setDataType(Identifiers.Int64);

                vendorServerInfo.addComponent(openFileDescriptors);
                vendorServerInfo.addComponent(maxFileDescriptors);
            }
        });
    }

}
