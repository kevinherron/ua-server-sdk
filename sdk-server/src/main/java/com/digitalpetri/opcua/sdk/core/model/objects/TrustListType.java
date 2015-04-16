package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;

public interface TrustListType extends FileType {

    Boolean getIsHttpsTrustList();

    DateTime getLastUpdateTime();

    void setIsHttpsTrustList(Boolean isHttpsTrustList);

    void setLastUpdateTime(DateTime lastUpdateTime);

}
