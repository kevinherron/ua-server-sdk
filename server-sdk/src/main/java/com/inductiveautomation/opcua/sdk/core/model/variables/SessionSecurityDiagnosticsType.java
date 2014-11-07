package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.sdk.core.model.UaMandatory;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MessageSecurityMode;

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
