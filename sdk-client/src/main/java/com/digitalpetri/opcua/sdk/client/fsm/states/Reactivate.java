package com.digitalpetri.opcua.sdk.client.fsm.states;

import java.nio.ByteBuffer;
import java.security.PrivateKey;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.OpcUaClient;
import com.digitalpetri.opcua.sdk.client.OpcUaSession;
import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.sdk.client.fsm.SessionState;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateContext;
import com.digitalpetri.opcua.sdk.client.fsm.SessionStateEvent;
import com.digitalpetri.opcua.stack.client.UaTcpStackClient;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.ClientSecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityAlgorithm;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.structured.ActivateSessionRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ActivateSessionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.SignatureData;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.digitalpetri.opcua.stack.core.types.structured.UserIdentityToken;
import com.digitalpetri.opcua.stack.core.util.SignatureUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reactivate implements SessionState {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final CompletableFuture<UaSession> future = new CompletableFuture<>();

    private volatile OpcUaSession session;

    private final UaSession previousSession;

    public Reactivate(UaSession previousSession) {
        this.previousSession = previousSession;
    }

    @Override
    public void activate(SessionStateEvent event, SessionStateContext context) {
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
                if (ex.getCause() instanceof UaException) {
                    UaException uax = (UaException) ex.getCause();
                    if (uax.getStatusCode().getValue() == StatusCodes.Bad_SessionIdInvalid) {
                        context.handleEvent(SessionStateEvent.ERR_REACTIVATE_FAILED);
                    } else {
                        context.handleEvent(SessionStateEvent.ERR_CONNECTION_LOST);
                    }
                } else {
                    context.handleEvent(SessionStateEvent.ERR_CONNECTION_LOST);
                }

                future.completeExceptionally(ex);
            }
        });
    }

    @Override
    public SessionState transition(SessionStateEvent event, SessionStateContext context) {
        switch (event) {
            case REACTIVATE_SUCCEEDED:
                return new Active(session, future);

            case ERR_REACTIVATE_FAILED:
                return new CreateAndActivate(new CompletableFuture<>()); // TODO flag to indicate transfer subscriptions needed

            case ERR_CONNECTION_LOST:
                return new Reactivate(previousSession);
        }

        return this;
    }

    private CompletableFuture<ActivateSessionResponse> activateSession(SessionStateContext context, UaSession session) {
        OpcUaClient client = context.getClient();
        UaTcpStackClient stackClient = client.getStackClient();

        try {
            EndpointDescription endpoint = stackClient.getEndpoint()
                    .orElseThrow(() -> new Exception("cannot create session with no endpoint configured"));

            Object[] oa = client.getConfig().getIdentityTokenProvider().getIdentityToken(endpoint);
            UserIdentityToken userIdentityToken = (UserIdentityToken) oa[0];
            SignatureData userTokenSignature = (SignatureData) oa[1];

            SignatureData clientSignature = buildClientSignature(
                    stackClient.getSecureChannel(),
                    session.getServerCertificate(),
                    session.getServerNonce());

            ActivateSessionRequest request = new ActivateSessionRequest(
                    client.newRequestHeader(session.getAuthenticationToken()),
                    clientSignature,
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
}
