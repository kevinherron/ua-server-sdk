package com.inductiveautomation.opcua.sdk.server.nodes.generated.variables;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.variables.ArrayItemType;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
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

    public ArrayItemNode(UaNodeManager nodeManager,
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

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask,
                value, dataType, valueRank, arrayDimensions, accessLevel, userAccessLevel, minimumSamplingInterval, historizing);

    }

    @Override
    public Range getEURange() {
        Optional<Range> eURange = getProperty("EURange");

        return eURange.orElse(null);
    }

    @Override
    public EUInformation getEngineeringUnits() {
        Optional<EUInformation> engineeringUnits = getProperty("EngineeringUnits");

        return engineeringUnits.orElse(null);
    }

    @Override
    public LocalizedText getTitle() {
        Optional<LocalizedText> title = getProperty("Title");

        return title.orElse(null);
    }

    @Override
    public AxisScaleEnumeration getAxisScaleType() {
        Optional<AxisScaleEnumeration> axisScaleType = getProperty("AxisScaleType");

        return axisScaleType.orElse(null);
    }

    @Override
    public void setEURange(Range eURange) {
        getPropertyNode("EURange").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(eURange)));
        });
    }

    @Override
    public void setEngineeringUnits(EUInformation engineeringUnits) {
        getPropertyNode("EngineeringUnits").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(engineeringUnits)));
        });
    }

    @Override
    public void setTitle(LocalizedText title) {
        getPropertyNode("Title").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(title)));
        });
    }

    @Override
    public void setAxisScaleType(AxisScaleEnumeration axisScaleType) {
        getPropertyNode("AxisScaleType").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(axisScaleType)));
        });
    }

    @Override
    public void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
