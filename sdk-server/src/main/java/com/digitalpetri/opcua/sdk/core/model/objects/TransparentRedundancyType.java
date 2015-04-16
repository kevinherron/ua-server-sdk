package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.structured.RedundantServerDataType;

public interface TransparentRedundancyType extends ServerRedundancyType {

    String getCurrentServerId();

    RedundantServerDataType[] getRedundantServerArray();

    void setCurrentServerId(String currentServerId);

    void setRedundantServerArray(RedundantServerDataType[] redundantServerArray);

}
