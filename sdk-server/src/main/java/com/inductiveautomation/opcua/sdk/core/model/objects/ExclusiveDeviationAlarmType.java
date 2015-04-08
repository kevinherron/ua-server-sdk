package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface ExclusiveDeviationAlarmType extends ExclusiveLimitAlarmType {

    NodeId getSetpointNode();

    void setSetpointNode(NodeId setpointNode);

}
