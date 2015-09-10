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

package com.digitalpetri.opcua.sdk.server.identity;

import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;
import javax.crypto.Cipher;

import com.digitalpetri.opcua.sdk.server.Session;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.SecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityAlgorithm;
import com.digitalpetri.opcua.stack.core.types.structured.AnonymousIdentityToken;
import com.digitalpetri.opcua.stack.core.types.structured.IssuedIdentityToken;
import com.digitalpetri.opcua.stack.core.types.structured.UserNameIdentityToken;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;
import com.digitalpetri.opcua.stack.core.types.structured.X509IdentityToken;

public abstract class IdentityValidator {

    /**
     * Validate an {@link AnonymousIdentityToken} and return an identity Object that represents the user.
     * <p>
     * This Object should implement equality in such a way that a subsequent identity validation for the same user
     * yields a comparable Object.
     *
     * @param token       the {@link AnonymousIdentityToken}.
     * @param tokenPolicy the {@link UserTokenPolicy} specified by the policyId in {@code token}.
     * @param channel     the {@link SecureChannel} the request is arriving on.
     * @param session     the {@link Session} the request is arriving on.
     * @return an identity Object that represents the user.
     * @throws UaException if the token is invalid, rejected, or user access is denied.
     */
    public Object validateAnonymousToken(AnonymousIdentityToken token, UserTokenPolicy tokenPolicy,
                                         SecureChannel channel, Session session) throws UaException {
        throw new UaException(StatusCodes.Bad_IdentityTokenInvalid);
    }

    /**
     * Validate a {@link UserNameIdentityToken} and return an identity Object that represents the user.
     * <p>
     * This Object should implement equality in such a way that a subsequent identity validation for the same user
     * yields a comparable Object.
     *
     * @param token       the {@link UserNameIdentityToken}.
     * @param tokenPolicy the {@link UserTokenPolicy} specified by the policyId in {@code token}.
     * @param channel     the {@link SecureChannel} the request is arriving on.
     * @param session     the {@link Session} the request is arriving on.
     * @return an identity Object that represents the user.
     * @throws UaException if the token is invalid, rejected, or user access is denied.
     */
    public Object validateUsernameToken(UserNameIdentityToken token, UserTokenPolicy tokenPolicy,
                                        SecureChannel channel, Session session) throws UaException {
        throw new UaException(StatusCodes.Bad_IdentityTokenInvalid);
    }

    /**
     * Validate an {@link X509IdentityToken} and return an identity Object that represents the user.
     * <p>
     * This Object should implement equality in such a way that a subsequent identity validation for the same user
     * yields a comparable Object.
     *
     * @param token       the {@link X509IdentityToken}.
     * @param tokenPolicy the {@link UserTokenPolicy} specified by the policyId in {@code token}.
     * @param channel     the {@link SecureChannel} the request is arriving on.
     * @param session     the {@link Session} the request is arriving on.
     * @return an identity Object that represents the user.
     * @throws UaException if the token is invalid, rejected, or user access is denied.
     */
    public Object validateX509Token(X509IdentityToken token, UserTokenPolicy tokenPolicy,
                                    SecureChannel channel, Session session) throws UaException {
        throw new UaException(StatusCodes.Bad_IdentityTokenInvalid);
    }

    /**
     * Validate an {@link IssuedIdentityToken} and return an identity Object that represents the user.
     * <p>
     * This Object should implement equality in such a way that a subsequent identity validation for the same user
     * yields a comparable Object.
     *
     * @param token       the {@link IssuedIdentityToken}.
     * @param tokenPolicy the {@link UserTokenPolicy} specified by the policyId in {@code token}.
     * @param channel     the {@link SecureChannel} the request is arriving on.
     * @param session     the {@link Session} the request is arriving on.
     * @return an identity Object that represents the user.
     * @throws UaException if the token is invalid, rejected, or user access is denied.
     */
    public Object validateIssuedIdentityToken(IssuedIdentityToken token, UserTokenPolicy tokenPolicy,
                                              SecureChannel channel, Session session) throws UaException {
        throw new UaException(StatusCodes.Bad_IdentityTokenInvalid);
    }

    /**
     * Decrypt the data contained in a {@link UserNameIdentityToken} or {@link IssuedIdentityToken}.
     * <p>
     * See {@link UserNameIdentityToken#getPassword()} and {@link IssuedIdentityToken#getTokenData()}.
     *
     * @param secureChannel the {@link SecureChannel}.
     * @param dataBytes     the encrypted data.
     * @return the decrypted data.
     * @throws UaException if decryption fails.
     */
    protected byte[] decryptTokenData(SecureChannel secureChannel,
                                      SecurityAlgorithm algorithm,
                                      byte[] dataBytes) throws UaException {

        int cipherTextBlockSize = secureChannel.getLocalAsymmetricCipherTextBlockSize();
        int blockCount = dataBytes.length / cipherTextBlockSize;

        int plainTextBufferSize = cipherTextBlockSize * blockCount;

        byte[] plainTextBytes = new byte[plainTextBufferSize];
        ByteBuffer plainTextNioBuffer = ByteBuffer.wrap(plainTextBytes);

        ByteBuffer passwordNioBuffer = ByteBuffer.wrap(dataBytes);

        try {
            Cipher cipher = getCipher(secureChannel, algorithm);

            for (int blockNumber = 0; blockNumber < blockCount; blockNumber++) {
                passwordNioBuffer.limit(passwordNioBuffer.position() + cipherTextBlockSize);

                cipher.doFinal(passwordNioBuffer, plainTextNioBuffer);
            }
        } catch (GeneralSecurityException e) {
            throw new UaException(StatusCodes.Bad_SecurityChecksFailed, e);
        }

        return plainTextBytes;
    }

    private Cipher getCipher(SecureChannel channel, SecurityAlgorithm algorithm) throws UaException {
        try {
            String transformation = algorithm.getTransformation();
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, channel.getKeyPair().getPrivate());
            return cipher;
        } catch (GeneralSecurityException e) {
            throw new UaException(StatusCodes.Bad_SecurityChecksFailed, e);
        }
    }

}
