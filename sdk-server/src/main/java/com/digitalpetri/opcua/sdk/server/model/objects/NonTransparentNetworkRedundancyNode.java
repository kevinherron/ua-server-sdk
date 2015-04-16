package com.digitalpetri.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.objects.NonTransparentNetworkRedundancyType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.NetworkGroupDataType;


@UaObjectType(name = "NonTransparentNetworkRedundancyType")
public class NonTransparentNetworkRedundancyNode extends NonTransparentRedundancyNode implements NonTransparentNetworkRedundancyType {

    public NonTransparentNetworkRedundancyNode(
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

    public NetworkGroupDataType[] getServerNetworkGroups() {
        Optional<NetworkGroupDataType[]> serverNetworkGroups = getProperty("ServerNetworkGroups");

        return serverNetworkGroups.orElse(null);
    }

    public synchronized void setServerNetworkGroups(NetworkGroupDataType[] serverNetworkGroups) {
        getPropertyNode("ServerNetworkGroups").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(serverNetworkGroups)));
        });
    }
}
