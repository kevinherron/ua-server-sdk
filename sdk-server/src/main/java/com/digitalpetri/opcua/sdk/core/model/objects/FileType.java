package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.ULong;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UShort;

public interface FileType extends BaseObjectType {

    ULong getSize();

    Boolean getWriteable();

    Boolean getUserWriteable();

    UShort getOpenCount();

    void setSize(ULong size);

    void setWriteable(Boolean writeable);

    void setUserWriteable(Boolean userWriteable);

    void setOpenCount(UShort openCount);

}
