package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.SessionDiagnosticsObjectType;
import com.inductiveautomation.opcua.sdk.core.model.variables.SessionDiagnosticsVariableType;
import com.inductiveautomation.opcua.sdk.core.model.variables.SessionSecurityDiagnosticsType;
import com.inductiveautomation.opcua.sdk.core.model.variables.SubscriptionDiagnosticsArrayType;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "SessionDiagnosticsObjectType")
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

    public SessionDiagnosticsVariableType getSessionDiagnostics() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> sessionDiagnostics = getVariableComponent("SessionDiagnostics");

        return sessionDiagnostics.map(node -> (SessionDiagnosticsVariableType) node).orElse(null);
    }

    public SessionSecurityDiagnosticsType getSessionSecurityDiagnostics() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> sessionSecurityDiagnostics = getVariableComponent("SessionSecurityDiagnostics");

        return sessionSecurityDiagnostics.map(node -> (SessionSecurityDiagnosticsType) node).orElse(null);
    }

    public SubscriptionDiagnosticsArrayType getSubscriptionDiagnosticsArray() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> subscriptionDiagnosticsArray = getVariableComponent("SubscriptionDiagnosticsArray");

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

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
