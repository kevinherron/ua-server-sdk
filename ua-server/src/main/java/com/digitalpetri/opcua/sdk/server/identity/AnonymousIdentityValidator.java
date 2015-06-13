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

import com.digitalpetri.opcua.sdk.server.Session;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.channel.SecureChannel;
import com.digitalpetri.opcua.stack.core.types.structured.AnonymousIdentityToken;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;

public class AnonymousIdentityValidator extends IdentityValidator {

    public static final Object ANONYMOUS_IDENTITY_OBJECT = new Object();

    @Override
    public Object validateAnonymousToken(AnonymousIdentityToken token, UserTokenPolicy tokenPolicy,
                                         SecureChannel channel, Session session) throws UaException {
        return ANONYMOUS_IDENTITY_OBJECT;
    }

}
