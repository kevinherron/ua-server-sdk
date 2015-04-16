package com.digitalpetri.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.sdk.core.model.variables.MultiStateValueDiscreteType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.EnumValueType;

@UaVariableType(name = "MultiStateValueDiscreteType")
public class MultiStateValueDiscreteNode extends DiscreteItemNode implements MultiStateValueDiscreteType {

    public MultiStateValueDiscreteNode(UaNamespace namespace,
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
    @UaMandatory("EnumValues")
    public EnumValueType[] getEnumValues() {
        Optional<EnumValueType[]> enumValues = getProperty("EnumValues");

        return enumValues.orElse(null);
    }

    @Override
    @UaMandatory("ValueAsText")
    public LocalizedText getValueAsText() {
        Optional<LocalizedText> valueAsText = getProperty("ValueAsText");

        return valueAsText.orElse(null);
    }

    @Override
    public synchronized void setEnumValues(EnumValueType[] enumValues) {
        getPropertyNode("EnumValues").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(enumValues)));
        });
    }

    @Override
    public synchronized void setValueAsText(LocalizedText valueAsText) {
        getPropertyNode("ValueAsText").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(valueAsText)));
        });
    }

}
