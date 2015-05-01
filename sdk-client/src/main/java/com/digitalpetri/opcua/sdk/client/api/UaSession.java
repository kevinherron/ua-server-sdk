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
