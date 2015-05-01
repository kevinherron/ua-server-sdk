/*
 * Copyright 2015
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.digitalpetri.opcua.sdk.client;

import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;

public class OpcUaSession implements UaSession {

    private volatile ByteString serverNonce = ByteString.NULL_VALUE;

    private final NodeId authToken;
    private final NodeId sessionId;
    private final String sessionName;
    private final double sessionTimeout;
    private final UInteger maxRequestSize;
    private final ByteString serverCertificate;
    private final SignedSoftwareCertificate[] serverSoftwareCertificates;

    public OpcUaSession(NodeId authToken,
                        NodeId sessionId,
                        String sessionName,
                        double sessionTimeout,
                        UInteger maxRequestSize,
                        ByteString serverCertificate,
                        SignedSoftwareCertificate[] serverSoftwareCertificates) {

        this.authToken = authToken;
        this.sessionId = sessionId;
        this.sessionName = sessionName;
        this.sessionTimeout = sessionTimeout;
        this.maxRequestSize = maxRequestSize;
        this.serverCertificate = serverCertificate;
        this.serverSoftwareCertificates = serverSoftwareCertificates;
    }

    @Override
    public NodeId getAuthenticationToken() {
        return authToken;
    }

    @Override
    public NodeId getSessionId() {
        return sessionId;
    }

    @Override
    public String getSessionName() {
        return sessionName;
    }

    @Override
    public Double getSessionTimeout() {
        return sessionTimeout;
    }

    @Override
    public UInteger getMaxRequestSize() {
        return maxRequestSize;
    }

    @Override
    public SignedSoftwareCertificate[] getServerSoftwareCertificates() {
        return serverSoftwareCertificates;
    }

    @Override
    public ByteString getServerCertificate() {
        return serverCertificate;
    }

    @Override
    public ByteString getServerNonce() {
        return serverNonce;
    }

    public void setServerNonce(ByteString serverNonce) {
        this.serverNonce = serverNonce;
    }

}
