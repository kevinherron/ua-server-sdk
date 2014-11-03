package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.DirectoryType;
import com.inductiveautomation.opcua.sdk.core.model.objects.FolderType;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "DirectoryType")
public class DirectoryNode extends FolderNode implements DirectoryType {

    public DirectoryNode(
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

    public FolderType getApplications() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> applications = getObjectComponent("Applications");

        return applications.map(node -> (FolderType) node).orElse(null);
    }

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
