package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.sdk.core.model.UaOptional;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface TwoStateVariableType extends StateVariableType {

    @UaMandatory("Id")
    Boolean getId();

    @UaOptional("TransitionTime")
    DateTime getTransitionTime();

    @UaOptional("EffectiveTransitionTime")
    DateTime getEffectiveTransitionTime();

    @UaOptional("TrueState")
    LocalizedText getTrueState();

    @UaOptional("FalseState")
    LocalizedText getFalseState();

    void setId(Boolean id);

    void setTransitionTime(DateTime transitionTime);

    void setEffectiveTransitionTime(DateTime effectiveTransitionTime);

    void setTrueState(LocalizedText trueState);

    void setFalseState(LocalizedText falseState);

}
