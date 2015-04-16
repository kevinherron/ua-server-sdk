package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.structured.NetworkGroupDataType;

public interface NonTransparentNetworkRedundancyType extends NonTransparentRedundancyType {

    NetworkGroupDataType[] getServerNetworkGroups();

    void setServerNetworkGroups(NetworkGroupDataType[] serverNetworkGroups);

}
