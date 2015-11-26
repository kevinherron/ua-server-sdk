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

import com.digitalpetri.opcua.sdk.core.model.variables.ProgramDiagnosticType;
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
import com.digitalpetri.opcua.stack.core.types.structured.Argument;
import com.digitalpetri.opcua.stack.core.types.structured.ProgramDiagnosticDataType;
import com.digitalpetri.opcua.stack.core.types.structured.StatusResult;

@com.digitalpetri.opcua.sdk.server.util.UaVariableNode(typeName = "0:ProgramDiagnosticType")
public class ProgramDiagnosticNode extends BaseDataVariableNode implements ProgramDiagnosticType {

    public ProgramDiagnosticNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            VariableTypeNode variableTypeNode) {

        super(nodeManager, nodeId, variableTypeNode);
    }

    public ProgramDiagnosticNode(
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
        ProgramDiagnosticDataType value = new ProgramDiagnosticDataType(
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public NodeId getCreateSessionId() {
        Optional<NodeId> property = getProperty(ProgramDiagnosticType.CREATE_SESSION_ID);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getCreateSessionIdNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ProgramDiagnosticType.CREATE_SESSION_ID.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setCreateSessionId(NodeId value) {
        setProperty(ProgramDiagnosticType.CREATE_SESSION_ID, value);
    }

    @Override
    public String getCreateClientName() {
        Optional<String> property = getProperty(ProgramDiagnosticType.CREATE_CLIENT_NAME);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getCreateClientNameNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ProgramDiagnosticType.CREATE_CLIENT_NAME.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setCreateClientName(String value) {
        setProperty(ProgramDiagnosticType.CREATE_CLIENT_NAME, value);
    }

    @Override
    public DateTime getInvocationCreationTime() {
        Optional<DateTime> property = getProperty(ProgramDiagnosticType.INVOCATION_CREATION_TIME);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getInvocationCreationTimeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ProgramDiagnosticType.INVOCATION_CREATION_TIME.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setInvocationCreationTime(DateTime value) {
        setProperty(ProgramDiagnosticType.INVOCATION_CREATION_TIME, value);
    }

    @Override
    public DateTime getLastTransitionTime() {
        Optional<DateTime> property = getProperty(ProgramDiagnosticType.LAST_TRANSITION_TIME);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getLastTransitionTimeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ProgramDiagnosticType.LAST_TRANSITION_TIME.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setLastTransitionTime(DateTime value) {
        setProperty(ProgramDiagnosticType.LAST_TRANSITION_TIME, value);
    }

    @Override
    public String getLastMethodCall() {
        Optional<String> property = getProperty(ProgramDiagnosticType.LAST_METHOD_CALL);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getLastMethodCallNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ProgramDiagnosticType.LAST_METHOD_CALL.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setLastMethodCall(String value) {
        setProperty(ProgramDiagnosticType.LAST_METHOD_CALL, value);
    }

    @Override
    public NodeId getLastMethodSessionId() {
        Optional<NodeId> property = getProperty(ProgramDiagnosticType.LAST_METHOD_SESSION_ID);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getLastMethodSessionIdNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ProgramDiagnosticType.LAST_METHOD_SESSION_ID.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setLastMethodSessionId(NodeId value) {
        setProperty(ProgramDiagnosticType.LAST_METHOD_SESSION_ID, value);
    }

    @Override
    public Argument[] getLastMethodInputArguments() {
        Optional<Argument[]> property = getProperty(ProgramDiagnosticType.LAST_METHOD_INPUT_ARGUMENTS);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getLastMethodInputArgumentsNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ProgramDiagnosticType.LAST_METHOD_INPUT_ARGUMENTS.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setLastMethodInputArguments(Argument[] value) {
        setProperty(ProgramDiagnosticType.LAST_METHOD_INPUT_ARGUMENTS, value);
    }

    @Override
    public Argument[] getLastMethodOutputArguments() {
        Optional<Argument[]> property = getProperty(ProgramDiagnosticType.LAST_METHOD_OUTPUT_ARGUMENTS);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getLastMethodOutputArgumentsNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ProgramDiagnosticType.LAST_METHOD_OUTPUT_ARGUMENTS.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setLastMethodOutputArguments(Argument[] value) {
        setProperty(ProgramDiagnosticType.LAST_METHOD_OUTPUT_ARGUMENTS, value);
    }

    @Override
    public DateTime getLastMethodCallTime() {
        Optional<DateTime> property = getProperty(ProgramDiagnosticType.LAST_METHOD_CALL_TIME);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getLastMethodCallTimeNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ProgramDiagnosticType.LAST_METHOD_CALL_TIME.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setLastMethodCallTime(DateTime value) {
        setProperty(ProgramDiagnosticType.LAST_METHOD_CALL_TIME, value);
    }

    @Override
    public StatusResult getLastMethodReturnStatus() {
        Optional<StatusResult> property = getProperty(ProgramDiagnosticType.LAST_METHOD_RETURN_STATUS);

        return property.orElse(null);
    }

    @Override
    public PropertyNode getLastMethodReturnStatusNode() {
        Optional<VariableNode> propertyNode = getPropertyNode(ProgramDiagnosticType.LAST_METHOD_RETURN_STATUS.getBrowseName());

        return propertyNode.map(n -> (PropertyNode) n).orElse(null);
    }

    @Override
    public void setLastMethodReturnStatus(StatusResult value) {
        setProperty(ProgramDiagnosticType.LAST_METHOD_RETURN_STATUS, value);
    }

}
