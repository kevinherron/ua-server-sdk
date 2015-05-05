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
import com.digitalpetri.opcua.sdk.core.model.variables.SessionDiagnosticsVariableType;
import com.digitalpetri.opcua.sdk.core.model.variables.SessionSecurityDiagnosticsType;
import com.digitalpetri.opcua.sdk.core.model.variables.SubscriptionDiagnosticsArrayType;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "SessionDiagnosticsObjectType")
public class SessionDiagnosticsObjectNode extends BaseObjectNode implements SessionDiagnosticsObjectType {

    public SessionDiagnosticsObjectNode(
            UaNamespace namespace,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(namespace, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    public SessionDiagnosticsVariableType getSessionDiagnostics() {
        Optional<VariableNode> sessionDiagnostics = getVariableComponent("SessionDiagnostics");

        return sessionDiagnostics.map(node -> (SessionDiagnosticsVariableType) node).orElse(null);
    }

    public SessionSecurityDiagnosticsType getSessionSecurityDiagnostics() {
        Optional<VariableNode> sessionSecurityDiagnostics = getVariableComponent("SessionSecurityDiagnostics");

        return sessionSecurityDiagnostics.map(node -> (SessionSecurityDiagnosticsType) node).orElse(null);
    }

    public SubscriptionDiagnosticsArrayType getSubscriptionDiagnosticsArray() {
        Optional<VariableNode> subscriptionDiagnosticsArray = getVariableComponent("SubscriptionDiagnosticsArray");

        return subscriptionDiagnosticsArray.map(node -> (SubscriptionDiagnosticsArrayType) node).orElse(null);
    }

    public synchronized void setSessionDiagnostics(SessionDiagnosticsVariableType sessionDiagnostics) {
        getVariableComponent("SessionDiagnostics").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sessionDiagnostics)));
        });
    }

    public synchronized void setSessionSecurityDiagnostics(SessionSecurityDiagnosticsType sessionSecurityDiagnostics) {
        getVariableComponent("SessionSecurityDiagnostics").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sessionSecurityDiagnostics)));
        });
    }

    public synchronized void setSubscriptionDiagnosticsArray(SubscriptionDiagnosticsArrayType subscriptionDiagnosticsArray) {
        getVariableComponent("SubscriptionDiagnosticsArray").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(subscriptionDiagnosticsArray)));
        });
    }
}
