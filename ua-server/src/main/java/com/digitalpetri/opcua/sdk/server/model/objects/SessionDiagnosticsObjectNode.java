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

import com.digitalpetri.opcua.sdk.core.model.objects.SessionDiagnosticsObjectType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.model.variables.SessionDiagnosticsVariableNode;
import com.digitalpetri.opcua.sdk.server.model.variables.SessionSecurityDiagnosticsNode;
import com.digitalpetri.opcua.sdk.server.model.variables.SubscriptionDiagnosticsArrayNode;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.SessionDiagnosticsDataType;
import com.digitalpetri.opcua.stack.core.types.structured.SessionSecurityDiagnosticsDataType;
import com.digitalpetri.opcua.stack.core.types.structured.SubscriptionDiagnosticsDataType;

@com.digitalpetri.opcua.sdk.server.util.UaObjectNode(typeName = "0:SessionDiagnosticsObjectType")
public class SessionDiagnosticsObjectNode extends BaseObjectNode implements SessionDiagnosticsObjectType {

    public SessionDiagnosticsObjectNode(
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
    public SessionDiagnosticsDataType getSessionDiagnostics() {
        Optional<VariableNode> component = getVariableComponent("SessionDiagnostics");

        return component.map(node -> (SessionDiagnosticsDataType) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public SessionDiagnosticsVariableNode getSessionDiagnosticsNode() {
        Optional<VariableNode> component = getVariableComponent("SessionDiagnostics");

        return component.map(node -> (SessionDiagnosticsVariableNode) node).orElse(null);
    }

    @Override
    public void setSessionDiagnostics(SessionDiagnosticsDataType value) {
        getVariableComponent("SessionDiagnostics")
                .ifPresent(n -> n.setValue(new DataValue(new Variant(value))));
    }

    @Override
    public SessionSecurityDiagnosticsDataType getSessionSecurityDiagnostics() {
        Optional<VariableNode> component = getVariableComponent("SessionSecurityDiagnostics");

        return component.map(node -> (SessionSecurityDiagnosticsDataType) node.getValue().getValue().getValue()).orElse(null);
    }

    @Override
    public SessionSecurityDiagnosticsNode getSessionSecurityDiagnosticsNode() {
        Optional<VariableNode> component = getVariableComponent("SessionSecurityDiagnostics");

        return component.map(node -> (SessionSecurityDiagnosticsNode) node).orElse(null);
    }

    @Override
    public void setSessionSecurityDiagnostics(SessionSecurityDiagnosticsDataType value) {
        getVariableComponent("SessionSecurityDiagnostics")
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
