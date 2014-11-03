package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.TransparentRedundancyType;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.RedundantServerDataType;


@UaObjectType(name = "TransparentRedundancyType")
public class TransparentRedundancyNode extends ServerRedundancyNode implements TransparentRedundancyType {

    public TransparentRedundancyNode(
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

    public String getCurrentServerId() {
        Optional<String> currentServerId = getProperty("CurrentServerId");

        return currentServerId.orElse(null);
    }

    public RedundantServerDataType[] getRedundantServerArray() {
        Optional<RedundantServerDataType[]> redundantServerArray = getProperty("RedundantServerArray");

        return redundantServerArray.orElse(null);
    }

    public synchronized void setCurrentServerId(String currentServerId) {
        getPropertyNode("CurrentServerId").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(currentServerId)));
        });
    }

    public synchronized void setRedundantServerArray(RedundantServerDataType[] redundantServerArray) {
        getPropertyNode("RedundantServerArray").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(redundantServerArray)));
        });
    }

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
