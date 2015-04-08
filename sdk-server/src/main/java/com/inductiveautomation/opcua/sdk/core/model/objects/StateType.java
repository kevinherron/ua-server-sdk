package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface StateType extends BaseObjectType {

    UInteger getStateNumber();

    void setStateNumber(UInteger stateNumber);

}
