package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;

public interface TrustListType extends FileType {

    Boolean getIsHttpsTrustList();

    DateTime getLastUpdateTime();

    void setIsHttpsTrustList(Boolean isHttpsTrustList);

    void setLastUpdateTime(DateTime lastUpdateTime);

    void atomicSet(Runnable runnable);

}
