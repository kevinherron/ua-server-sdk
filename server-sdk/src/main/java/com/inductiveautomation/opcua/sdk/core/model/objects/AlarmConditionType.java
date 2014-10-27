package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.TwoStateVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface AlarmConditionType extends AcknowledgeableConditionType {

    NodeId getInputNode();

    Boolean getSuppressedOrShelved();

    Double getMaxTimeShelved();

    TwoStateVariableType getEnabledState();

    TwoStateVariableType getActiveState();

    TwoStateVariableType getSuppressedState();

    ShelvedStateMachineType getShelvingState();

    void setInputNode(NodeId inputNode);

    void setSuppressedOrShelved(Boolean suppressedOrShelved);

    void setMaxTimeShelved(Double maxTimeShelved);

    void atomicSet(Runnable runnable);

}
