package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;

public interface BuildInfoType extends BaseDataVariableType {

    @UaMandatory("ProductUri")
    String getProductUri();

    @UaMandatory("ManufacturerName")
    String getManufacturerName();

    @UaMandatory("ProductName")
    String getProductName();

    @UaMandatory("SoftwareVersion")
    String getSoftwareVersion();

    @UaMandatory("BuildNumber")
    String getBuildNumber();

    @UaMandatory("BuildDate")
    DateTime getBuildDate();

    void setProductUri(String productUri);

    void setManufacturerName(String manufacturerName);

    void setProductName(String productName);

    void setSoftwareVersion(String softwareVersion);

    void setBuildNumber(String buildNumber);

    void setBuildDate(DateTime buildDate);

}
