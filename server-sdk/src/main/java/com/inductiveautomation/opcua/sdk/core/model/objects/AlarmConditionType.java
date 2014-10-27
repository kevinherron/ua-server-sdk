package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface AlarmConditionType extends AcknowledgeableConditionType {

    NodeId getInputNode();

    Boolean getSuppressedOrShelved();

    Double getMaxTimeShelved();

    LocalizedText getEnabledState();

    LocalizedText getActiveState();

    LocalizedText getSuppressedState();

    ShelvedStateMachineType getShelvingState();

    void setInputNode(NodeId inputNode);

    void setSuppressedOrShelved(Boolean suppressedOrShelved);

    void setMaxTimeShelved(Double maxTimeShelved);

    void setEnabledState(LocalizedText enabledState);

    void setActiveState(LocalizedText activeState);

    void setSuppressedState(LocalizedText suppressedState);

    void setShelvingState(ShelvedStateMachineType shelvingState);

    void atomicSet(Runnable runnable);

}
