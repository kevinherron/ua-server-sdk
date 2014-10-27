package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;

public interface BuildInfoType extends BaseDataVariableType {

    String getProductUri();

    String getManufacturerName();

    String getProductName();

    String getSoftwareVersion();

    String getBuildNumber();

    DateTime getBuildDate();

    void setProductUri(String productUri);

    void setManufacturerName(String manufacturerName);

    void setProductName(String productName);

    void setSoftwareVersion(String softwareVersion);

    void setBuildNumber(String buildNumber);

    void setBuildDate(DateTime buildDate);

    void atomicSet(Runnable runnable);

}
