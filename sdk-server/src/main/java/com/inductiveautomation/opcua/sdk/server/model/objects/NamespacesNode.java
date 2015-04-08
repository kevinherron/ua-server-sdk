package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.AddressSpaceFileType;
import com.inductiveautomation.opcua.sdk.core.model.objects.NamespaceMetadataType;
import com.inductiveautomation.opcua.sdk.core.model.objects.NamespacesType;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "NamespacesType")
public class NamespacesNode extends BaseObjectNode implements NamespacesType {

    public NamespacesNode(
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

    public NamespaceMetadataType getNamespaceIdentifier() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> namespaceIdentifier = getObjectComponent("NamespaceIdentifier");

        return namespaceIdentifier.map(node -> (NamespaceMetadataType) node).orElse(null);
    }

    public AddressSpaceFileType getAddressSpaceFile() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> addressSpaceFile = getObjectComponent("AddressSpaceFile");

        return addressSpaceFile.map(node -> (AddressSpaceFileType) node).orElse(null);
    }

}
