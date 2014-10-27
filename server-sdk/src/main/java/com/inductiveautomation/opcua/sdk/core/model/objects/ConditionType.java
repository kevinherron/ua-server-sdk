package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.ConditionVariableType;
import com.inductiveautomation.opcua.sdk.core.model.variables.TwoStateVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;

public interface ConditionType extends BaseEventType {

    NodeId getConditionClassId();

    LocalizedText getConditionClassName();

    String getConditionName();

    NodeId getBranchId();

    Boolean getRetain();

    String getClientUserId();

    TwoStateVariableType getEnabledState();

    ConditionVariableType getQuality();

    ConditionVariableType getLastSeverity();

    ConditionVariableType getComment();

    void setConditionClassId(NodeId conditionClassId);

    void setConditionClassName(LocalizedText conditionClassName);

    void setConditionName(String conditionName);

    void setBranchId(NodeId branchId);

    void setRetain(Boolean retain);

    void setClientUserId(String clientUserId);

    void atomicSet(Runnable runnable);

}
