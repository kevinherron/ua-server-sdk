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
import com.digitalpetri.opcua.sdk.core.model.objects.SessionsDiagnosticsSummaryType;
import com.digitalpetri.opcua.sdk.core.model.variables.SamplingIntervalDiagnosticsArrayType;
import com.digitalpetri.opcua.sdk.core.model.variables.ServerDiagnosticsSummaryType;
import com.digitalpetri.opcua.sdk.core.model.variables.SubscriptionDiagnosticsArrayType;
import com.digitalpetri.opcua.sdk.core.nodes.ObjectNode;
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


@UaObjectType(name = "ServerDiagnosticsType")
public class ServerDiagnosticsNode extends BaseObjectNode implements ServerDiagnosticsType {

    public ServerDiagnosticsNode(
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

    public ServerDiagnosticsSummaryType getServerDiagnosticsSummary() {
        Optional<VariableNode> serverDiagnosticsSummary = getVariableComponent("ServerDiagnosticsSummary");

        return serverDiagnosticsSummary.map(node -> (ServerDiagnosticsSummaryType) node).orElse(null);
    }

    public SamplingIntervalDiagnosticsArrayType getSamplingIntervalDiagnosticsArray() {
        Optional<VariableNode> samplingIntervalDiagnosticsArray = getVariableComponent("SamplingIntervalDiagnosticsArray");

        return samplingIntervalDiagnosticsArray.map(node -> (SamplingIntervalDiagnosticsArrayType) node).orElse(null);
    }

    public SubscriptionDiagnosticsArrayType getSubscriptionDiagnosticsArray() {
        Optional<VariableNode> subscriptionDiagnosticsArray = getVariableComponent("SubscriptionDiagnosticsArray");

        return subscriptionDiagnosticsArray.map(node -> (SubscriptionDiagnosticsArrayType) node).orElse(null);
    }

    public SessionsDiagnosticsSummaryType getSessionsDiagnosticsSummary() {
        Optional<ObjectNode> sessionsDiagnosticsSummary = getObjectComponent("SessionsDiagnosticsSummary");

        return sessionsDiagnosticsSummary.map(node -> (SessionsDiagnosticsSummaryType) node).orElse(null);
    }

    public Boolean getEnabledFlag() {
        Optional<Boolean> enabledFlag = getProperty("EnabledFlag");

        return enabledFlag.orElse(null);
    }

    public synchronized void setServerDiagnosticsSummary(ServerDiagnosticsSummaryType serverDiagnosticsSummary) {
        getVariableComponent("ServerDiagnosticsSummary").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(serverDiagnosticsSummary)));
        });
    }

    public synchronized void setSamplingIntervalDiagnosticsArray(SamplingIntervalDiagnosticsArrayType samplingIntervalDiagnosticsArray) {
        getVariableComponent("SamplingIntervalDiagnosticsArray").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(samplingIntervalDiagnosticsArray)));
        });
    }

    public synchronized void setSubscriptionDiagnosticsArray(SubscriptionDiagnosticsArrayType subscriptionDiagnosticsArray) {
        getVariableComponent("SubscriptionDiagnosticsArray").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(subscriptionDiagnosticsArray)));
        });
    }

    public synchronized void setEnabledFlag(Boolean enabledFlag) {
        getPropertyNode("EnabledFlag").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(enabledFlag)));
        });
    }
}
