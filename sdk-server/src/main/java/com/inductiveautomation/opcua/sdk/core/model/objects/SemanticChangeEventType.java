package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.structured.SemanticChangeStructureDataType;

public interface SemanticChangeEventType extends BaseModelChangeEventType {

    SemanticChangeStructureDataType[] getChanges();

    void setChanges(SemanticChangeStructureDataType[] changes);

}
