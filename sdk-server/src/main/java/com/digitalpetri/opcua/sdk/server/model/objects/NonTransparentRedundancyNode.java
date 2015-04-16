package com.digitalpetri.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.objects.NonTransparentRedundancyType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaObjectType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;


@UaObjectType(name = "NonTransparentRedundancyType")
public class NonTransparentRedundancyNode extends ServerRedundancyNode implements NonTransparentRedundancyType {

    public NonTransparentRedundancyNode(
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

    public String[] getServerUriArray() {
        Optional<String[]> serverUriArray = getProperty("ServerUriArray");

        return serverUriArray.orElse(null);
    }

    public synchronized void setServerUriArray(String[] serverUriArray) {
        getPropertyNode("ServerUriArray").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(serverUriArray)));
        });
    }
}
