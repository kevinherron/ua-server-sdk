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

package com.digitalpetri.opcua.sdk.client.api;

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;

public interface UaSession {

    /**
     * Get the authentication token assigned by the server.
     *
     * @return a unique {@link NodeId} assigned by the server to the session.
     */
    NodeId getAuthenticationToken();

    /**
     * Get the session id assigned by the server.
     * <p>
     * This identifier is used to access the diagnostics information for the session in the server address space. It is
     * also used in the audit logs and any events that report information related to the session.
     *
     * @return a unique {@link NodeId} assigned by the server to the session.
     */
    NodeId getSessionId();

    /**
     * @return the human-readable name assigned to this session by the client.
     */
    String getSessionName();

    /**
     * Get the revised session timeout, that is, the number of milliseconds a session may remain open without activity.
     *
     * @return the revised session timeout.
     */
    Double getSessionTimeout();

    /**
     * @return the maximum allowable size for any request sent to the server.
     */
    UInteger getMaxRequestSize();

    /**
     * @return the last nonce received from the server.
     */
    ByteString getServerNonce();

    /**
     * @return the server application instance certificate.
     */
    ByteString getServerCertificate();

    /**
     * @return the server {@link SignedSoftwareCertificate}s.
     */
    SignedSoftwareCertificate[] getServerSoftwareCertificates();

}
