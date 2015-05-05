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
