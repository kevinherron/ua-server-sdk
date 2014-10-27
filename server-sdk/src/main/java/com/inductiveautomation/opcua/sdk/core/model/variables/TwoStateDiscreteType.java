package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface TwoStateDiscreteType extends DiscreteItemType {

    LocalizedText getFalseState();

    LocalizedText getTrueState();

    void setFalseState(LocalizedText falseState);

    void setTrueState(LocalizedText trueState);

    void atomicSet(Runnable runnable);

}
