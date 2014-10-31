package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface FiniteStateVariableType extends StateVariableType {

    @UaMandatory("Id")
    NodeId getId();

    void setId(NodeId id);

    void atomicAction(Runnable runnable);

}
