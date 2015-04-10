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

package com.inductiveautomation.opcua.sdk.client.api;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.SignedSoftwareCertificate;

import java.security.cert.X509Certificate;
import java.util.Optional;

public interface UaSession {

    NodeId getAuthToken();

    NodeId getSessionId();

    String getSessionName();

    Double getSessionTimeout();

    UInteger getMaxRequestSize();

    Optional<X509Certificate> getServerCertificate();

    SignedSoftwareCertificate[] getServerSoftwareCertificates();

}
