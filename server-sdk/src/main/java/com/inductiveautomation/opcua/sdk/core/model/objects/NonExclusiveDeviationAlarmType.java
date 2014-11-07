package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface NonExclusiveDeviationAlarmType extends NonExclusiveLimitAlarmType {

    NodeId getSetpointNode();

    void setSetpointNode(NodeId setpointNode);

}
