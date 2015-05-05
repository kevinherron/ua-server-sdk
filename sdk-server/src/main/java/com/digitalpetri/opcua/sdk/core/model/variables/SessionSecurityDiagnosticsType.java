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
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;

public interface SessionSecurityDiagnosticsType extends BaseDataVariableType {

    @UaMandatory("SessionId")
    NodeId getSessionId();

    @UaMandatory("ClientUserIdOfSession")
    String getClientUserIdOfSession();

    @UaMandatory("ClientUserIdHistory")
    String[] getClientUserIdHistory();

    @UaMandatory("AuthenticationMechanism")
    String getAuthenticationMechanism();

    @UaMandatory("Encoding")
    String getEncoding();

    @UaMandatory("TransportProtocol")
    String getTransportProtocol();

    @UaMandatory("SecurityMode")
    MessageSecurityMode getSecurityMode();

    @UaMandatory("SecurityPolicyUri")
    String getSecurityPolicyUri();

    @UaMandatory("ClientCertificate")
    ByteString getClientCertificate();

    void setSessionId(NodeId sessionId);

    void setClientUserIdOfSession(String clientUserIdOfSession);

    void setClientUserIdHistory(String[] clientUserIdHistory);

    void setAuthenticationMechanism(String authenticationMechanism);

    void setEncoding(String encoding);

    void setTransportProtocol(String transportProtocol);

    void setSecurityMode(MessageSecurityMode securityMode);

    void setSecurityPolicyUri(String securityPolicyUri);

    void setClientCertificate(ByteString clientCertificate);

}
