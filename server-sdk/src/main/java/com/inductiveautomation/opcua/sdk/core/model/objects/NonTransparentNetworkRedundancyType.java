package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.structured.NetworkGroupDataType;

public interface NonTransparentNetworkRedundancyType extends NonTransparentRedundancyType {

    NetworkGroupDataType[] getServerNetworkGroups();

    void setServerNetworkGroups(NetworkGroupDataType[] serverNetworkGroups);

}
