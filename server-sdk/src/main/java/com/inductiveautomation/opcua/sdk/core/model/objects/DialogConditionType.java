package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.sdk.core.model.variables.TwoStateVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;

public interface DialogConditionType extends ConditionType {

    TwoStateVariableType getEnabledState();

    TwoStateVariableType getDialogState();

    LocalizedText getPrompt();

    LocalizedText[] getResponseOptionSet();

    Integer getDefaultResponse();

    Integer getOkResponse();

    Integer getCancelResponse();

    Integer getLastResponse();

    void setPrompt(LocalizedText prompt);

    void setResponseOptionSet(LocalizedText[] responseOptionSet);

    void setDefaultResponse(Integer defaultResponse);

    void setOkResponse(Integer okResponse);

    void setCancelResponse(Integer cancelResponse);

    void setLastResponse(Integer lastResponse);

}
