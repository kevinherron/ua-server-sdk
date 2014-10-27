package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface HistoryServerCapabilitiesType extends BaseObjectType {

    Boolean getAccessHistoryDataCapability();

    Boolean getAccessHistoryEventsCapability();

    UInteger getMaxReturnDataValues();

    UInteger getMaxReturnEventValues();

    Boolean getInsertDataCapability();

    Boolean getReplaceDataCapability();

    Boolean getUpdateDataCapability();

    Boolean getDeleteRawCapability();

    Boolean getDeleteAtTimeCapability();

    Boolean getInsertEventCapability();

    Boolean getReplaceEventCapability();

    Boolean getUpdateEventCapability();

    Boolean getDeleteEventCapability();

    Boolean getInsertAnnotationCapability();

    FolderType getAggregateFunctions();

    void setAccessHistoryDataCapability(Boolean accessHistoryDataCapability);

    void setAccessHistoryEventsCapability(Boolean accessHistoryEventsCapability);

    void setMaxReturnDataValues(UInteger maxReturnDataValues);

    void setMaxReturnEventValues(UInteger maxReturnEventValues);

    void setInsertDataCapability(Boolean insertDataCapability);

    void setReplaceDataCapability(Boolean replaceDataCapability);

    void setUpdateDataCapability(Boolean updateDataCapability);

    void setDeleteRawCapability(Boolean deleteRawCapability);

    void setDeleteAtTimeCapability(Boolean deleteAtTimeCapability);

    void setInsertEventCapability(Boolean insertEventCapability);

    void setReplaceEventCapability(Boolean replaceEventCapability);

    void setUpdateEventCapability(Boolean updateEventCapability);

    void setDeleteEventCapability(Boolean deleteEventCapability);

    void setInsertAnnotationCapability(Boolean insertAnnotationCapability);

    void atomicSet(Runnable runnable);

}
