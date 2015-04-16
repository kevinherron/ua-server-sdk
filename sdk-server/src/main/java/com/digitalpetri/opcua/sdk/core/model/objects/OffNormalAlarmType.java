package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface OffNormalAlarmType extends DiscreteAlarmType {

    NodeId getNormalState();

    void setNormalState(NodeId normalState);

}
