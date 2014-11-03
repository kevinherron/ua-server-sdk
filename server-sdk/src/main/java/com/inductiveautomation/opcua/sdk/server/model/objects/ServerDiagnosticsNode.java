package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.ServerDiagnosticsType;
import com.inductiveautomation.opcua.sdk.core.model.objects.SessionsDiagnosticsSummaryType;
import com.inductiveautomation.opcua.sdk.core.model.variables.SamplingIntervalDiagnosticsArrayType;
import com.inductiveautomation.opcua.sdk.core.model.variables.ServerDiagnosticsSummaryType;
import com.inductiveautomation.opcua.sdk.core.model.variables.SubscriptionDiagnosticsArrayType;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "ServerDiagnosticsType")
public class ServerDiagnosticsNode extends BaseObjectNode implements ServerDiagnosticsType {

    public ServerDiagnosticsNode(
            UaNamespace nodeManager,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    public ServerDiagnosticsSummaryType getServerDiagnosticsSummary() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> serverDiagnosticsSummary = getVariableComponent("ServerDiagnosticsSummary");

        return serverDiagnosticsSummary.map(node -> (ServerDiagnosticsSummaryType) node).orElse(null);
    }

    public SamplingIntervalDiagnosticsArrayType getSamplingIntervalDiagnosticsArray() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> samplingIntervalDiagnosticsArray = getVariableComponent("SamplingIntervalDiagnosticsArray");

        return samplingIntervalDiagnosticsArray.map(node -> (SamplingIntervalDiagnosticsArrayType) node).orElse(null);
    }

    public SubscriptionDiagnosticsArrayType getSubscriptionDiagnosticsArray() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> subscriptionDiagnosticsArray = getVariableComponent("SubscriptionDiagnosticsArray");

        return subscriptionDiagnosticsArray.map(node -> (SubscriptionDiagnosticsArrayType) node).orElse(null);
    }

    public SessionsDiagnosticsSummaryType getSessionsDiagnosticsSummary() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> sessionsDiagnosticsSummary = getObjectComponent("SessionsDiagnosticsSummary");

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

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
