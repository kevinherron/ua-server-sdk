package com.digitalpetri.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.variables.BaseVariableType;
import com.digitalpetri.opcua.sdk.server.model.UaVariableNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaVariableType(name = "BaseVariableType")
public class BaseVariableNode extends UaVariableNode implements BaseVariableType {

    public BaseVariableNode(UaNamespace namespace,
                            NodeId nodeId,
                            QualifiedName browseName,
                            LocalizedText displayName,
                            Optional<LocalizedText> description,
                            Optional<UInteger> writeMask,
                            Optional<UInteger> userWriteMask,
                            DataValue value,
                            NodeId dataType,
                            Integer valueRank,
                            Optional<UInteger[]> arrayDimensions,
                            UByte accessLevel,
                            UByte userAccessLevel,
                            Optional<Double> minimumSamplingInterval,
                            boolean historizing) {

        super(namespace, nodeId, browseName, displayName, description, writeMask, userWriteMask,
                value, dataType, valueRank, arrayDimensions, accessLevel, userAccessLevel, minimumSamplingInterval, historizing);

    }


}
