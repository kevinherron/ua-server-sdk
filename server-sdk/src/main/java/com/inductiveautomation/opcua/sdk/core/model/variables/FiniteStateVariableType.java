package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface FiniteStateVariableType extends StateVariableType {

    NodeId getId();

    void setId(NodeId id);

    void atomicSet(Runnable runnable);

}
