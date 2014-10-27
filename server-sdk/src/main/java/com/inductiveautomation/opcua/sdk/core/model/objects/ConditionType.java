package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;

public interface ConditionType extends BaseEventType {

    NodeId getConditionClassId();

    LocalizedText getConditionClassName();

    String getConditionName();

    NodeId getBranchId();

    Boolean getRetain();

    String getClientUserId();

    LocalizedText getEnabledState();

    StatusCode getQuality();

    UShort getLastSeverity();

    LocalizedText getComment();

    void setConditionClassId(NodeId conditionClassId);

    void setConditionClassName(LocalizedText conditionClassName);

    void setConditionName(String conditionName);

    void setBranchId(NodeId branchId);

    void setRetain(Boolean retain);

    void setClientUserId(String clientUserId);

    void setEnabledState(LocalizedText enabledState);

    void setQuality(StatusCode quality);

    void setLastSeverity(UShort lastSeverity);

    void setComment(LocalizedText comment);

    void atomicSet(Runnable runnable);

}
