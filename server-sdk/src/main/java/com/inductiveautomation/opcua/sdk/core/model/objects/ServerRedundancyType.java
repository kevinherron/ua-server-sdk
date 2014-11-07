package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.enumerated.RedundancySupport;

public interface ServerRedundancyType extends BaseObjectType {

    RedundancySupport getRedundancySupport();

    void setRedundancySupport(RedundancySupport redundancySupport);

}
