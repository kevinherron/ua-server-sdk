package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.NamespacesType;
import com.inductiveautomation.opcua.sdk.core.model.objects.ServerCapabilitiesType;
import com.inductiveautomation.opcua.sdk.core.model.objects.ServerDiagnosticsType;
import com.inductiveautomation.opcua.sdk.core.model.objects.ServerRedundancyType;
import com.inductiveautomation.opcua.sdk.core.model.objects.ServerType;
import com.inductiveautomation.opcua.sdk.core.model.objects.VendorServerInfoType;
import com.inductiveautomation.opcua.sdk.core.model.variables.ServerStatusType;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "ServerType")
public class ServerNode extends BaseObjectNode implements ServerType {

    public ServerNode(
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

    public String[] getServerArray() {
        Optional<String[]> serverArray = getProperty("ServerArray");

        return serverArray.orElse(null);
    }

    public String[] getNamespaceArray() {
        Optional<String[]> namespaceArray = getProperty("NamespaceArray");

        return namespaceArray.orElse(null);
    }

    public ServerStatusType getServerStatus() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.VariableNode> serverStatus = getVariableComponent("ServerStatus");

        return serverStatus.map(node -> (ServerStatusType) node).orElse(null);
    }

    public UByte getServiceLevel() {
        Optional<UByte> serviceLevel = getProperty("ServiceLevel");

        return serviceLevel.orElse(null);
    }

    public Boolean getAuditing() {
        Optional<Boolean> auditing = getProperty("Auditing");

        return auditing.orElse(null);
    }

    public ServerCapabilitiesType getServerCapabilities() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> serverCapabilities = getObjectComponent("ServerCapabilities");

        return serverCapabilities.map(node -> (ServerCapabilitiesType) node).orElse(null);
    }

    public ServerDiagnosticsType getServerDiagnostics() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> serverDiagnostics = getObjectComponent("ServerDiagnostics");

        return serverDiagnostics.map(node -> (ServerDiagnosticsType) node).orElse(null);
    }

    public VendorServerInfoType getVendorServerInfo() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> vendorServerInfo = getObjectComponent("VendorServerInfo");

        return vendorServerInfo.map(node -> (VendorServerInfoType) node).orElse(null);
    }

    public ServerRedundancyType getServerRedundancy() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> serverRedundancy = getObjectComponent("ServerRedundancy");

        return serverRedundancy.map(node -> (ServerRedundancyType) node).orElse(null);
    }

    public NamespacesType getNamespaces() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> namespaces = getObjectComponent("Namespaces");

        return namespaces.map(node -> (NamespacesType) node).orElse(null);
    }

    public synchronized void setServerArray(String[] serverArray) {
        getPropertyNode("ServerArray").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(serverArray)));
        });
    }

    public synchronized void setNamespaceArray(String[] namespaceArray) {
        getPropertyNode("NamespaceArray").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(namespaceArray)));
        });
    }

    public synchronized void setServerStatus(ServerStatusType serverStatus) {
        getVariableComponent("ServerStatus").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(serverStatus)));
        });
    }

    public synchronized void setServiceLevel(UByte serviceLevel) {
        getPropertyNode("ServiceLevel").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(serviceLevel)));
        });
    }

    public synchronized void setAuditing(Boolean auditing) {
        getPropertyNode("Auditing").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(auditing)));
        });
    }
}
