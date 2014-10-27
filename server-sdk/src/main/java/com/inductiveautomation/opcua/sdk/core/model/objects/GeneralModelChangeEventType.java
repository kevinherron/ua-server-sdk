package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.structured.ModelChangeStructureDataType;

public interface GeneralModelChangeEventType extends BaseModelChangeEventType {

    ModelChangeStructureDataType[] getChanges();

    void setChanges(ModelChangeStructureDataType[] changes);

    void atomicSet(Runnable runnable);

}
