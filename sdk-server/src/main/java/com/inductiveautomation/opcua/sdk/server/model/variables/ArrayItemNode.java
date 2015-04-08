package com.inductiveautomation.opcua.sdk.server.model.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.sdk.core.model.UaOptional;
import com.inductiveautomation.opcua.sdk.core.model.variables.ArrayItemType;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.util.UaVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.AxisScaleEnumeration;
import com.inductiveautomation.opcua.stack.core.types.structured.EUInformation;
import com.inductiveautomation.opcua.stack.core.types.structured.Range;

@UaVariableType(name = "ArrayItemType")
public class ArrayItemNode extends DataItemNode implements ArrayItemType {

    public ArrayItemNode(UaNamespace namespace,
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
    @UaMandatory("EngineeringUnits")
    public EUInformation getEngineeringUnits() {
        Optional<EUInformation> engineeringUnits = getProperty("EngineeringUnits");

        return engineeringUnits.orElse(null);
    }

    @Override
    @UaMandatory("Title")
    public LocalizedText getTitle() {
        Optional<LocalizedText> title = getProperty("Title");

        return title.orElse(null);
    }

    @Override
    @UaMandatory("AxisScaleType")
    public AxisScaleEnumeration getAxisScaleType() {
        Optional<Integer> axisScaleType = getProperty("AxisScaleType");

        return axisScaleType.map(AxisScaleEnumeration::from).orElse(null);
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

    @Override
    public synchronized void setTitle(LocalizedText title) {
        getPropertyNode("Title").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(title)));
        });
    }

    @Override
    public synchronized void setAxisScaleType(AxisScaleEnumeration axisScaleType) {
        getPropertyNode("AxisScaleType").ifPresent(n -> {
            Integer value = axisScaleType.getValue();

            n.setValue(new DataValue(new Variant(value)));
        });
    }

}
