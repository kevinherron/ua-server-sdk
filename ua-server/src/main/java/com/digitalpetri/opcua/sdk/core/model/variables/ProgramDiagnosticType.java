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

import com.digitalpetri.opcua.sdk.server.model.Property;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.structured.Argument;
import com.digitalpetri.opcua.stack.core.types.structured.StatusResult;


public interface ProgramDiagnosticType extends BaseDataVariableType {

    Property<NodeId> CREATE_SESSION_ID = new Property.BasicProperty<>(
            QualifiedName.parse("0:CreateSessionId"),
            NodeId.parse("ns=0;i=17"),
            -1,
            NodeId.class
    );

    Property<String> CREATE_CLIENT_NAME = new Property.BasicProperty<>(
            QualifiedName.parse("0:CreateClientName"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    Property<DateTime> INVOCATION_CREATION_TIME = new Property.BasicProperty<>(
            QualifiedName.parse("0:InvocationCreationTime"),
            NodeId.parse("ns=0;i=294"),
            -1,
            DateTime.class
    );

    Property<DateTime> LAST_TRANSITION_TIME = new Property.BasicProperty<>(
            QualifiedName.parse("0:LastTransitionTime"),
            NodeId.parse("ns=0;i=294"),
            -1,
            DateTime.class
    );

    Property<String> LAST_METHOD_CALL = new Property.BasicProperty<>(
            QualifiedName.parse("0:LastMethodCall"),
            NodeId.parse("ns=0;i=12"),
            -1,
            String.class
    );

    Property<NodeId> LAST_METHOD_SESSION_ID = new Property.BasicProperty<>(
            QualifiedName.parse("0:LastMethodSessionId"),
            NodeId.parse("ns=0;i=17"),
            -1,
            NodeId.class
    );

    Property<Argument[]> LAST_METHOD_INPUT_ARGUMENTS = new Property.BasicProperty<>(
            QualifiedName.parse("0:LastMethodInputArguments"),
            NodeId.parse("ns=0;i=296"),
            1,
            Argument[].class
    );

    Property<Argument[]> LAST_METHOD_OUTPUT_ARGUMENTS = new Property.BasicProperty<>(
            QualifiedName.parse("0:LastMethodOutputArguments"),
            NodeId.parse("ns=0;i=296"),
            1,
            Argument[].class
    );

    Property<DateTime> LAST_METHOD_CALL_TIME = new Property.BasicProperty<>(
            QualifiedName.parse("0:LastMethodCallTime"),
            NodeId.parse("ns=0;i=294"),
            -1,
            DateTime.class
    );

    Property<StatusResult> LAST_METHOD_RETURN_STATUS = new Property.BasicProperty<>(
            QualifiedName.parse("0:LastMethodReturnStatus"),
            NodeId.parse("ns=0;i=299"),
            -1,
            StatusResult.class
    );


    NodeId getCreateSessionId();

    PropertyType getCreateSessionIdNode();

    void setCreateSessionId(NodeId value);

    String getCreateClientName();

    PropertyType getCreateClientNameNode();

    void setCreateClientName(String value);

    DateTime getInvocationCreationTime();

    PropertyType getInvocationCreationTimeNode();

    void setInvocationCreationTime(DateTime value);

    DateTime getLastTransitionTime();

    PropertyType getLastTransitionTimeNode();

    void setLastTransitionTime(DateTime value);

    String getLastMethodCall();

    PropertyType getLastMethodCallNode();

    void setLastMethodCall(String value);

    NodeId getLastMethodSessionId();

    PropertyType getLastMethodSessionIdNode();

    void setLastMethodSessionId(NodeId value);

    Argument[] getLastMethodInputArguments();

    PropertyType getLastMethodInputArgumentsNode();

    void setLastMethodInputArguments(Argument[] value);

    Argument[] getLastMethodOutputArguments();

    PropertyType getLastMethodOutputArgumentsNode();

    void setLastMethodOutputArguments(Argument[] value);

    DateTime getLastMethodCallTime();

    PropertyType getLastMethodCallTimeNode();

    void setLastMethodCallTime(DateTime value);

    StatusResult getLastMethodReturnStatus();

    PropertyType getLastMethodReturnStatusNode();

    void setLastMethodReturnStatus(StatusResult value);

}
