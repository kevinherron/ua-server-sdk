package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.structured.Argument;
import com.inductiveautomation.opcua.stack.core.types.structured.StatusResult;

public interface ProgramDiagnosticType extends BaseDataVariableType {

    @UaMandatory("CreateSessionId")
    NodeId getCreateSessionId();

    @UaMandatory("CreateClientName")
    String getCreateClientName();

    @UaMandatory("InvocationCreationTime")
    DateTime getInvocationCreationTime();

    @UaMandatory("LastTransitionTime")
    DateTime getLastTransitionTime();

    @UaMandatory("LastMethodCall")
    String getLastMethodCall();

    @UaMandatory("LastMethodSessionId")
    NodeId getLastMethodSessionId();

    @UaMandatory("LastMethodInputArguments")
    Argument[] getLastMethodInputArguments();

    @UaMandatory("LastMethodOutputArguments")
    Argument[] getLastMethodOutputArguments();

    @UaMandatory("LastMethodCallTime")
    DateTime getLastMethodCallTime();

    @UaMandatory("LastMethodReturnStatus")
    StatusResult getLastMethodReturnStatus();

    void setCreateSessionId(NodeId createSessionId);

    void setCreateClientName(String createClientName);

    void setInvocationCreationTime(DateTime invocationCreationTime);

    void setLastTransitionTime(DateTime lastTransitionTime);

    void setLastMethodCall(String lastMethodCall);

    void setLastMethodSessionId(NodeId lastMethodSessionId);

    void setLastMethodInputArguments(Argument[] lastMethodInputArguments);

    void setLastMethodOutputArguments(Argument[] lastMethodOutputArguments);

    void setLastMethodCallTime(DateTime lastMethodCallTime);

    void setLastMethodReturnStatus(StatusResult lastMethodReturnStatus);

    void atomicAction(Runnable runnable);

}
