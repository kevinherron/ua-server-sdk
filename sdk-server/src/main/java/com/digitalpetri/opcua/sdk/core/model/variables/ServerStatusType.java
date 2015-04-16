package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.ServerState;
import com.digitalpetri.opcua.stack.core.types.structured.BuildInfo;

public interface ServerStatusType extends BaseDataVariableType {

    @UaMandatory("StartTime")
    DateTime getStartTime();

    @UaMandatory("CurrentTime")
    DateTime getCurrentTime();

    @UaMandatory("State")
    ServerState getState();

    @UaMandatory("BuildInfo")
    BuildInfo getBuildInfo();

    @UaMandatory("SecondsTillShutdown")
    UInteger getSecondsTillShutdown();

    @UaMandatory("ShutdownReason")
    LocalizedText getShutdownReason();

    void setStartTime(DateTime startTime);

    void setCurrentTime(DateTime currentTime);

    void setState(ServerState state);

    void setBuildInfo(BuildInfo buildInfo);

    void setSecondsTillShutdown(UInteger secondsTillShutdown);

    void setShutdownReason(LocalizedText shutdownReason);

}
