package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.sdk.core.model.UaOptional;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.enumerated.AxisScaleEnumeration;
import com.inductiveautomation.opcua.stack.core.types.structured.EUInformation;
import com.inductiveautomation.opcua.stack.core.types.structured.Range;

public interface ArrayItemType extends DataItemType {

    @UaOptional("InstrumentRange")
    Range getInstrumentRange();

    @UaMandatory("EURange")
    Range getEURange();

    @UaMandatory("EngineeringUnits")
    EUInformation getEngineeringUnits();

    @UaMandatory("Title")
    LocalizedText getTitle();

    @UaMandatory("AxisScaleType")
    AxisScaleEnumeration getAxisScaleType();

    void setInstrumentRange(Range instrumentRange);

    void setEURange(Range eURange);

    void setEngineeringUnits(EUInformation engineeringUnits);

    void setTitle(LocalizedText title);

    void setAxisScaleType(AxisScaleEnumeration axisScaleType);

}
