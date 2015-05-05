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

package com.digitalpetri.opcua.sdk.client.fsm.states;

import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicReference;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.sdk.client.OpcUaSession;
import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.sdk.client.fsm.SessionState;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateContext;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateEvent;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityAlgorithm;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.structured.ActivateSessionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ActivateSessionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSessionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSessionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.SignatureData;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.digitalpetri.opcua.stack.core.types.structured.UserIdentityToken;
import com.digitalpetri.opcua.stack.core.util.CertificateUtil;
import com.digitalpetri.opcua.stack.core.util.NonceUtil;
import com.digitalpetri.opcua.stack.core.util.SignatureUtil;
import org.jooq.lambda.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateAndActivate implements SessionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final AtomicReference<OpcUaSession> session = new AtomicReference<>();

    private final CompletableFuture<UaSession> future;

    public CreateAndActivate(CompletableFuture<UaSession> future) {
        this.future = future;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {
        CompletableFuture<OpcUaSession> f = createSession(context).thenCompose(csr -> {
            logger.debug("CreateSession succeeded, id={}, timeout={}",
                    csr.getSessionId(), csr.getRevisedSessionTimeout());

            return activateSession(context, csr).thenApply(asr -> {
                logger.debug("ActivationSession succeeded, id={}", csr.getSessionId());

                ByteString bs = csr.getServerCertificate();

                X509Certificate serverCertificate = null;

                if (bs.isNotNull()) {
                    try {
                        serverCertificate = CertificateUtil.decodeCertificate(bs.bytes());
                    } catch (UaException e) {
                        logger.warn("Error decoding server certificate: {}", e.getMessage(), e);
                    }
                }

                OpcUaSession session = new OpcUaSession(
                        csr.getAuthenticationToken(),
                        csr.getSessionId(),
                        context.getClient().getConfig().getSessionName().get(),
                        csr.getRevisedSessionTimeout(),
                        csr.getMaxRequestMessageSize(),
                        bs,
                        csr.getServerSoftwareCertificates());

                session.setServerNonce(asr.getServerNonce());

                return session;
            });
        });


        f.whenComplete((session, ex) -> {
            if (session != null) {
                this.session.set(session);

                context.handleEvent(SessionStateEvent.CREATE_AND_ACTIVATE_SUCCEEDED);
            } else {
                logger.debug("CreateAndActivate failed: {}", ex.getMessage(), ex);

                context.handleEvent(SessionStateEvent.ERR_CREATE_AND_ACTIVATE_FAILED);

                future.completeExceptionally(ex);
            }
        });
    }

    private CompletableFuture<CreateSessionResponse> createSession(SessionStateContext context) {
        OpcUaClient client = context.getClient();
        UaTcpStackClient stackClient = client.getStackClient();
        ClientSecureChannel secureChannel = stackClient.getSecureChannel();

        String serverUri = stackClient.getEndpoint().flatMap(e -> {
            String gatewayServerUri = e.getServer().getGatewayServerUri();
            if (gatewayServerUri != null && !gatewayServerUri.isEmpty()) {
                return Optional.ofNullable(e.getServer().getApplicationUri());
            } else {
                return Optional.empty();
            }
        }).orElse(null);


        SecurityAlgorithm algorithm = secureChannel.getSecurityPolicy()
                .getAsymmetricEncryptionAlgorithm();
        ByteString clientNonce = NonceUtil.generateNonce(algorithm);

        ByteString clientCertificate;
        try {
            clientCertificate = secureChannel.getLocalCertificateBytes();
        } catch (UaException e) {
            clientCertificate = ByteString.NULL_VALUE;
        }

        CreateSessionRequest request = new CreateSessionRequest(
                client.newRequestHeader(),
                stackClient.getApplication(),
                serverUri,
                stackClient.getEndpointUrl(),
                client.getConfig().getSessionName().get(),
                clientNonce,
                clientCertificate,
                client.getConfig().getSessionTimeout(),
                client.getConfig().getMaxResponseMessageSize());

        logger.debug("Sending CreateSessionRequest...");

        return stackClient.sendRequest(request);
    }

    private CompletableFuture<ActivateSessionResponse> activateSession(SessionStateContext context,
                                                                       CreateSessionResponse response) {

        OpcUaClient client = context.getClient();
        UaTcpStackClient stackClient = client.getStackClient();

        try {
            EndpointDescription endpoint = stackClient.getEndpoint()
                    .orElseThrow(() -> new Exception("cannot create session with no endpoint configured"));

            Tuple2<UserIdentityToken, SignatureData> tuple =
                    client.getConfig().getIdentityProvider().getIdentityToken(endpoint, response.getServerNonce());

            UserIdentityToken userIdentityToken = tuple.v1();
            SignatureData userTokenSignature = tuple.v2();

            ActivateSessionRequest request = new ActivateSessionRequest(
                    client.newRequestHeader(response.getAuthenticationToken()),
                    buildClientSignature(stackClient.getSecureChannel(), response),
                    new SignedSoftwareCertificate[0],
                    new String[0],
                    new ExtensionObject(userIdentityToken),
                    userTokenSignature);

            logger.debug("Sending ActivateSessionRequest...");

            return stackClient.sendRequest(request);
        } catch (Exception e) {
            CompletableFuture<ActivateSessionResponse> f = new CompletableFuture<>();
            f.completeExceptionally(e);
            return f;
        }
    }

    private SignatureData buildClientSignature(ClientSecureChannel secureChannel, CreateSessionResponse response) {
        ByteString serverNonce = response.getServerNonce() != null ?
                response.getServerNonce() : ByteString.NULL_VALUE;

        ByteString serverCert = response.getServerCertificate() != null ?
                response.getServerCertificate() : ByteString.NULL_VALUE;

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

    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        switch (event) {
            case ERR_CREATE_AND_ACTIVATE_FAILED:
                return new Inactive();

            case CREATE_AND_ACTIVATE_SUCCEEDED:
                return new CreatingSubscriptions(session.get(), future);
        }

        return this;
    }

    @Override
    public CompletableFuture<UaSession> getSessionFuture() {
        return future;
    }

}
