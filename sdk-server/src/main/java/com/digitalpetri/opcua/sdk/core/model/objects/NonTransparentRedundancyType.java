package com.digitalpetri.opcua.sdk.core.model.objects;

public interface NonTransparentRedundancyType extends ServerRedundancyType {

    String[] getServerUriArray();

    void setServerUriArray(String[] serverUriArray);

}
