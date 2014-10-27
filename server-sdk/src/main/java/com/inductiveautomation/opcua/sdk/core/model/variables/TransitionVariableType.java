package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface TransitionVariableType extends BaseDataVariableType {

    Object getId();

    QualifiedName getName();

    UInteger getNumber();

    DateTime getTransitionTime();

    DateTime getEffectiveTransitionTime();

    void setId(Object id);

    void setName(QualifiedName name);

    void setNumber(UInteger number);

    void setTransitionTime(DateTime transitionTime);

    void setEffectiveTransitionTime(DateTime effectiveTransitionTime);

    void atomicSet(Runnable runnable);

}
