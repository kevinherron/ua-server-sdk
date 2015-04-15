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

package com.inductiveautomation.opcua.sdk.client.fsm.states;

import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.sdk.client.OpcUaClient;
import com.inductiveautomation.opcua.sdk.client.OpcUaSession;
import com.inductiveautomation.opcua.sdk.client.api.UaSession;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionState;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionStateContext;
import com.inductiveautomation.opcua.sdk.client.fsm.SessionStateEvent;
import com.inductiveautomation.opcua.stack.client.UaTcpStackClient;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.channel.ClientSecureChannel;
import com.inductiveautomation.opcua.stack.core.security.SecurityAlgorithm;
import com.inductiveautomation.opcua.stack.core.security.SecurityPolicy;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.structured.ActivateSessionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ActivateSessionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSessionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SignatureData;
import com.inductiveautomation.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.inductiveautomation.opcua.stack.core.types.structured.UserIdentityToken;
import com.inductiveautomation.opcua.stack.core.util.CertificateUtil;
import com.inductiveautomation.opcua.stack.core.util.SignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ActivatingSession implements SessionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CompletableFuture<UaSession> sessionFuture;
    private final CreateSessionResponse createSessionResponse;

    public ActivatingSession(CompletableFuture<UaSession> sessionFuture,
                             CreateSessionResponse createSessionResponse) {

        this.sessionFuture = sessionFuture;
        this.createSessionResponse = createSessionResponse;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {

        try {
            OpcUaClient client = context.getClient();
            UaTcpStackClient stackClient = client.getStackClient();

            Object[] oa = client.getConfig().getIdentityTokenProvider().getIdentityToken(stackClient.getEndpoint());
            UserIdentityToken userIdentityToken = (UserIdentityToken) oa[0];
            SignatureData userTokenSignature = (SignatureData) oa[1];

            ActivateSessionRequest request = new ActivateSessionRequest(
                    client.newRequestHeader(createSessionResponse.getAuthenticationToken()),
                    buildClientSignature(stackClient.getSecureChannel()),
                    new SignedSoftwareCertificate[0],
                    new String[0],
                    new ExtensionObject(userIdentityToken),
                    userTokenSignature
            );

            CompletableFuture<ActivateSessionResponse> future = stackClient.sendRequest(request);

            future.whenComplete((r, ex) -> {
                if (r != null) {
                    logger.debug("ActivateSession succeeded.");

                    context.handleEvent(SessionStateEvent.ACTIVATE_SESSION_SUCCEEDED);
                } else {
                    logger.debug("ActivateSession failed: {}", ex.getMessage(), ex);

                    context.handleEvent(SessionStateEvent.ACTIVATE_SESSION_FAILED);
                }
            });

        } catch (Throwable t) {
            logger.warn("ActivateSession failed: {}", t.getMessage(), t);

            context.handleEvent(SessionStateEvent.ACTIVATE_SESSION_FAILED);
        }
    }

    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        switch (event) {
            case ACTIVATE_SESSION_FAILED:
                return new Inactive();

            case ACTIVATE_SESSION_SUCCEEDED:
                ByteString bs = createSessionResponse.getServerCertificate();

                X509Certificate serverCertificate = null;

                if (bs.isNotNull()) {
                    try {
                        serverCertificate = CertificateUtil.decodeCertificate(bs.bytes());
                    } catch (UaException e) {
                        logger.warn("Error decoding server certificate: {}", e.getMessage(), e);
                    }
                }

                OpcUaSession session = new OpcUaSession(
                        createSessionResponse.getAuthenticationToken(),
                        createSessionResponse.getSessionId(),
                        context.getClient().getConfig().getSessionName().get(),
                        createSessionResponse.getRevisedSessionTimeout(),
                        createSessionResponse.getMaxRequestMessageSize(),
                        serverCertificate,
                        createSessionResponse.getServerSoftwareCertificates()
                );

                return new CreatingSubscriptions(session, sessionFuture);
        }

        return this;
    }

    @Override
    public CompletableFuture<UaSession> sessionFuture() {
        return sessionFuture;
    }

    private SignatureData buildClientSignature(ClientSecureChannel secureChannel) {
        ByteString serverNonce = createSessionResponse.getServerNonce() != null ?
                createSessionResponse.getServerNonce() : ByteString.NULL_VALUE;

        ByteString serverCert = createSessionResponse.getServerCertificate() != null ?
                createSessionResponse.getServerCertificate() : ByteString.NULL_VALUE;

        byte[] serverNonceBytes = Optional.ofNullable(serverNonce.bytes()).orElse(new byte[0]);

        byte[] serverCertBytes = Optional.ofNullable(serverCert.bytes()).orElse(new byte[0]);

        // Signature is serverCert + serverNonce signed with our private key.
        byte[] signature = new byte[serverCertBytes.length + serverNonceBytes.length];
        System.arraycopy(serverCertBytes, 0, signature, 0, serverCertBytes.length);
        System.arraycopy(serverNonceBytes, 0, signature, serverCertBytes.length, serverNonceBytes.length);

        SecurityAlgorithm signatureAlgorithm = secureChannel.getSecurityPolicy().getAsymmetricSignatureAlgorithm();

        if (secureChannel.getSecurityPolicy() != SecurityPolicy.None) {
            try {
                PrivateKey privateKey = secureChannel.getKeyPair().getPrivate();

                signature = SignatureUtil.sign(
                        signatureAlgorithm,
                        privateKey,
                        ByteBuffer.wrap(signature)
                );
            } catch (Throwable t) {
                logger.warn("Asymmetric signing failed: {}", t.getMessage(), t);
            }
        }

        return new SignatureData(signatureAlgorithm.getUri(), ByteString.of(signature));
    }

}
