package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface StateType extends BaseObjectType {

    UInteger getStateNumber();

    void setStateNumber(UInteger stateNumber);

}
