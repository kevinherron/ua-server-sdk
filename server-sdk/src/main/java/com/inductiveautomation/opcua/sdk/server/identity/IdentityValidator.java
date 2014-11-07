/*
 * Copyright 2014
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

package com.inductiveautomation.opcua.sdk.server.identity;

import javax.crypto.Cipher;
import java.nio.ByteBuffer;
import java.security.GeneralSecurityException;

import com.inductiveautomation.opcua.sdk.server.Session;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.channel.SecureChannel;
import com.inductiveautomation.opcua.stack.core.types.structured.AnonymousIdentityToken;
import com.inductiveautomation.opcua.stack.core.types.structured.IssuedIdentityToken;
import com.inductiveautomation.opcua.stack.core.types.structured.UserNameIdentityToken;
import com.inductiveautomation.opcua.stack.core.types.structured.UserTokenPolicy;
import com.inductiveautomation.opcua.stack.core.types.structured.X509IdentityToken;

public abstract class IdentityValidator {

    public Object validateAnonymousToken(AnonymousIdentityToken token, UserTokenPolicy tokenPolicy,
                                           SecureChannel channel, Session session) throws UaException {
        throw new UaException(StatusCodes.Bad_IdentityTokenInvalid);
    }

    public Object validateUsernameToken(UserNameIdentityToken token, UserTokenPolicy tokenPolicy,
                                          SecureChannel channel, Session session) throws UaException {
        throw new UaException(StatusCodes.Bad_IdentityTokenInvalid);
    }

    public Object validateX509Token(X509IdentityToken token, UserTokenPolicy tokenPolicy,
                                      SecureChannel channel, Session session) throws UaException {
        throw new UaException(StatusCodes.Bad_IdentityTokenInvalid);
    }

    public Object validateIssuedIdentityToken(IssuedIdentityToken token, UserTokenPolicy tokenPolicy,
                                                SecureChannel channel, Session session) throws UaException {
        throw new UaException(StatusCodes.Bad_IdentityTokenInvalid);
    }

    protected byte[] decryptTokenData(SecureChannel secureChannel, byte[] dataBytes) throws UaException {
        int cipherTextBlockSize = secureChannel.getLocalAsymmetricCipherTextBlockSize();
        int blockCount = dataBytes.length / cipherTextBlockSize;

        int plainTextBufferSize = cipherTextBlockSize * blockCount;

        byte[] plainTextBytes = new byte[plainTextBufferSize];
        ByteBuffer plainTextNioBuffer = ByteBuffer.wrap(plainTextBytes);

        ByteBuffer passwordNioBuffer = ByteBuffer.wrap(dataBytes);

        try {
            Cipher cipher = getCipher(secureChannel);

            for (int blockNumber = 0; blockNumber < blockCount; blockNumber++) {
                passwordNioBuffer.limit(passwordNioBuffer.position() + cipherTextBlockSize);

                cipher.doFinal(passwordNioBuffer, plainTextNioBuffer);
            }
        } catch (GeneralSecurityException e) {
            throw new UaException(StatusCodes.Bad_SecurityChecksFailed, e);
        }

        return plainTextBytes;
    }

    protected  Cipher getCipher(SecureChannel channel) throws UaException {
        try {
            String transformation = channel.getSecurityPolicy().getAsymmetricEncryptionAlgorithm().getTransformation();
            Cipher cipher = Cipher.getInstance(transformation);
            cipher.init(Cipher.DECRYPT_MODE, channel.getKeyPair().getPrivate());
            return cipher;
        } catch (GeneralSecurityException e) {
            throw new UaException(StatusCodes.Bad_SecurityChecksFailed, e);
        }
    }

}
