package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface NonExclusiveDeviationAlarmType extends NonExclusiveLimitAlarmType {

    NodeId getSetpointNode();

    void setSetpointNode(NodeId setpointNode);

}
