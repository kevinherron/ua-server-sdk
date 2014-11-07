package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface TransitionType extends BaseObjectType {

    UInteger getTransitionNumber();

    void setTransitionNumber(UInteger transitionNumber);

}
