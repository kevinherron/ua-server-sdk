package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.FileType;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.ULong;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;


@UaObjectType(name = "FileType")
public class FileNode extends BaseObjectNode implements FileType {

    public FileNode(
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

    public ULong getSize() {
        Optional<ULong> size = getProperty("Size");

        return size.orElse(null);
    }

    public Boolean getWriteable() {
        Optional<Boolean> writeable = getProperty("Writeable");

        return writeable.orElse(null);
    }

    public Boolean getUserWriteable() {
        Optional<Boolean> userWriteable = getProperty("UserWriteable");

        return userWriteable.orElse(null);
    }

    public UShort getOpenCount() {
        Optional<UShort> openCount = getProperty("OpenCount");

        return openCount.orElse(null);
    }

    public synchronized void setSize(ULong size) {
        getPropertyNode("Size").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(size)));
        });
    }

    public synchronized void setWriteable(Boolean writeable) {
        getPropertyNode("Writeable").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(writeable)));
        });
    }

    public synchronized void setUserWriteable(Boolean userWriteable) {
        getPropertyNode("UserWriteable").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(userWriteable)));
        });
    }

    public synchronized void setOpenCount(UShort openCount) {
        getPropertyNode("OpenCount").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(openCount)));
        });
    }

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
