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
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.structured.Argument;
import com.digitalpetri.opcua.stack.core.types.structured.StatusResult;

public interface ProgramDiagnosticType extends BaseDataVariableType {

    @UaMandatory("CreateSessionId")
    NodeId getCreateSessionId();

    @UaMandatory("CreateClientName")
    String getCreateClientName();

    @UaMandatory("InvocationCreationTime")
    DateTime getInvocationCreationTime();

    @UaMandatory("LastTransitionTime")
    DateTime getLastTransitionTime();

    @UaMandatory("LastMethodCall")
    String getLastMethodCall();

    @UaMandatory("LastMethodSessionId")
    NodeId getLastMethodSessionId();

    @UaMandatory("LastMethodInputArguments")
    Argument[] getLastMethodInputArguments();

    @UaMandatory("LastMethodOutputArguments")
    Argument[] getLastMethodOutputArguments();

    @UaMandatory("LastMethodCallTime")
    DateTime getLastMethodCallTime();

    @UaMandatory("LastMethodReturnStatus")
    StatusResult getLastMethodReturnStatus();

    void setCreateSessionId(NodeId createSessionId);

    void setCreateClientName(String createClientName);

    void setInvocationCreationTime(DateTime invocationCreationTime);

    void setLastTransitionTime(DateTime lastTransitionTime);

    void setLastMethodCall(String lastMethodCall);

    void setLastMethodSessionId(NodeId lastMethodSessionId);

    void setLastMethodInputArguments(Argument[] lastMethodInputArguments);

    void setLastMethodOutputArguments(Argument[] lastMethodOutputArguments);

    void setLastMethodCallTime(DateTime lastMethodCallTime);

    void setLastMethodReturnStatus(StatusResult lastMethodReturnStatus);

}
