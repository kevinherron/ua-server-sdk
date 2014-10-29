package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface TwoStateVariableType<T> extends StateVariableType {

    Boolean getId();

    DateTime getTransitionTime();

    DateTime getEffectiveTransitionTime();

    LocalizedText getTrueState();

    LocalizedText getFalseState();

    void setId(Boolean id);

    void setTransitionTime(DateTime transitionTime);

    void setEffectiveTransitionTime(DateTime effectiveTransitionTime);

    void setTrueState(LocalizedText trueState);

    void setFalseState(LocalizedText falseState);

}
