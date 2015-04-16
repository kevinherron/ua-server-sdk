package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.PerformUpdateType;

public interface AuditHistoryValueUpdateEventType extends AuditHistoryUpdateEventType {

    NodeId getUpdatedNode();

    PerformUpdateType getPerformInsertReplace();

    DataValue[] getNewValues();

    DataValue[] getOldValues();

    void setUpdatedNode(NodeId updatedNode);

    void setPerformInsertReplace(PerformUpdateType performInsertReplace);

    void setNewValues(DataValue[] newValues);

    void setOldValues(DataValue[] oldValues);

}
