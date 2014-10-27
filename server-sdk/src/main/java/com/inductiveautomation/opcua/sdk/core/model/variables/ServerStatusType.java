package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.ServerState;
import com.inductiveautomation.opcua.stack.core.types.structured.BuildInfo;

public interface ServerStatusType extends BaseDataVariableType {

    DateTime getStartTime();

    DateTime getCurrentTime();

    ServerState getState();

    BuildInfo getBuildInfo();

    UInteger getSecondsTillShutdown();

    LocalizedText getShutdownReason();

    void setStartTime(DateTime startTime);

    void setCurrentTime(DateTime currentTime);

    void setState(ServerState state);

    void setBuildInfo(BuildInfo buildInfo);

    void setSecondsTillShutdown(UInteger secondsTillShutdown);

    void setShutdownReason(LocalizedText shutdownReason);

    void atomicSet(Runnable runnable);

}
