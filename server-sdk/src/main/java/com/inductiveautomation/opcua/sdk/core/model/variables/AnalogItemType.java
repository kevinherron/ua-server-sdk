package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.structured.Range;

public interface AnalogItemType extends DataItemType {

    Range getEURange();

    void setEURange(Range eURange);

    void atomicSet(Runnable runnable);

}
