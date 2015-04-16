package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.structured.SemanticChangeStructureDataType;

public interface SemanticChangeEventType extends BaseModelChangeEventType {

    SemanticChangeStructureDataType[] getChanges();

    void setChanges(SemanticChangeStructureDataType[] changes);

}
