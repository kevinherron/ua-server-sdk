package com.digitalpetri.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.UaOptional;
import com.digitalpetri.opcua.sdk.core.model.variables.DataItemType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaVariableType(name = "DataItemType")
public class DataItemNode extends BaseDataVariableNode implements DataItemType {

    public DataItemNode(UaNamespace namespace,
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
    @UaOptional("Definition")
    public String getDefinition() {
        Optional<String> definition = getProperty("Definition");

        return definition.orElse(null);
    }

    @Override
    @UaOptional("ValuePrecision")
    public Double getValuePrecision() {
        Optional<Double> valuePrecision = getProperty("ValuePrecision");

        return valuePrecision.orElse(null);
    }

    @Override
    public synchronized void setDefinition(String definition) {
        getPropertyNode("Definition").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(definition)));
        });
    }

    @Override
    public synchronized void setValuePrecision(Double valuePrecision) {
        getPropertyNode("ValuePrecision").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(valuePrecision)));
        });
    }

}
