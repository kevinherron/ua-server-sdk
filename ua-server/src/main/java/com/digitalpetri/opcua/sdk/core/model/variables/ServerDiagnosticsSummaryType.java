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

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;


public interface ServerDiagnosticsSummaryType extends BaseDataVariableType {


    UInteger getServerViewCount();

    BaseDataVariableType getServerViewCountNode();

    void setServerViewCount(UInteger value);

    UInteger getCurrentSessionCount();

    BaseDataVariableType getCurrentSessionCountNode();

    void setCurrentSessionCount(UInteger value);

    UInteger getCumulatedSessionCount();

    BaseDataVariableType getCumulatedSessionCountNode();

    void setCumulatedSessionCount(UInteger value);

    UInteger getSecurityRejectedSessionCount();

    BaseDataVariableType getSecurityRejectedSessionCountNode();

    void setSecurityRejectedSessionCount(UInteger value);

    UInteger getRejectedSessionCount();

    BaseDataVariableType getRejectedSessionCountNode();

    void setRejectedSessionCount(UInteger value);

    UInteger getSessionTimeoutCount();

    BaseDataVariableType getSessionTimeoutCountNode();

    void setSessionTimeoutCount(UInteger value);

    UInteger getSessionAbortCount();

    BaseDataVariableType getSessionAbortCountNode();

    void setSessionAbortCount(UInteger value);

    UInteger getPublishingIntervalCount();

    BaseDataVariableType getPublishingIntervalCountNode();

    void setPublishingIntervalCount(UInteger value);

    UInteger getCurrentSubscriptionCount();

    BaseDataVariableType getCurrentSubscriptionCountNode();

    void setCurrentSubscriptionCount(UInteger value);

    UInteger getCumulatedSubscriptionCount();

    BaseDataVariableType getCumulatedSubscriptionCountNode();

    void setCumulatedSubscriptionCount(UInteger value);

    UInteger getSecurityRejectedRequestsCount();

    BaseDataVariableType getSecurityRejectedRequestsCountNode();

    void setSecurityRejectedRequestsCount(UInteger value);

    UInteger getRejectedRequestsCount();

    BaseDataVariableType getRejectedRequestsCountNode();

    void setRejectedRequestsCount(UInteger value);
}
