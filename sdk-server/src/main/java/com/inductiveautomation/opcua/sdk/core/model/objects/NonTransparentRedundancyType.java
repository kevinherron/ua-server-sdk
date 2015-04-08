package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface NonTransparentRedundancyType extends ServerRedundancyType {

    String[] getServerUriArray();

    void setServerUriArray(String[] serverUriArray);

}
