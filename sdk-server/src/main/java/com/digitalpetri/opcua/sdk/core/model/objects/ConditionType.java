package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.ConditionVariableType;
import com.digitalpetri.opcua.sdk.core.model.variables.TwoStateVariableType;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;

public interface ConditionType extends BaseEventType {

    NodeId getConditionClassId();

    LocalizedText getConditionClassName();

    String getConditionName();

    NodeId getBranchId();

    Boolean getRetain();

    TwoStateVariableType getEnabledState();

    ConditionVariableType getQuality();

    ConditionVariableType getLastSeverity();

    ConditionVariableType getComment();

    String getClientUserId();

    void setConditionClassId(NodeId conditionClassId);

    void setConditionClassName(LocalizedText conditionClassName);

    void setConditionName(String conditionName);

    void setBranchId(NodeId branchId);

    void setRetain(Boolean retain);

    void setClientUserId(String clientUserId);

}
