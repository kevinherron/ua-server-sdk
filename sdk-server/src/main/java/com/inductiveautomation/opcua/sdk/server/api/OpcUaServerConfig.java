/*
 * Copyright 2014 Inductive Automation
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

package com.inductiveautomation.opcua.sdk.server.api;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.EnumSet;
import java.util.List;

import com.google.common.collect.Lists;
import com.inductiveautomation.opcua.sdk.server.identity.AnonymousIdentityValidator;
import com.inductiveautomation.opcua.sdk.server.identity.IdentityValidator;
import com.inductiveautomation.opcua.stack.core.Stack;
import com.inductiveautomation.opcua.stack.core.application.CertificateManager;
import com.inductiveautomation.opcua.stack.core.security.SecurityPolicy;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.enumerated.UserTokenType;
import com.inductiveautomation.opcua.stack.core.types.structured.BuildInfo;
import com.inductiveautomation.opcua.stack.core.types.structured.UserTokenPolicy;

public interface OpcUaServerConfig {

    public static final UserTokenPolicy USER_TOKEN_POLICY_ANONYMOUS = new UserTokenPolicy(
            "anonymous",
            UserTokenType.Anonymous,
            null, null, null
    );

    public static final UserTokenPolicy USER_TOKEN_POLICY_USERNAME = new UserTokenPolicy(
            "username",
            UserTokenType.UserName,
            null, null, null
    );

    CertificateManager getCertificateManager();

    EnumSet<SecurityPolicy> getSecurityPolicies();

    default String getHostname() {
        try {
            String hostname = System.getProperty("hostname");
            return (hostname != null) ? hostname : InetAddress.getLocalHost().getCanonicalHostName();
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
        return new OpcUaServerConfigLimits() {};
    }

}
