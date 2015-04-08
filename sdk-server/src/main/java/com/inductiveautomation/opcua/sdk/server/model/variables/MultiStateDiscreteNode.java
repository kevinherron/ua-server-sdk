package com.inductiveautomation.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.sdk.core.model.variables.MultiStateDiscreteType;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

@UaVariableType(name = "MultiStateDiscreteType")
public class MultiStateDiscreteNode extends DiscreteItemNode implements MultiStateDiscreteType {

    public MultiStateDiscreteNode(UaNamespace namespace,
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
    @UaMandatory("EnumStrings")
    public LocalizedText[] getEnumStrings() {
        Optional<LocalizedText[]> enumStrings = getProperty("EnumStrings");

        return enumStrings.orElse(null);
    }

    @Override
    public synchronized void setEnumStrings(LocalizedText[] enumStrings) {
        getPropertyNode("EnumStrings").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(enumStrings)));
        });
    }

}
