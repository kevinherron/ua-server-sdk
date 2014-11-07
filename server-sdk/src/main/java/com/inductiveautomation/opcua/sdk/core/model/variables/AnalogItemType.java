package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.sdk.core.model.UaOptional;
import com.inductiveautomation.opcua.stack.core.types.structured.EUInformation;
import com.inductiveautomation.opcua.stack.core.types.structured.Range;

public interface AnalogItemType extends DataItemType {

    @UaOptional("InstrumentRange")
    Range getInstrumentRange();

    @UaMandatory("EURange")
    Range getEURange();

    @UaOptional("EngineeringUnits")
    EUInformation getEngineeringUnits();

    void setInstrumentRange(Range instrumentRange);

    void setEURange(Range eURange);

    void setEngineeringUnits(EUInformation engineeringUnits);

}
