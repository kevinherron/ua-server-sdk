package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.enumerated.AxisScaleEnumeration;
import com.inductiveautomation.opcua.stack.core.types.structured.EUInformation;
import com.inductiveautomation.opcua.stack.core.types.structured.Range;

public interface ArrayItemType extends DataItemType {

    Range getEURange();

    EUInformation getEngineeringUnits();

    LocalizedText getTitle();

    AxisScaleEnumeration getAxisScaleType();

    void setEURange(Range eURange);

    void setEngineeringUnits(EUInformation engineeringUnits);

    void setTitle(LocalizedText title);

    void setAxisScaleType(AxisScaleEnumeration axisScaleType);

    void atomicSet(Runnable runnable);

}
