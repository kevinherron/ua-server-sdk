package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.structured.Argument;
import com.inductiveautomation.opcua.stack.core.types.structured.StatusResult;

public interface ProgramDiagnosticType extends BaseDataVariableType {

    NodeId getCreateSessionId();

    String getCreateClientName();

    DateTime getInvocationCreationTime();

    DateTime getLastTransitionTime();

    String getLastMethodCall();

    NodeId getLastMethodSessionId();

    Argument[] getLastMethodInputArguments();

    Argument[] getLastMethodOutputArguments();

    DateTime getLastMethodCallTime();

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

    void atomicSet(Runnable runnable);

}
