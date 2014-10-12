/*
 * OPC-UA SDK
 *
 * Copyright (C) 2014 Kevin Herron
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

package com.inductiveautomation.opcua.sdk.server.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.EnumSet;
import java.util.List;

import com.inductiveautomation.opcua.sdk.server.objects.ServerCapabilities;
import com.inductiveautomation.opcua.stack.core.Stack;
import com.inductiveautomation.opcua.stack.core.security.SecurityPolicy;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.enumerated.UserTokenType;
import com.inductiveautomation.opcua.stack.core.types.structured.BuildInfo;
import com.inductiveautomation.opcua.stack.core.types.structured.UserTokenPolicy;
import com.google.common.collect.Lists;

public interface OpcUaServerConfig {

    public static final UserTokenPolicy UserTokenPolicy_Anonymous = new UserTokenPolicy(
            "anonymous",
            UserTokenType.Anonymous,
            null, null, null
    );

    default List<String> getBindAddresses() {
        return Lists.newArrayList("0.0.0.0");
    }

    default EnumSet<SecurityPolicy> getSecurityPolicies() {
        return EnumSet.allOf(SecurityPolicy.class);
    }

    default int getBindPort() {
        return Stack.DEFAULT_PORT;
    }

    default BuildInfo getBuildInfo() {
        return new BuildInfo("", "", "", "", "", DateTime.MinValue);
    }

    default String getHostname() {
        try {
            return InetAddress.getLocalHost().getCanonicalHostName();
        } catch (UnknownHostException e) {
            return "localhost";
        }
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
        return Lists.newArrayList(UserTokenPolicy_Anonymous);
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

    default KeyPair getKeyPair() {
        return null;
    }

    default Certificate getCertificate() {
        return null;
    }

    ServerCapabilities getServerCapabilities();

}
