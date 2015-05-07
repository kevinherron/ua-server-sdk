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

package com.digitalpetri.opcua.sdk.server.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.EnumSet;
import java.util.List;

import com.digitalpetri.opcua.sdk.server.identity.AnonymousIdentityValidator;
import com.digitalpetri.opcua.sdk.server.identity.IdentityValidator;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.application.CertificateManager;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.enumerated.UserTokenType;
import com.digitalpetri.opcua.stack.core.types.structured.BuildInfo;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;
import com.google.common.collect.Lists;

public interface OpcUaServerConfig {

    UserTokenPolicy USER_TOKEN_POLICY_ANONYMOUS = new UserTokenPolicy(
            "anonymous",
            UserTokenType.Anonymous,
            null, null, null);

    UserTokenPolicy USER_TOKEN_POLICY_USERNAME = new UserTokenPolicy(
            "username",
            UserTokenType.UserName,
            null, null, null);

    CertificateManager getCertificateManager();

    EnumSet<SecurityPolicy> getSecurityPolicies();

    default String getHostname() {
        try {
            return System.getProperty("hostname", InetAddress.getLocalHost().getCanonicalHostName());
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    default List<String> getBindAddresses() {
        return Lists.newArrayList("0.0.0.0");
    }

    default int getBindPort() {
        return Stack.DEFAULT_PORT;
    }

    /**
     * The server name to use when building endpoint URLs: "opc.tcp://{hostname}:{port}/{serverName}.".
     * <p>
     * If empty, endpoint URLs will be of the format "opc.tcp://{hostname}:{port}".
     *
     * @return The server name to use when building endpoint URLs.
     */
    default String getServerName() {
        return "";
    }

    default List<UserTokenPolicy> getUserTokenPolicies() {
        return Lists.newArrayList(USER_TOKEN_POLICY_ANONYMOUS);
    }

    default IdentityValidator getIdentityValidator() {
        return new AnonymousIdentityValidator();
    }

    default LocalizedText getApplicationName() {
        return LocalizedText.english("un-configured application name");
    }

    default String getApplicationUri() {
        return "un-configured application URI";
    }

    default String getProductUri() {
        return "un-configured product URI";
    }

    default BuildInfo getBuildInfo() {
        return new BuildInfo("", "", "", "", "", DateTime.MIN_VALUE);
    }

    default OpcUaServerConfigLimits getLimits() {
        return new OpcUaServerConfigLimits() {
        };
    }

}
