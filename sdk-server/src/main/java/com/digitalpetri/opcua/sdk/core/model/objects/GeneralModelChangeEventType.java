package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.structured.ModelChangeStructureDataType;

public interface GeneralModelChangeEventType extends BaseModelChangeEventType {

    ModelChangeStructureDataType[] getChanges();

    void setChanges(ModelChangeStructureDataType[] changes);

}
