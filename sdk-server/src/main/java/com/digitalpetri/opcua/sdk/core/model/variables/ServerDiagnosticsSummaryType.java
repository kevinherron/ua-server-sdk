/*
 * digitalpetri OPC-UA SDK
 *
 * Copyright (C) 2015 Kevin Herron
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
