package com.digitalpetri.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.sdk.core.model.variables.ConditionVariableType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaVariableType(name = "ConditionVariableType")
public class ConditionVariableNode extends BaseDataVariableNode implements ConditionVariableType {

    public ConditionVariableNode(UaNamespace namespace,
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

    @Override
    @UaMandatory("SourceTimestamp")
    public DateTime getSourceTimestamp() {
        Optional<DateTime> sourceTimestamp = getProperty("SourceTimestamp");

        return sourceTimestamp.orElse(null);
    }

    @Override
    public synchronized void setSourceTimestamp(DateTime sourceTimestamp) {
        getPropertyNode("SourceTimestamp").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(sourceTimestamp)));
        });
    }

}
