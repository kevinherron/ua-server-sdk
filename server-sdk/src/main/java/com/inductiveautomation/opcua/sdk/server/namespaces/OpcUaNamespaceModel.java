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

import java.util.Map;
import java.util.concurrent.atomic.AtomicReference;

import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.nodes.UaNode;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.enumerated.RedundancySupport;
import com.inductiveautomation.opcua.stack.core.types.enumerated.ServerState;
import com.inductiveautomation.opcua.stack.core.types.structured.BuildInfo;
import com.inductiveautomation.opcua.stack.core.types.structured.ServerStatusDataType;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class OpcUaNamespaceModel {

    private final ServerStatus serverStatus = new ServerStatus();

    private final OpcUaServer server;
    private final Map<NodeId, UaNode> nodes;

    public OpcUaNamespaceModel(OpcUaServer server, Map<NodeId, UaNode> nodes) {
        this.server = server;
        this.nodes = nodes;
    }

    public DataValue readValue(NodeId nodeId) {
        if (nodeId.equals(Identifiers.Server_Auditing)) {
            return dv(false);
        } else if (nodeId.equals(Identifiers.Server_NamespaceArray)) {
            return dv(server.getNamespaceManager().getNamespaceTable().toArray());
        } else if (nodeId.equals(Identifiers.Server_ServerArray)) {
            return dv(server.getServerTable().toArray());
        } else if (nodeId.equals(Identifiers.Server_ServerDiagnostics_EnabledFlag)) {
            return dv(false);
        } else if (nodeId.equals(Identifiers.Server_ServerRedundancy_RedundancySupport)) {
            return dv(RedundancySupport.None);
        } else if (nodeId.equals(Identifiers.Server_ServerStatus)) {
            return dv(serverStatus.serverStatus());
        } else if (nodeId.equals(Identifiers.Server_ServerStatus_BuildInfo)) {
            return dv(serverStatus.buildInfo());
        } else if (nodeId.equals(Identifiers.Server_ServerStatus_BuildInfo_BuildDate)) {
            return dv(serverStatus.buildInfo().getBuildDate());
        } else if (nodeId.equals(Identifiers.Server_ServerStatus_BuildInfo_BuildNumber)) {
            return dv(serverStatus.buildInfo().getBuildNumber());
        } else if (nodeId.equals(Identifiers.Server_ServerStatus_BuildInfo_ManufacturerName)) {
            return dv(serverStatus.buildInfo().getManufacturerName());
        } else if (nodeId.equals(Identifiers.Server_ServerStatus_BuildInfo_ProductName)) {
            return dv(serverStatus.buildInfo().getProductName());
        } else if (nodeId.equals(Identifiers.Server_ServerStatus_BuildInfo_ProductUri)) {
            return dv(serverStatus.buildInfo().getProductUri());
        } else if (nodeId.equals(Identifiers.Server_ServerStatus_BuildInfo_SoftwareVersion)) {
            return dv(serverStatus.buildInfo().getSoftwareVersion());
        } else if (nodeId.equals(Identifiers.Server_ServerStatus_CurrentTime)) {
            return dv(serverStatus.currentTime());
        } else if (nodeId.equals(Identifiers.Server_ServerStatus_SecondsTillShutdown)) {
            return dv(serverStatus.secondsTillShutdown());
        } else if (nodeId.equals(Identifiers.Server_ServerStatus_ShutdownReason)) {
            return dv(serverStatus.shutdownReason());
        } else if (nodeId.equals(Identifiers.Server_ServerStatus_StartTime)) {
            return dv(serverStatus.startTime());
        } else if (nodeId.equals(Identifiers.Server_ServerStatus_State)) {
            return dv(serverStatus.state());
        }

        /* ServerCapabilities.OperationLimits */
        else if (nodeId.equals(Identifiers.Server_ServerCapabilities_OperationLimits_MaxMonitoredItemsPerCall)) {
            return dv(server.getConfig().getServerCapabilities().getOperationLimits().getMaxMonitoredItemsPerCall());
        } else if (nodeId.equals(Identifiers.Server_ServerCapabilities_OperationLimits_MaxNodesPerBrowse)) {
            return dv(server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerBrowse());
        } else if (nodeId.equals(Identifiers.Server_ServerCapabilities_OperationLimits_MaxNodesPerHistoryReadData)) {
            return dv(server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerHistoryReadData());
        } else if (nodeId.equals(Identifiers.Server_ServerCapabilities_OperationLimits_MaxNodesPerHistoryReadEvents)) {
            return dv(server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerHistoryReadEvents());
        } else if (nodeId.equals(Identifiers.Server_ServerCapabilities_OperationLimits_MaxNodesPerHistoryUpdateData)) {
            return dv(server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerHistoryUpdateData());
        } else if (nodeId.equals(Identifiers.Server_ServerCapabilities_OperationLimits_MaxNodesPerHistoryUpdateEvents)) {
            return dv(server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerHistoryUpdateEvents());
        } else if (nodeId.equals(Identifiers.Server_ServerCapabilities_OperationLimits_MaxNodesPerMethodCall)) {
            return dv(server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerMethodCall());
        } else if (nodeId.equals(Identifiers.Server_ServerCapabilities_OperationLimits_MaxNodesPerNodeManagement)) {
            return dv(server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerNodeManagement());
        } else if (nodeId.equals(Identifiers.Server_ServerCapabilities_OperationLimits_MaxNodesPerRead)) {
            return dv(server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerRead());
        } else if (nodeId.equals(Identifiers.Server_ServerCapabilities_OperationLimits_MaxNodesPerRegisterNodes)) {
            return dv(server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerRegisterNodes());
        } else if (nodeId.equals(Identifiers.Server_ServerCapabilities_OperationLimits_MaxNodesPerTranslateBrowsePathsToNodeIds)) {
            return dv(server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerTranslateBrowsePaths());
        } else if (nodeId.equals(Identifiers.Server_ServerCapabilities_OperationLimits_MaxNodesPerWrite)) {
            return dv(server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerWrite());
        }

        /* Fall back to UaNode's value */
        else {
            UaNode node = nodes.get(nodeId);

            return (node != null) ?
                    node.readAttribute(AttributeIds.Value) :
                    new DataValue(StatusCodes.Bad_NodeIdUnknown);
        }
    }

    private class ServerStatus {

        private final AtomicReference<BuildInfo> buildInfo = new AtomicReference<>();

        private final DateTime startTime = new DateTime();

        DateTime currentTime() {
            return new DateTime();
        }

        public DateTime startTime() {
            return startTime;
        }

        ServerState state() {
            return ServerState.Running;
        }

        Long secondsTillShutdown() {
            return 0L;
        }

        LocalizedText shutdownReason() {
            return LocalizedText.english("");
        }

        public ServerStatusDataType serverStatus() {
            return new ServerStatusDataType(startTime, currentTime(), state(), buildInfo(), uint(secondsTillShutdown()), shutdownReason());
        }

        public BuildInfo buildInfo() {
            buildInfo.compareAndSet(null, server.getConfig().getBuildInfo());
            return buildInfo.get();
        }

    }

    private static DataValue dv(Object o) {
        return new DataValue(new Variant(o), StatusCode.Good, DateTime.now(), DateTime.now());
    }

}
