package com.digitalpetri.opcua.sdk.client.api.identity;

import javax.crypto.Cipher;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPublicKey;
import java.util.Arrays;
import java.util.Optional;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.security.SecurityAlgorithm;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.enumerated.UserTokenType;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.SignatureData;
import com.digitalpetri.opcua.stack.core.types.structured.UserIdentityToken;
import com.digitalpetri.opcua.stack.core.types.structured.UserNameIdentityToken;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;
import com.digitalpetri.opcua.stack.core.util.CertificateUtil;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.jooq.lambda.tuple.Tuple2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * An {@link IdentityProvider} that will choose the first available username+password {@link UserTokenPolicy}.
 */
public class UsernameProvider implements IdentityProvider {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String username;
    private final String password;

    public UsernameProvider(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Override
    public Tuple2<UserIdentityToken, SignatureData> getIdentityToken(EndpointDescription endpoint,
                                                                     ByteString serverNonce) throws Exception {

        UserTokenPolicy tokenPolicy = Arrays.stream(endpoint.getUserIdentityTokens())
                .filter(t -> t.getTokenType() == UserTokenType.UserName)
                .findFirst().orElseThrow(() -> new Exception("no username token policy found"));

        String policyId = tokenPolicy.getPolicyId();

        SecurityPolicy securityPolicy = SecurityPolicy.None;

        String securityPolicyUri = tokenPolicy.getSecurityPolicyUri();
        try {
            if (securityPolicyUri != null && !securityPolicyUri.isEmpty()) {
                securityPolicy = SecurityPolicy.fromUri(securityPolicyUri);
            } else {
                securityPolicyUri = endpoint.getSecurityPolicyUri();
                securityPolicy = SecurityPolicy.fromUri(securityPolicyUri);
            }
        } catch (Throwable t) {
            logger.warn("Error parsing SecurityPolicy for uri={}, falling back to no security.", securityPolicyUri);
        }

        byte[] passwordBytes = password.getBytes("UTF-8");
        byte[] nonceBytes = Optional.ofNullable(serverNonce.bytes()).orElse(new byte[0]);

        ByteBuf buffer = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN);
        buffer.writeInt(passwordBytes.length + nonceBytes.length);
        buffer.writeBytes(passwordBytes);
        buffer.writeBytes(nonceBytes);

        if (securityPolicy != SecurityPolicy.None) {
            ByteString bs = endpoint.getServerCertificate();
            X509Certificate certificate = CertificateUtil.decodeCertificate(bs.bytes());

            int plainTextBlockSize = getPlainTextBlockSize(certificate, securityPolicy);
            int blockCount = buffer.readableBytes() / plainTextBlockSize;
            Cipher cipher = getAndInitializeCipher(certificate, securityPolicy);

            ByteBuffer plainTextNioBuffer = buffer.nioBuffer();
            ByteBuffer cipherTextNioBuffer = Unpooled.buffer().order(ByteOrder.LITTLE_ENDIAN).nioBuffer();

            for (int blockNumber = 0; blockNumber < blockCount; blockNumber++) {
                int position = blockNumber * plainTextBlockSize;
                int limit = (blockNumber + 1) * plainTextBlockSize;
                plainTextNioBuffer.position(position).limit(limit);

                cipher.doFinal(plainTextNioBuffer, cipherTextNioBuffer);
            }

            cipherTextNioBuffer.flip();
            buffer = Unpooled.wrappedBuffer(cipherTextNioBuffer);
        }

        byte[] bs = new byte[buffer.readableBytes()];
        buffer.readBytes(bs);

        UserNameIdentityToken token = new UserNameIdentityToken(
                policyId,
                username,
                ByteString.of(bs),
                securityPolicy.getAsymmetricEncryptionAlgorithm().getUri());

        return new Tuple2<>(token, new SignatureData());
    }

    public Cipher getAndInitializeCipher(X509Certificate serverCertificate,
                                         SecurityPolicy securityPolicy) throws UaException {

        assert (serverCertificate != null);

        try {
            String transformation = securityPolicy.getAsymmetricEncryptionAlgorithm().getTransformation();
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.ENCRYPT_MODE, serverCertificate.getPublicKey());
            return cipher;
        } catch (GeneralSecurityException e) {
            throw new UaException(StatusCodes.Bad_SecurityChecksFailed, e);
        }
    }

    public int getPlainTextBlockSize(X509Certificate certificate, SecurityPolicy securityPolicy) {
        SecurityAlgorithm algorithm = securityPolicy.getAsymmetricEncryptionAlgorithm();

        switch (algorithm) {
            case Rsa15:
                return (getAsymmetricKeyLength(certificate) + 1) / 8 - 11;
            case RsaOaep:
                return (getAsymmetricKeyLength(certificate) + 1) / 8 - 42;
        }

        return 1;
    }

    private int getAsymmetricKeyLength(X509Certificate certificate) {
        PublicKey publicKey = certificate != null ?
                certificate.getPublicKey() : null;

        return (publicKey instanceof RSAPublicKey) ?
                ((RSAPublicKey) publicKey).getModulus().bitLength() : 0;
    }

    @Override
    public String toString() {
        return "UsernameProvider{" +
                "username='" + username + '\'' +
                '}';
    }

}
