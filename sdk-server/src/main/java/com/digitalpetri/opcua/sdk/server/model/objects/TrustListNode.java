package com.digitalpetri.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.objects.TrustListType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "TrustListType")
public class TrustListNode extends FileNode implements TrustListType {

    public TrustListNode(
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
}
