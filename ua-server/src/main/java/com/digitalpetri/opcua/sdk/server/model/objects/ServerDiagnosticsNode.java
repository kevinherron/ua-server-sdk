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

package com.digitalpetri.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.objects.ServerDiagnosticsType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.PropertyNode;
import com.digitalpetri.opcua.sdk.server.model.variables.SamplingIntervalDiagnosticsArrayNode;
import com.digitalpetri.opcua.sdk.server.model.variables.ServerDiagnosticsSummaryNode;
import com.digitalpetri.opcua.sdk.server.model.variables.SubscriptionDiagnosticsArrayNode;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.SamplingIntervalDiagnosticsDataType;
import com.digitalpetri.opcua.stack.core.types.structured.ServerDiagnosticsSummaryDataType;
import com.digitalpetri.opcua.stack.core.types.structured.SubscriptionDiagnosticsDataType;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:ServerDiagnosticsType")
public class ServerDiagnosticsNode extends BaseObjectNode implements ServerDiagnosticsType {

    public ServerDiagnosticsNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    @Override
    public Boolean getEnabledFlag() {
        Optional<Boolean> property = getProperty(ServerDiagnosticsType.ENABLED_FLAG);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getEnabledFlagNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ServerDiagnosticsType.ENABLED_FLAG.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setEnabledFlag(Boolean value) {
        setProperty(ServerDiagnosticsType.ENABLED_FLAG, value);
    }

    @Override
    public SessionsDiagnosticsSummaryNode getSessionsDiagnosticsSummaryNode() {
        Optional<ObjectNode> component = getObjectComponent("SessionsDiagnosticsSummary");

        return component.map(node -> (SessionsDiagnosticsSummaryNode) node).orElse(null);
    }

    @Override
    public ServerDiagnosticsSummaryDataType getServerDiagnosticsSummary() {
        Optional<VariableNode> component = getVariableComponent("ServerDiagnosticsSummary");

        return component.map(node -> (ServerDiagnosticsSummaryDataType) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public ServerDiagnosticsSummaryNode getServerDiagnosticsSummaryNode() {
        Optional<VariableNode> component = getVariableComponent("ServerDiagnosticsSummary");

        return component.map(node -> (ServerDiagnosticsSummaryNode) node).orElse(null);
    }

    @Override
    public void setServerDiagnosticsSummary(ServerDiagnosticsSummaryDataType value) {
        getVariableComponent("ServerDiagnosticsSummary")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public SamplingIntervalDiagnosticsDataType[] getSamplingIntervalDiagnosticsArray() {
        Optional<VariableNode> component = getVariableComponent("SamplingIntervalDiagnosticsArray");

        return component.map(node -> (SamplingIntervalDiagnosticsDataType[]) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public SamplingIntervalDiagnosticsArrayNode getSamplingIntervalDiagnosticsArrayNode() {
        Optional<VariableNode> component = getVariableComponent("SamplingIntervalDiagnosticsArray");

        return component.map(node -> (SamplingIntervalDiagnosticsArrayNode) node).orElse(null);
    }

    @Override
    public void setSamplingIntervalDiagnosticsArray(SamplingIntervalDiagnosticsDataType[] value) {
        getVariableComponent("SamplingIntervalDiagnosticsArray")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public SubscriptionDiagnosticsDataType[] getSubscriptionDiagnosticsArray() {
        Optional<VariableNode> component = getVariableComponent("SubscriptionDiagnosticsArray");

        return component.map(node -> (SubscriptionDiagnosticsDataType[]) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public SubscriptionDiagnosticsArrayNode getSubscriptionDiagnosticsArrayNode() {
        Optional<VariableNode> component = getVariableComponent("SubscriptionDiagnosticsArray");

        return component.map(node -> (SubscriptionDiagnosticsArrayNode) node).orElse(null);
    }

    @Override
    public void setSubscriptionDiagnosticsArray(SubscriptionDiagnosticsDataType[] value) {
        getVariableComponent("SubscriptionDiagnosticsArray")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

}
