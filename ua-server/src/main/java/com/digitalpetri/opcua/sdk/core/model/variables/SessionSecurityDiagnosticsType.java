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

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;


public interface SessionSecurityDiagnosticsType extends BaseDataVariableType {


    NodeId getSessionId();

    BaseDataVariableType getSessionIdNode();

    void setSessionId(NodeId value);

    String getClientUserIdOfSession();

    BaseDataVariableType getClientUserIdOfSessionNode();

    void setClientUserIdOfSession(String value);

    String[] getClientUserIdHistory();

    BaseDataVariableType getClientUserIdHistoryNode();

    void setClientUserIdHistory(String[] value);

    String getAuthenticationMechanism();

    BaseDataVariableType getAuthenticationMechanismNode();

    void setAuthenticationMechanism(String value);

    String getEncoding();

    BaseDataVariableType getEncodingNode();

    void setEncoding(String value);

    String getTransportProtocol();

    BaseDataVariableType getTransportProtocolNode();

    void setTransportProtocol(String value);

    MessageSecurityMode getSecurityMode();

    BaseDataVariableType getSecurityModeNode();

    void setSecurityMode(MessageSecurityMode value);

    String getSecurityPolicyUri();

    BaseDataVariableType getSecurityPolicyUriNode();

    void setSecurityPolicyUri(String value);

    ByteString getClientCertificate();

    BaseDataVariableType getClientCertificateNode();

    void setClientCertificate(ByteString value);
}
