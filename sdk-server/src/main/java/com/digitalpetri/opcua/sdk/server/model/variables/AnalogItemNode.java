package com.digitalpetri.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.sdk.core.model.UaOptional;
import com.digitalpetri.opcua.sdk.core.model.variables.AnalogItemType;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.UaVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.EUInformation;
import com.digitalpetri.opcua.stack.core.types.structured.Range;

@UaVariableType(name = "AnalogItemType")
public class AnalogItemNode extends DataItemNode implements AnalogItemType {

    public AnalogItemNode(UaNamespace namespace,
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
    @UaOptional("InstrumentRange")
    public Range getInstrumentRange() {
        Optional<Range> instrumentRange = getProperty("InstrumentRange");

        return instrumentRange.orElse(null);
    }

    @Override
    @UaMandatory("EURange")
    public Range getEURange() {
        Optional<Range> eURange = getProperty("EURange");

        return eURange.orElse(null);
    }

    @Override
    @UaOptional("EngineeringUnits")
    public EUInformation getEngineeringUnits() {
        Optional<EUInformation> engineeringUnits = getProperty("EngineeringUnits");

        return engineeringUnits.orElse(null);
    }

    @Override
    public synchronized void setInstrumentRange(Range instrumentRange) {
        getPropertyNode("InstrumentRange").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(instrumentRange)));
        });
    }

    @Override
    public synchronized void setEURange(Range eURange) {
        getPropertyNode("EURange").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(eURange)));
        });
    }

    @Override
    public synchronized void setEngineeringUnits(EUInformation engineeringUnits) {
        getPropertyNode("EngineeringUnits").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(engineeringUnits)));
        });
    }

}
