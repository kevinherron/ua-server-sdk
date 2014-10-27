package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface FiniteTransitionVariableType extends TransitionVariableType {

    NodeId getId();

    void setId(NodeId id);

    void atomicSet(Runnable runnable);

}
