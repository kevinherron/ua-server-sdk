package com.digitalpetri.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.sdk.core.model.variables.TwoStateDiscreteType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaVariableType(name = "TwoStateDiscreteType")
public class TwoStateDiscreteNode extends DiscreteItemNode implements TwoStateDiscreteType {

    public TwoStateDiscreteNode(UaNamespace namespace,
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
    @UaMandatory("FalseState")
    public LocalizedText getFalseState() {
        Optional<LocalizedText> falseState = getProperty("FalseState");

        return falseState.orElse(null);
    }

    @Override
    @UaMandatory("TrueState")
    public LocalizedText getTrueState() {
        Optional<LocalizedText> trueState = getProperty("TrueState");

        return trueState.orElse(null);
    }

    @Override
    public synchronized void setFalseState(LocalizedText falseState) {
        getPropertyNode("FalseState").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(falseState)));
        });
    }

    @Override
    public synchronized void setTrueState(LocalizedText trueState) {
        getPropertyNode("TrueState").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(trueState)));
        });
    }

}
