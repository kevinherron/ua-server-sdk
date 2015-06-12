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
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.sdk.client.OpcUaSession;
import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.sdk.client.fsm.SessionState;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateContext;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateEvent;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityAlgorithm;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.structured.ActivateSessionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ActivateSessionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.SignatureData;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.digitalpetri.opcua.stack.core.types.structured.UserIdentityToken;
import com.digitalpetri.opcua.stack.core.util.SignatureUtil;
import org.jooq.lambda.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reactivating implements SessionState {

    private static final int MAX_REACTIVATE_DELAY_SECONDS = 16;

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CompletableFuture<UaSession> future = new CompletableFuture<>();

    private volatile ScheduledFuture<?> scheduledFuture;
    private volatile OpcUaSession session;

    private final UaSession previousSession;
    private final long delay;

    public Reactivating(UaSession previousSession, long delay) {
        this.previousSession = previousSession;
        this.delay = delay;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {
        Runnable activate = () -> {
            scheduledFuture = null;

            activateSession(context, previousSession).whenComplete((asr, ex) -> {
                if (asr != null) {
                    session = new OpcUaSession(
                            previousSession.getAuthenticationToken(),
                            previousSession.getSessionId(),
                            context.getClient().getConfig().getSessionName().get(),
                            previousSession.getSessionTimeout(),
                            previousSession.getMaxRequestSize(),
                            previousSession.getServerCertificate(),
                            previousSession.getServerSoftwareCertificates());

                    session.setServerNonce(asr.getServerNonce());

                    context.handleEvent(SessionStateEvent.REACTIVATE_SUCCEEDED);
                } else {
                    StatusCode statusCode = UaException.extract(ex)
                            .map(UaException::getStatusCode)
                            .orElse(StatusCode.BAD);

                    if (statusCode.getValue() == StatusCodes.Bad_SessionIdInvalid ||
                            statusCode.getValue() == StatusCodes.Bad_SessionClosed ||
                            statusCode.getValue() == StatusCodes.Bad_SessionNotActivated ||
                            statusCode.getValue() == StatusCodes.Bad_SecurityChecksFailed) {

                        // Treat any session-related errors as re-activate failed.
                        context.handleEvent(SessionStateEvent.ERR_REACTIVATE_INVALID);
                    } else {
                        context.handleEvent(SessionStateEvent.ERR_REACTIVATE_FAILED);
                    }

                    future.completeExceptionally(ex);
                }
            });
        };

        if (scheduledFuture == null || (scheduledFuture != null && scheduledFuture.cancel(false))) {
            logger.debug("Reactivating in {} seconds...", delay);

            scheduledFuture = Stack.sharedScheduledExecutor().schedule(activate, delay, TimeUnit.SECONDS);
        }

    }

    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        switch (event) {
            case REACTIVATE_SUCCEEDED:
                return new Active(session, future);

            case ERR_REACTIVATE_FAILED:
                return new Reactivating(previousSession, nextDelay());

            case ERR_REACTIVATE_INVALID:
                return new CreatingSession(new CompletableFuture<>());
        }

        return this;
    }

    private CompletableFuture<ActivateSessionResponse> activateSession(SessionStateContext context, UaSession session) {
        OpcUaClient client = context.getClient();
        UaTcpStackClient stackClient = client.getStackClient();

        try {
            EndpointDescription endpoint = stackClient.getEndpoint()
                    .orElseThrow(() -> new Exception("cannot create session with no endpoint configured"));

            Tuple2<UserIdentityToken, SignatureData> tuple =
                    client.getConfig().getIdentityProvider().getIdentityToken(endpoint, session.getServerNonce());

            UserIdentityToken userIdentityToken = tuple.v1();
            SignatureData userTokenSignature = tuple.v2();

            SignatureData clientSignature = buildClientSignature(
                    stackClient.getSecureChannel(),
                    session.getServerCertificate(),
                    session.getServerNonce());

            ActivateSessionRequest request = new ActivateSessionRequest(
                    client.newRequestHeader(session.getAuthenticationToken()),
                    clientSignature,
                    new SignedSoftwareCertificate[0],
                    new String[0],
                    ExtensionObject.encode(userIdentityToken),
                    userTokenSignature);

            logger.debug("Sending ActivateSessionRequest...");

            return stackClient.sendRequest(request);
        } catch (Exception e) {
            CompletableFuture<ActivateSessionResponse> f = new CompletableFuture<>();
            f.completeExceptionally(e);
            return f;
        }
    }

    private SignatureData buildClientSignature(ClientSecureChannel secureChannel,
                                               ByteString serverCertificate,
                                               ByteString serverNonce) {

        byte[] serverNonceBytes = Optional.ofNullable(serverNonce.bytes()).orElse(new byte[0]);
        byte[] serverCertificateBytes = Optional.ofNullable(serverCertificate.bytes()).orElse(new byte[0]);

        // Signature is serverCert + serverNonce signed with our private key.
        byte[] signature = new byte[serverCertificateBytes.length + serverNonceBytes.length];
        System.arraycopy(serverCertificateBytes, 0, signature, 0, serverCertificateBytes.length);
        System.arraycopy(serverNonceBytes, 0, signature, serverCertificateBytes.length, serverNonceBytes.length);

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
    public CompletableFuture<UaSession> getSessionFuture() {
        return future;
    }

    private long nextDelay() {
        if (delay == 0) {
            return 1;
        } else {
            return Math.min(delay << 1, MAX_REACTIVATE_DELAY_SECONDS);
        }
    }

}
