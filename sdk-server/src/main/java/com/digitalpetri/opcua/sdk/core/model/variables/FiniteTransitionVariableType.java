package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface FiniteTransitionVariableType extends TransitionVariableType {

    @UaMandatory("Id")
    NodeId getId();

    void setId(NodeId id);

}
