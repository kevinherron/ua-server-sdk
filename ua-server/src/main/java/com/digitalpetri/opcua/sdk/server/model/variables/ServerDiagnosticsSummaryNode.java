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

package com.digitalpetri.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.variables.ServerDiagnosticsSummaryType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableTypeNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.ServerDiagnosticsSummaryDataType;

@com.digitalpetri.opcua.sdk.server.util.UaVariableNode(typeName = "0:ServerDiagnosticsSummaryType")
public class ServerDiagnosticsSummaryNode extends BaseDataVariableNode implements ServerDiagnosticsSummaryType {

    public ServerDiagnosticsSummaryNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            VariableTypeNode variableTypeNode) {

        super(nodeManager, nodeId, variableTypeNode);
    }

    public ServerDiagnosticsSummaryNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            DataValue value,
            NodeId dataType,
            Integer valueRank,
            Optional<UInteger[]> arrayDimensions,
            UByte accessLevel,
            UByte userAccessLevel,
            Optional<Double> minimumSamplingInterval,
            boolean historizing) {

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask,
                value, dataType, valueRank, arrayDimensions, accessLevel, userAccessLevel, minimumSamplingInterval, historizing);
    }

    @Override
    public DataValue getValue() {
        ServerDiagnosticsSummaryDataType value = new ServerDiagnosticsSummaryDataType(
                getServerViewCount(),
                getCurrentSessionCount(),
                getCumulatedSessionCount(),
                getSecurityRejectedSessionCount(),
                getRejectedSessionCount(),
                getSessionTimeoutCount(),
                getSessionAbortCount(),
                getPublishingIntervalCount(),
                getCurrentSubscriptionCount(),
                getCumulatedSubscriptionCount(),
                getSecurityRejectedRequestsCount(),
                getRejectedRequestsCount()
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public UInteger getServerViewCount() {
        Optional<VariableNode> component = getVariableComponent("ServerViewCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getServerViewCountNode() {
        Optional<VariableNode> component = getVariableComponent("ServerViewCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setServerViewCount(UInteger value) {
        getVariableComponent("ServerViewCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getCurrentSessionCount() {
        Optional<VariableNode> component = getVariableComponent("CurrentSessionCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getCurrentSessionCountNode() {
        Optional<VariableNode> component = getVariableComponent("CurrentSessionCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setCurrentSessionCount(UInteger value) {
        getVariableComponent("CurrentSessionCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getCumulatedSessionCount() {
        Optional<VariableNode> component = getVariableComponent("CumulatedSessionCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getCumulatedSessionCountNode() {
        Optional<VariableNode> component = getVariableComponent("CumulatedSessionCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setCumulatedSessionCount(UInteger value) {
        getVariableComponent("CumulatedSessionCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getSecurityRejectedSessionCount() {
        Optional<VariableNode> component = getVariableComponent("SecurityRejectedSessionCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getSecurityRejectedSessionCountNode() {
        Optional<VariableNode> component = getVariableComponent("SecurityRejectedSessionCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setSecurityRejectedSessionCount(UInteger value) {
        getVariableComponent("SecurityRejectedSessionCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getRejectedSessionCount() {
        Optional<VariableNode> component = getVariableComponent("RejectedSessionCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getRejectedSessionCountNode() {
        Optional<VariableNode> component = getVariableComponent("RejectedSessionCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setRejectedSessionCount(UInteger value) {
        getVariableComponent("RejectedSessionCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getSessionTimeoutCount() {
        Optional<VariableNode> component = getVariableComponent("SessionTimeoutCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getSessionTimeoutCountNode() {
        Optional<VariableNode> component = getVariableComponent("SessionTimeoutCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setSessionTimeoutCount(UInteger value) {
        getVariableComponent("SessionTimeoutCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getSessionAbortCount() {
        Optional<VariableNode> component = getVariableComponent("SessionAbortCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getSessionAbortCountNode() {
        Optional<VariableNode> component = getVariableComponent("SessionAbortCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setSessionAbortCount(UInteger value) {
        getVariableComponent("SessionAbortCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getPublishingIntervalCount() {
        Optional<VariableNode> component = getVariableComponent("PublishingIntervalCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getPublishingIntervalCountNode() {
        Optional<VariableNode> component = getVariableComponent("PublishingIntervalCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setPublishingIntervalCount(UInteger value) {
        getVariableComponent("PublishingIntervalCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getCurrentSubscriptionCount() {
        Optional<VariableNode> component = getVariableComponent("CurrentSubscriptionCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getCurrentSubscriptionCountNode() {
        Optional<VariableNode> component = getVariableComponent("CurrentSubscriptionCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setCurrentSubscriptionCount(UInteger value) {
        getVariableComponent("CurrentSubscriptionCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getCumulatedSubscriptionCount() {
        Optional<VariableNode> component = getVariableComponent("CumulatedSubscriptionCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getCumulatedSubscriptionCountNode() {
        Optional<VariableNode> component = getVariableComponent("CumulatedSubscriptionCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setCumulatedSubscriptionCount(UInteger value) {
        getVariableComponent("CumulatedSubscriptionCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getSecurityRejectedRequestsCount() {
        Optional<VariableNode> component = getVariableComponent("SecurityRejectedRequestsCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getSecurityRejectedRequestsCountNode() {
        Optional<VariableNode> component = getVariableComponent("SecurityRejectedRequestsCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setSecurityRejectedRequestsCount(UInteger value) {
        getVariableComponent("SecurityRejectedRequestsCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getRejectedRequestsCount() {
        Optional<VariableNode> component = getVariableComponent("RejectedRequestsCount");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getRejectedRequestsCountNode() {
        Optional<VariableNode> component = getVariableComponent("RejectedRequestsCount");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setRejectedRequestsCount(UInteger value) {
        getVariableComponent("RejectedRequestsCount")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

}
