package com.digitalpetri.opcua.sdk.core.model.variables;

import com.digitalpetri.opcua.sdk.core.model.UaMandatory;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface ServerDiagnosticsSummaryType extends BaseDataVariableType {

    @UaMandatory("ServerViewCount")
    UInteger getServerViewCount();

    @UaMandatory("CurrentSessionCount")
    UInteger getCurrentSessionCount();

    @UaMandatory("CumulatedSessionCount")
    UInteger getCumulatedSessionCount();

    @UaMandatory("SecurityRejectedSessionCount")
    UInteger getSecurityRejectedSessionCount();

    @UaMandatory("RejectedSessionCount")
    UInteger getRejectedSessionCount();

    @UaMandatory("SessionTimeoutCount")
    UInteger getSessionTimeoutCount();

    @UaMandatory("SessionAbortCount")
    UInteger getSessionAbortCount();

    @UaMandatory("PublishingIntervalCount")
    UInteger getPublishingIntervalCount();

    @UaMandatory("CurrentSubscriptionCount")
    UInteger getCurrentSubscriptionCount();

    @UaMandatory("CumulatedSubscriptionCount")
    UInteger getCumulatedSubscriptionCount();

    @UaMandatory("SecurityRejectedRequestsCount")
    UInteger getSecurityRejectedRequestsCount();

    @UaMandatory("RejectedRequestsCount")
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

}
