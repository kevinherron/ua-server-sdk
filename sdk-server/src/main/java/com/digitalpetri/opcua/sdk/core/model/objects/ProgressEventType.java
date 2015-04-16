package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;

public interface ProgressEventType extends BaseEventType {

    Object getContext();

    UShort getProgress();

    void setContext(Object context);

    void setProgress(UShort progress);

}
