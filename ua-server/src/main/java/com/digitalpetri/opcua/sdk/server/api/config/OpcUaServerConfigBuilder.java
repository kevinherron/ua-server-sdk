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

package com.digitalpetri.opcua.sdk.server.api.config;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.EnumSet;
import java.util.List;

import com.digitalpetri.opcua.sdk.server.identity.AnonymousIdentityValidator;
import com.digitalpetri.opcua.sdk.server.identity.IdentityValidator;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.application.CertificateManager;
import com.digitalpetri.opcua.stack.core.application.DirectoryCertificateManager;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.structured.BuildInfo;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;

import static com.google.common.collect.Lists.newArrayList;

public class OpcUaServerConfigBuilder {

    private String hostname = getHostname();
    private List<String> bindAddresses = newArrayList("0.0.0.0");
    private int bindPort = Stack.DEFAULT_PORT;
    private String serverName = "";
    private EnumSet<SecurityPolicy> securityPolicies = EnumSet.of(SecurityPolicy.None);
    private List<UserTokenPolicy> userTokenPolicies = newArrayList(OpcUaServerConfig.USER_TOKEN_POLICY_ANONYMOUS);
    private IdentityValidator identityValidator = new AnonymousIdentityValidator();
    private CertificateManager certificateManager;

    private LocalizedText applicationName = LocalizedText.english("un-configured application name");
    private String applicationUri = "urn:digitalpetri:opcua:sdk:un-configured";
    private String productUri = "urn:digitalpetri:opcua:sdk:un-configured";

    private BuildInfo buildInfo = new BuildInfo(
            "", "", "", "", "", DateTime.MIN_VALUE);

    private OpcUaServerConfigLimits limits =
            new OpcUaServerConfigLimits() {};

    public OpcUaServerConfigBuilder setHostname(String hostname) {
        this.hostname = hostname;
        return this;
    }

    public OpcUaServerConfigBuilder setBindAddresses(List<String> bindAddresses) {
        this.bindAddresses = bindAddresses;
        return this;
    }

    public OpcUaServerConfigBuilder setBindPort(int bindPort) {
        this.bindPort = bindPort;
        return this;
    }

    public OpcUaServerConfigBuilder setServerName(String serverName) {
        this.serverName = serverName;
        return this;
    }

    public OpcUaServerConfigBuilder setSecurityPolicies(EnumSet<SecurityPolicy> securityPolicies) {
        this.securityPolicies = securityPolicies;
        return this;
    }

    public OpcUaServerConfigBuilder setUserTokenPolicies(List<UserTokenPolicy> userTokenPolicies) {
        this.userTokenPolicies = userTokenPolicies;
        return this;
    }

    public OpcUaServerConfigBuilder setIdentityValidator(IdentityValidator identityValidator) {
        this.identityValidator = identityValidator;
        return this;
    }

    public OpcUaServerConfigBuilder setCertificateManager(CertificateManager certificateManager) {
        this.certificateManager = certificateManager;
        return this;
    }

    public OpcUaServerConfigBuilder setApplicationName(LocalizedText applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public OpcUaServerConfigBuilder setApplicationUri(String applicationUri) {
        this.applicationUri = applicationUri;
        return this;
    }

    public OpcUaServerConfigBuilder setProductUri(String productUri) {
        this.productUri = productUri;
        return this;
    }

    public OpcUaServerConfigBuilder setBuildInfo(BuildInfo buildInfo) {
        this.buildInfo = buildInfo;
        return this;
    }

    public OpcUaServerConfigBuilder setLimits(OpcUaServerConfigLimits limits) {
        this.limits = limits;
        return this;
    }

    public OpcUaServerConfig build() {
        if (certificateManager == null) {
            certificateManager = new DirectoryCertificateManager(new File("./security"));
        }

        return new OpcUaServerConfigImpl(
                hostname,
                bindAddresses,
                bindPort,
                serverName,
                securityPolicies,
                userTokenPolicies,
                identityValidator,
                certificateManager,
                applicationName,
                applicationUri,
                productUri,
                buildInfo,
                limits);
    }

    private String getHostname() {
        try {
            return System.getProperty("hostname",
                    InetAddress.getLocalHost().getCanonicalHostName());
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    public static final class OpcUaServerConfigImpl implements OpcUaServerConfig {

        private final String hostname;
        private final List<String> bindAddresses;
        private final int bindPort;
        private final String serverName;
        private final EnumSet<SecurityPolicy> securityPolicies;
        private final List<UserTokenPolicy> userTokenPolicies;
        private final IdentityValidator identityValidator;
        private final CertificateManager certificateManager;

        private final LocalizedText applicationName;
        private final String applicationUri;
        private final String productUri;

        private final BuildInfo buildInfo;

        private final OpcUaServerConfigLimits limits;

        public OpcUaServerConfigImpl(String hostname,
                                     List<String> bindAddresses,
                                     int bindPort,
                                     String serverName,
                                     EnumSet<SecurityPolicy> securityPolicies,
                                     List<UserTokenPolicy> userTokenPolicies,
                                     IdentityValidator identityValidator,
                                     CertificateManager certificateManager,
                                     LocalizedText applicationName,
                                     String applicationUri,
                                     String productUri,
                                     BuildInfo buildInfo,
                                     OpcUaServerConfigLimits limits) {

            this.hostname = hostname;
            this.bindAddresses = bindAddresses;
            this.bindPort = bindPort;
            this.serverName = serverName;
            this.securityPolicies = securityPolicies;
            this.userTokenPolicies = userTokenPolicies;
            this.identityValidator = identityValidator;
            this.certificateManager = certificateManager;
            this.applicationName = applicationName;
            this.applicationUri = applicationUri;
            this.productUri = productUri;
            this.buildInfo = buildInfo;
            this.limits = limits;
        }

        @Override
        public String getHostname() {
            return hostname;
        }

        @Override
        public List<String> getBindAddresses() {
            return bindAddresses;
        }

        @Override
        public int getBindPort() {
            return bindPort;
        }

        @Override
        public String getServerName() {
            return serverName;
        }

        @Override
        public EnumSet<SecurityPolicy> getSecurityPolicies() {
            return securityPolicies;
        }

        @Override
        public List<UserTokenPolicy> getUserTokenPolicies() {
            return userTokenPolicies;
        }

        @Override
        public IdentityValidator getIdentityValidator() {
            return identityValidator;
        }

        @Override
        public CertificateManager getCertificateManager() {
            return certificateManager;
        }

        @Override
        public LocalizedText getApplicationName() {
            return applicationName;
        }

        @Override
        public String getApplicationUri() {
            return applicationUri;
        }

        @Override
        public String getProductUri() {
            return productUri;
        }

        @Override
        public BuildInfo getBuildInfo() {
            return buildInfo;
        }

        @Override
        public OpcUaServerConfigLimits getLimits() {
            return limits;
        }

    }

}
