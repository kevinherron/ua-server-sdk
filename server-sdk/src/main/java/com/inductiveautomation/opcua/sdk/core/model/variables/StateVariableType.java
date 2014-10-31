package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.sdk.core.model.UaOptional;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface StateVariableType extends BaseDataVariableType {

    @UaMandatory("Id")
    Object getId();

    @UaOptional("Name")
    QualifiedName getName();

    @UaOptional("Number")
    UInteger getNumber();

    @UaOptional("EffectiveDisplayName")
    LocalizedText getEffectiveDisplayName();

    void setId(Object id);

    void setName(QualifiedName name);

    void setNumber(UInteger number);

    void setEffectiveDisplayName(LocalizedText effectiveDisplayName);

    void atomicAction(Runnable runnable);

}
