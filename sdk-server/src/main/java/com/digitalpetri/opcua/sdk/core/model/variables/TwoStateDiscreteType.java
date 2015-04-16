package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;

public interface TwoStateDiscreteType extends DiscreteItemType {

    @UaMandatory("FalseState")
    LocalizedText getFalseState();

    @UaMandatory("TrueState")
    LocalizedText getTrueState();

    void setFalseState(LocalizedText falseState);

    void setTrueState(LocalizedText trueState);

}
