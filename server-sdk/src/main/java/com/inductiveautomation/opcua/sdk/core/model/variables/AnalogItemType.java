package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.structured.EUInformation;
import com.inductiveautomation.opcua.stack.core.types.structured.Range;

public interface AnalogItemType extends DataItemType {

    Range getInstrumentRange();

    Range getEURange();

    EUInformation getEngineeringUnits();

    void setInstrumentRange(Range instrumentRange);

    void setEURange(Range eURange);

    void setEngineeringUnits(EUInformation engineeringUnits);

    void atomicSet(Runnable runnable);

}
