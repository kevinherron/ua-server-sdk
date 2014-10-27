package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface ServerDiagnosticsSummaryType extends BaseDataVariableType {

    UInteger getServerViewCount();

    UInteger getCurrentSessionCount();

    UInteger getCumulatedSessionCount();

    UInteger getSecurityRejectedSessionCount();

    UInteger getRejectedSessionCount();

    UInteger getSessionTimeoutCount();

    UInteger getSessionAbortCount();

    UInteger getPublishingIntervalCount();

    UInteger getCurrentSubscriptionCount();

    UInteger getCumulatedSubscriptionCount();

    UInteger getSecurityRejectedRequestsCount();

    UInteger getRejectedRequestsCount();

    void setServerViewCount(UInteger serverViewCount);

    void setCurrentSessionCount(UInteger currentSessionCount);

    void setCumulatedSessionCount(UInteger cumulatedSessionCount);

    void setSecurityRejectedSessionCount(UInteger securityRejectedSessionCount);

    void setRejectedSessionCount(UInteger rejectedSessionCount);

    void setSessionTimeoutCount(UInteger sessionTimeoutCount);

    void setSessionAbortCount(UInteger sessionAbortCount);

    void setPublishingIntervalCount(UInteger publishingIntervalCount);

    void setCurrentSubscriptionCount(UInteger currentSubscriptionCount);

    void setCumulatedSubscriptionCount(UInteger cumulatedSubscriptionCount);

    void setSecurityRejectedRequestsCount(UInteger securityRejectedRequestsCount);

    void setRejectedRequestsCount(UInteger rejectedRequestsCount);

    void atomicSet(Runnable runnable);

}
