package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.TrustListType;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "TrustListType")
public class TrustListNode extends FileNode implements TrustListType {

    public TrustListNode(
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

    public Boolean getIsHttpsTrustList() {
        Optional<Boolean> isHttpsTrustList = getProperty("IsHttpsTrustList");

        return isHttpsTrustList.orElse(null);
    }

    public DateTime getLastUpdateTime() {
        Optional<DateTime> lastUpdateTime = getProperty("LastUpdateTime");

        return lastUpdateTime.orElse(null);
    }

    public synchronized void setIsHttpsTrustList(Boolean isHttpsTrustList) {
        getPropertyNode("IsHttpsTrustList").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(isHttpsTrustList)));
        });
    }

    public synchronized void setLastUpdateTime(DateTime lastUpdateTime) {
        getPropertyNode("LastUpdateTime").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(lastUpdateTime)));
        });
    }

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
