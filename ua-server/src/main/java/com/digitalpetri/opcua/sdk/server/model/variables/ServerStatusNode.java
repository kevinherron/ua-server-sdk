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

import com.digitalpetri.opcua.sdk.core.model.variables.ServerStatusType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableTypeNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.ServerState;
import com.digitalpetri.opcua.stack.core.types.structured.BuildInfo;
import com.digitalpetri.opcua.stack.core.types.structured.ServerStatusDataType;

@com.digitalpetri.opcua.sdk.server.util.UaVariableNode(typeName = "0:ServerStatusType")
public class ServerStatusNode extends BaseDataVariableNode implements ServerStatusType {

    public ServerStatusNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            VariableTypeNode variableTypeNode) {

        super(nodeManager, nodeId, variableTypeNode);
    }

    public ServerStatusNode(
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
        ServerStatusDataType value = new ServerStatusDataType(
                getStartTime(),
                getCurrentTime(),
                getState(),
                getBuildInfo(),
                getSecondsTillShutdown(),
                getShutdownReason()
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public DateTime getStartTime() {
        Optional<VariableNode> component = getVariableComponent("StartTime");

        return component.map(node -> (DateTime) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getStartTimeNode() {
        Optional<VariableNode> component = getVariableComponent("StartTime");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setStartTime(DateTime value) {
        getVariableComponent("StartTime")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public DateTime getCurrentTime() {
        Optional<VariableNode> component = getVariableComponent("CurrentTime");

        return component.map(node -> (DateTime) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getCurrentTimeNode() {
        Optional<VariableNode> component = getVariableComponent("CurrentTime");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setCurrentTime(DateTime value) {
        getVariableComponent("CurrentTime")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public ServerState getState() {
        Optional<VariableNode> component = getVariableComponent("State");

        return component.map(node -> (ServerState) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getStateNode() {
        Optional<VariableNode> component = getVariableComponent("State");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setState(ServerState value) {
        getVariableComponent("State")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public BuildInfo getBuildInfo() {
        Optional<VariableNode> component = getVariableComponent("BuildInfo");

        return component.map(node -> (BuildInfo) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BuildInfoNode getBuildInfoNode() {
        Optional<VariableNode> component = getVariableComponent("BuildInfo");

        return component.map(node -> (BuildInfoNode) node).orElse(null);
    }

    @Override
    public void setBuildInfo(BuildInfo value) {
        getVariableComponent("BuildInfo")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public UInteger getSecondsTillShutdown() {
        Optional<VariableNode> component = getVariableComponent("SecondsTillShutdown");

        return component.map(node -> (UInteger) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getSecondsTillShutdownNode() {
        Optional<VariableNode> component = getVariableComponent("SecondsTillShutdown");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setSecondsTillShutdown(UInteger value) {
        getVariableComponent("SecondsTillShutdown")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public LocalizedText getShutdownReason() {
        Optional<VariableNode> component = getVariableComponent("ShutdownReason");

        return component.map(node -> (LocalizedText) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getShutdownReasonNode() {
        Optional<VariableNode> component = getVariableComponent("ShutdownReason");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setShutdownReason(LocalizedText value) {
        getVariableComponent("ShutdownReason")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

}
