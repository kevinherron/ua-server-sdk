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
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.ServerState;
import com.digitalpetri.opcua.stack.core.types.structured.BuildInfo;

public interface ServerStatusType extends BaseDataVariableType {

    @UaMandatory("StartTime")
    DateTime getStartTime();

    @UaMandatory("CurrentTime")
    DateTime getCurrentTime();

    @UaMandatory("State")
    ServerState getState();

    @UaMandatory("BuildInfo")
    BuildInfo getBuildInfo();

    @UaMandatory("SecondsTillShutdown")
    UInteger getSecondsTillShutdown();

    @UaMandatory("ShutdownReason")
    LocalizedText getShutdownReason();

    void setStartTime(DateTime startTime);

    void setCurrentTime(DateTime currentTime);

    void setState(ServerState state);

    void setBuildInfo(BuildInfo buildInfo);

    void setSecondsTillShutdown(UInteger secondsTillShutdown);

    void setShutdownReason(LocalizedText shutdownReason);

}
