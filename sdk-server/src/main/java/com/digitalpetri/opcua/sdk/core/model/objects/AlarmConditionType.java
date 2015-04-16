package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.TwoStateVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface AlarmConditionType extends AcknowledgeableConditionType {

    TwoStateVariableType getEnabledState();

    TwoStateVariableType getActiveState();

    NodeId getInputNode();

    TwoStateVariableType getSuppressedState();

    ShelvedStateMachineType getShelvingState();

    Boolean getSuppressedOrShelved();

    Double getMaxTimeShelved();

    void setInputNode(NodeId inputNode);

    void setSuppressedOrShelved(Boolean suppressedOrShelved);

    void setMaxTimeShelved(Double maxTimeShelved);

}
