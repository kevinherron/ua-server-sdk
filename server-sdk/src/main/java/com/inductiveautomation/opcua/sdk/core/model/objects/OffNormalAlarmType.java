package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface OffNormalAlarmType extends DiscreteAlarmType {

    NodeId getNormalState();

    void setNormalState(NodeId normalState);

}
