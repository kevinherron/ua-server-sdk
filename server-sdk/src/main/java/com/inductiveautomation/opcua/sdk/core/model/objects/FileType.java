package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.ULong;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UShort;

public interface FileType extends BaseObjectType {

    ULong getSize();

    Boolean getWriteable();

    Boolean getUserWriteable();

    UShort getOpenCount();

    void setSize(ULong size);

    void setWriteable(Boolean writeable);

    void setUserWriteable(Boolean userWriteable);

    void setOpenCount(UShort openCount);

    void atomicSet(Runnable runnable);

}
