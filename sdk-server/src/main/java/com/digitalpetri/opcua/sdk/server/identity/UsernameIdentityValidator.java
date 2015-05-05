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

import java.util.Arrays;
import java.util.function.Predicate;

import com.digitalpetri.opcua.sdk.server.Session;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.SecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityAlgorithm;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.structured.AnonymousIdentityToken;
import com.digitalpetri.opcua.stack.core.types.structured.UserNameIdentityToken;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;

public class UsernameIdentityValidator extends IdentityValidator {

    private static final Object ANON_IDENTITY_OBJECT = new Object();

    private final boolean allowAnonymous;
    private final Predicate<AuthenticationChallenge> predicate;

    public UsernameIdentityValidator(boolean allowAnonymous,
                                     Predicate<AuthenticationChallenge> predicate) {

        this.allowAnonymous = allowAnonymous;
        this.predicate = predicate;
    }

    @Override
    public Object validateAnonymousToken(AnonymousIdentityToken token, UserTokenPolicy tokenPolicy,
                                         SecureChannel channel, Session session) throws UaException {

        if (allowAnonymous) return ANON_IDENTITY_OBJECT;
        else throw new UaException(StatusCodes.Bad_UserAccessDenied);
    }

    @Override
    public Object validateUsernameToken(UserNameIdentityToken token,
                                        UserTokenPolicy tokenPolicy,
                                        SecureChannel channel,
                                        Session session) throws UaException {

        return validateUserNameIdentityToken(token, channel, session);
    }

    private String validateUserNameIdentityToken(UserNameIdentityToken token,
                                                 SecureChannel channel,
                                                 Session session) throws UaException {

        SecurityPolicy securityPolicy = channel.getSecurityPolicy();
        String username = token.getUserName();
        ByteString lastNonce = session.getLastNonce();
        int lastNonceLength = lastNonce.length();

        if (username == null || username.isEmpty()) {
            throw new UaException(StatusCodes.Bad_IdentityTokenInvalid);
        }

        SecurityAlgorithm algorithm;

        String algorithmUri = token.getEncryptionAlgorithm();
        if (algorithmUri == null || algorithmUri.isEmpty()) {
            algorithm = channel.getSecurityPolicy().getAsymmetricEncryptionAlgorithm();
        } else {
            try {
                algorithm = SecurityAlgorithm.fromUri(algorithmUri);
            } catch (UaException e) {
                throw new UaException(StatusCodes.Bad_IdentityTokenInvalid);
            }

            if (algorithm != SecurityAlgorithm.Rsa15 && algorithm != SecurityAlgorithm.RsaOaep) {
                throw new UaException(StatusCodes.Bad_IdentityTokenInvalid);
            }
        }

        byte[] tokenBytes = token.getPassword().bytes();
        if (tokenBytes == null) tokenBytes = new byte[0];

        if (securityPolicy != SecurityPolicy.None) {
            byte[] plainTextBytes = decryptTokenData(channel, algorithm, tokenBytes);

            int length = ((plainTextBytes[3] & 0xFF) << 24) |
                    ((plainTextBytes[2] & 0xFF) << 16) |
                    ((plainTextBytes[1] & 0xFF) << 8) |
                    (plainTextBytes[0] & 0xFF);

            byte[] passwordBytes = new byte[length - lastNonceLength];
            byte[] nonceBytes = new byte[lastNonceLength];

            System.arraycopy(plainTextBytes, 4, passwordBytes, 0, passwordBytes.length);
            System.arraycopy(plainTextBytes, 4 + passwordBytes.length, nonceBytes, 0, lastNonceLength);

            String password = new String(passwordBytes);
            AuthenticationChallenge challenge = new AuthenticationChallenge(username, password);

            if (Arrays.equals(lastNonce.bytes(), nonceBytes) && predicate.test(challenge)) {
                return username;
            } else {
                throw new UaException(StatusCodes.Bad_UserAccessDenied);
            }
        } else {
            String password = new String(tokenBytes);
            AuthenticationChallenge challenge = new AuthenticationChallenge(username, password);

            if (predicate.test(challenge)) {
                return username;
            } else {
                throw new UaException(StatusCodes.Bad_UserAccessDenied);
            }
        }
    }

    public static final class AuthenticationChallenge {

        private final String username;
        private final String password;

        public AuthenticationChallenge(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public String getPassword() {
            return password;
        }

    }

}
