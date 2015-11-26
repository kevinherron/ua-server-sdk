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

import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.ServerState;
import com.digitalpetri.opcua.stack.core.types.structured.BuildInfo;


public interface ServerStatusType extends BaseDataVariableType {


    DateTime getStartTime();

    BaseDataVariableType getStartTimeNode();

    void setStartTime(DateTime value);

    DateTime getCurrentTime();

    BaseDataVariableType getCurrentTimeNode();

    void setCurrentTime(DateTime value);

    ServerState getState();

    BaseDataVariableType getStateNode();

    void setState(ServerState value);

    BuildInfo getBuildInfo();

    BuildInfoType getBuildInfoNode();

    void setBuildInfo(BuildInfo value);

    UInteger getSecondsTillShutdown();

    BaseDataVariableType getSecondsTillShutdownNode();

    void setSecondsTillShutdown(UInteger value);

    LocalizedText getShutdownReason();

    BaseDataVariableType getShutdownReasonNode();

    void setShutdownReason(LocalizedText value);
}
