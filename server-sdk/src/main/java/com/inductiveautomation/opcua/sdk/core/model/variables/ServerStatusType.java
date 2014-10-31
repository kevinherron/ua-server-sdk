package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.ServerState;
import com.inductiveautomation.opcua.stack.core.types.structured.BuildInfo;

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

    void atomicAction(Runnable runnable);

}
