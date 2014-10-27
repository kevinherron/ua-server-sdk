package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface StateVariableType extends BaseDataVariableType {

    Object getId();

    QualifiedName getName();

    UInteger getNumber();

    LocalizedText getEffectiveDisplayName();

    void setId(Object id);

    void setName(QualifiedName name);

    void setNumber(UInteger number);

    void setEffectiveDisplayName(LocalizedText effectiveDisplayName);

    void atomicSet(Runnable runnable);

}
