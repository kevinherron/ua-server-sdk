package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface ExclusiveDeviationAlarmType extends ExclusiveLimitAlarmType {

    NodeId getSetpointNode();

    void setSetpointNode(NodeId setpointNode);

}
