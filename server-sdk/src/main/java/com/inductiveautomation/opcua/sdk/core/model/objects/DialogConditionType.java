package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.TwoStateVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface DialogConditionType extends ConditionType {

    LocalizedText getPrompt();

    LocalizedText[] getResponseOptionSet();

    Integer getDefaultResponse();

    Integer getOkResponse();

    Integer getCancelResponse();

    Integer getLastResponse();

    TwoStateVariableType getEnabledState();

    TwoStateVariableType getDialogState();

    void setPrompt(LocalizedText prompt);

    void setResponseOptionSet(LocalizedText[] responseOptionSet);

    void setDefaultResponse(Integer defaultResponse);

    void setOkResponse(Integer okResponse);

    void setCancelResponse(Integer cancelResponse);

    void setLastResponse(Integer lastResponse);

    void atomicSet(Runnable runnable);

}
