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

import java.util.Arrays;
import java.util.function.Predicate;

import com.inductiveautomation.opcua.sdk.server.Session;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.channel.SecureChannel;
import com.inductiveautomation.opcua.stack.core.security.SecurityPolicy;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.structured.AnonymousIdentityToken;
import com.inductiveautomation.opcua.stack.core.types.structured.UserNameIdentityToken;
import com.inductiveautomation.opcua.stack.core.types.structured.UserTokenPolicy;

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

        byte[] tokenBytes = token.getPassword().bytes();
        if (tokenBytes == null) tokenBytes = new byte[0];

        if (securityPolicy != SecurityPolicy.None) {
            byte[] plainTextBytes = decryptTokenData(channel, tokenBytes);

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
