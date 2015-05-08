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

package com.digitalpetri.opcua.sdk.client.api.config;

import java.util.function.Supplier;

import com.digitalpetri.opcua.sdk.client.api.identity.IdentityProvider;
import com.digitalpetri.opcua.stack.client.config.UaTcpStackClientConfig;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface OpcUaClientConfig extends UaTcpStackClientConfig {

    /**
     * @return a {@link Supplier} for the session name.
     */
    Supplier<String> getSessionName();

    /**
     * @return the session timeout, in milliseconds, to request.
     */
    UInteger getSessionTimeout();

    /**
     * @return the timeout, in milliseconds, before failing a request due to timeout.
     */
    UInteger getRequestTimeout();

    /**
     * @return the maximum size for a response from the server.
     */
    UInteger getMaxResponseMessageSize();

    /**
     * @return an {@link IdentityProvider} to use when activating a session.
     */
    IdentityProvider getIdentityProvider();

    static OpcUaClientConfigBuilder builder() {
        return new OpcUaClientConfigBuilder();
    }

}
