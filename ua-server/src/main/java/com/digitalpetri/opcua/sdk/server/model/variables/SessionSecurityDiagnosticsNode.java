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

import com.digitalpetri.opcua.sdk.core.model.variables.SessionSecurityDiagnosticsType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableTypeNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.structured.SessionSecurityDiagnosticsDataType;

@com.digitalpetri.opcua.sdk.server.util.UaVariableNode(typeName = "0:SessionSecurityDiagnosticsType")
public class SessionSecurityDiagnosticsNode extends BaseDataVariableNode implements SessionSecurityDiagnosticsType {

    public SessionSecurityDiagnosticsNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            VariableTypeNode variableTypeNode) {

        super(nodeManager, nodeId, variableTypeNode);
    }

    public SessionSecurityDiagnosticsNode(
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
        SessionSecurityDiagnosticsDataType value = new SessionSecurityDiagnosticsDataType(
                getSessionId(),
                getClientUserIdOfSession(),
                getClientUserIdHistory(),
                getAuthenticationMechanism(),
                getEncoding(),
                getTransportProtocol(),
                getSecurityMode(),
                getSecurityPolicyUri(),
                getClientCertificate()
        );

        return new DataValue(new Variant(value));
    }

    @Override
    public NodeId getSessionId() {
        Optional<VariableNode> component = getVariableComponent("SessionId");

        return component.map(node -> (NodeId) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getSessionIdNode() {
        Optional<VariableNode> component = getVariableComponent("SessionId");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setSessionId(NodeId value) {
        getVariableComponent("SessionId")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public String getClientUserIdOfSession() {
        Optional<VariableNode> component = getVariableComponent("ClientUserIdOfSession");

        return component.map(node -> (String) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getClientUserIdOfSessionNode() {
        Optional<VariableNode> component = getVariableComponent("ClientUserIdOfSession");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setClientUserIdOfSession(String value) {
        getVariableComponent("ClientUserIdOfSession")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public String[] getClientUserIdHistory() {
        Optional<VariableNode> component = getVariableComponent("ClientUserIdHistory");

        return component.map(node -> (String[]) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getClientUserIdHistoryNode() {
        Optional<VariableNode> component = getVariableComponent("ClientUserIdHistory");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setClientUserIdHistory(String[] value) {
        getVariableComponent("ClientUserIdHistory")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public String getAuthenticationMechanism() {
        Optional<VariableNode> component = getVariableComponent("AuthenticationMechanism");

        return component.map(node -> (String) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getAuthenticationMechanismNode() {
        Optional<VariableNode> component = getVariableComponent("AuthenticationMechanism");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setAuthenticationMechanism(String value) {
        getVariableComponent("AuthenticationMechanism")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public String getEncoding() {
        Optional<VariableNode> component = getVariableComponent("Encoding");

        return component.map(node -> (String) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getEncodingNode() {
        Optional<VariableNode> component = getVariableComponent("Encoding");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setEncoding(String value) {
        getVariableComponent("Encoding")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public String getTransportProtocol() {
        Optional<VariableNode> component = getVariableComponent("TransportProtocol");

        return component.map(node -> (String) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getTransportProtocolNode() {
        Optional<VariableNode> component = getVariableComponent("TransportProtocol");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setTransportProtocol(String value) {
        getVariableComponent("TransportProtocol")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public MessageSecurityMode getSecurityMode() {
        Optional<VariableNode> component = getVariableComponent("SecurityMode");

        return component.map(node -> (MessageSecurityMode) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getSecurityModeNode() {
        Optional<VariableNode> component = getVariableComponent("SecurityMode");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setSecurityMode(MessageSecurityMode value) {
        getVariableComponent("SecurityMode")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public String getSecurityPolicyUri() {
        Optional<VariableNode> component = getVariableComponent("SecurityPolicyUri");

        return component.map(node -> (String) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getSecurityPolicyUriNode() {
        Optional<VariableNode> component = getVariableComponent("SecurityPolicyUri");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setSecurityPolicyUri(String value) {
        getVariableComponent("SecurityPolicyUri")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public ByteString getClientCertificate() {
        Optional<VariableNode> component = getVariableComponent("ClientCertificate");

        return component.map(node -> (ByteString) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public BaseDataVariableNode getClientCertificateNode() {
        Optional<VariableNode> component = getVariableComponent("ClientCertificate");

        return component.map(node -> (BaseDataVariableNode) node).orElse(null);
    }

    @Override
    public void setClientCertificate(ByteString value) {
        getVariableComponent("ClientCertificate")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

}
