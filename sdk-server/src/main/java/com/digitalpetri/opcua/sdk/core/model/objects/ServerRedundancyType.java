package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.enumerated.RedundancySupport;

public interface ServerRedundancyType extends BaseObjectType {

    RedundancySupport getRedundancySupport();

    void setRedundancySupport(RedundancySupport redundancySupport);

}
