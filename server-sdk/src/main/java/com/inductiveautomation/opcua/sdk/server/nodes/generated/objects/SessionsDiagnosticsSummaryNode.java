package com.inductiveautomation.opcua.sdk.server.nodes.generated.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.SessionDiagnosticsObjectType;
import com.inductiveautomation.opcua.sdk.core.model.objects.SessionsDiagnosticsSummaryType;
import com.inductiveautomation.opcua.sdk.core.model.variables.SessionDiagnosticsArrayType;
import com.inductiveautomation.opcua.sdk.core.model.variables.SessionSecurityDiagnosticsArrayType;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "SessionsDiagnosticsSummaryType")
public class SessionsDiagnosticsSummaryNode extends BaseObjectNode implements SessionsDiagnosticsSummaryType {

    public SessionsDiagnosticsSummaryNode(
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

    public SessionDiagnosticsArrayType getSessionDiagnosticsArray() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> sessionDiagnosticsArray = getVariableComponent("SessionDiagnosticsArray");

        return sessionDiagnosticsArray.map(node -> (SessionDiagnosticsArrayType) node).orElse(null);
    }

    public SessionSecurityDiagnosticsArrayType getSessionSecurityDiagnosticsArray() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> sessionSecurityDiagnosticsArray = getVariableComponent("SessionSecurityDiagnosticsArray");

        return sessionSecurityDiagnosticsArray.map(node -> (SessionSecurityDiagnosticsArrayType) node).orElse(null);
    }

    public SessionDiagnosticsObjectType getSessionPlaceholder() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> sessionPlaceholder = getObjectComponent("SessionPlaceholder");

        return sessionPlaceholder.map(node -> (SessionDiagnosticsObjectType) node).orElse(null);
    }

    public synchronized void setSessionDiagnosticsArray(SessionDiagnosticsArrayType sessionDiagnosticsArray) {
        getVariableComponent("SessionDiagnosticsArray").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sessionDiagnosticsArray)));
        });
    }

    public synchronized void setSessionSecurityDiagnosticsArray(SessionSecurityDiagnosticsArrayType sessionSecurityDiagnosticsArray) {
        getVariableComponent("SessionSecurityDiagnosticsArray").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sessionSecurityDiagnosticsArray)));
        });
    }

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
