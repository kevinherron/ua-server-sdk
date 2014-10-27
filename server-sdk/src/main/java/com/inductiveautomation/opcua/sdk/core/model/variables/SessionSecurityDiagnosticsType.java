package com.inductiveautomation.opcua.sdk.core.model.variables;

import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MessageSecurityMode;

public interface SessionSecurityDiagnosticsType extends BaseDataVariableType {

    NodeId getSessionId();

    String getClientUserIdOfSession();

    String[] getClientUserIdHistory();

    String getAuthenticationMechanism();

    String getEncoding();

    String getTransportProtocol();

    MessageSecurityMode getSecurityMode();

    String getSecurityPolicyUri();

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

    void atomicSet(Runnable runnable);

}
