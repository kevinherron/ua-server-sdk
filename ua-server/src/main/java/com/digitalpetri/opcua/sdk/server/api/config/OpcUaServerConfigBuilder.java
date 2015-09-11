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

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.ExecutorService;

import com.digitalpetri.opcua.sdk.server.identity.AnonymousIdentityValidator;
import com.digitalpetri.opcua.sdk.server.identity.IdentityValidator;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.application.CertificateManager;
import com.digitalpetri.opcua.stack.core.application.CertificateValidator;
import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.structured.BuildInfo;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;
import com.digitalpetri.opcua.stack.server.config.UaTcpStackServerConfig;
import com.digitalpetri.opcua.stack.server.config.UaTcpStackServerConfigBuilder;

import static com.google.common.collect.Lists.newArrayList;

public class OpcUaServerConfigBuilder extends UaTcpStackServerConfigBuilder {

    private String hostname = getDefaultHostname();
    private List<String> bindAddresses = newArrayList("0.0.0.0");
    private int bindPort = Stack.DEFAULT_PORT;
    private EnumSet<SecurityPolicy> securityPolicies = EnumSet.of(SecurityPolicy.None);
    private IdentityValidator identityValidator = new AnonymousIdentityValidator();

    private BuildInfo buildInfo = new BuildInfo(
            "", "", "", "", "", DateTime.MIN_VALUE);

    private OpcUaServerConfigLimits limits =
            new OpcUaServerConfigLimits() {
            };

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

    public OpcUaServerConfigBuilder setSecurityPolicies(EnumSet<SecurityPolicy> securityPolicies) {
        this.securityPolicies = securityPolicies;
        return this;
    }

    public OpcUaServerConfigBuilder setIdentityValidator(IdentityValidator identityValidator) {
        this.identityValidator = identityValidator;
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

    @Override
    public OpcUaServerConfigBuilder setServerName(String serverName) {
        super.setServerName(serverName);
        return this;
    }

    @Override
    public OpcUaServerConfigBuilder setApplicationName(LocalizedText applicationName) {
        super.setApplicationName(applicationName);
        return this;
    }

    @Override
    public OpcUaServerConfigBuilder setApplicationUri(String applicationUri) {
        super.setApplicationUri(applicationUri);
        return this;
    }

    @Override
    public OpcUaServerConfigBuilder setProductUri(String productUri) {
        super.setProductUri(productUri);
        return this;
    }

    @Override
    public OpcUaServerConfigBuilder setCertificateManager(CertificateManager certificateManager) {
        super.setCertificateManager(certificateManager);
        return this;
    }

    @Override
    public OpcUaServerConfigBuilder setCertificateValidator(CertificateValidator certificateValidator) {
        super.setCertificateValidator(certificateValidator);
        return this;
    }

    @Override
    public OpcUaServerConfigBuilder setUserTokenPolicies(List<UserTokenPolicy> userTokenPolicies) {
        super.setUserTokenPolicies(userTokenPolicies);
        return this;
    }

    @Override
    public OpcUaServerConfigBuilder setSoftwareCertificates(List<SignedSoftwareCertificate> softwareCertificates) {
        super.setSoftwareCertificates(softwareCertificates);
        return this;
    }

    @Override
    public OpcUaServerConfigBuilder setExecutor(ExecutorService executor) {
        super.setExecutor(executor);
        return this;
    }

    @Override
    public OpcUaServerConfigBuilder setChannelConfig(ChannelConfig channelConfig) {
        super.setChannelConfig(channelConfig);
        return this;
    }

    public OpcUaServerConfig build() {
        UaTcpStackServerConfig stackServerConfig = super.build();

        return new OpcUaServerConfigImpl(
                stackServerConfig,
                hostname,
                bindAddresses,
                bindPort,
                securityPolicies,
                identityValidator,
                buildInfo,
                limits
        );
    }

    private static String getDefaultHostname() {
        try {
            return System.getProperty("hostname",
                    InetAddress.getLocalHost().getHostName());
        } catch (UnknownHostException e) {
            return "localhost";
        }
    }

    public static final class OpcUaServerConfigImpl implements OpcUaServerConfig {

        private final UaTcpStackServerConfig stackServerConfig;

        private final String hostname;
        private final List<String> bindAddresses;
        private final int bindPort;
        private final EnumSet<SecurityPolicy> securityPolicies;
        private final IdentityValidator identityValidator;
        private final BuildInfo buildInfo;
        private final OpcUaServerConfigLimits limits;

        public OpcUaServerConfigImpl(UaTcpStackServerConfig stackServerConfig,
                                     String hostname,
                                     List<String> bindAddresses,
                                     int bindPort,
                                     EnumSet<SecurityPolicy> securityPolicies,
                                     IdentityValidator identityValidator,
                                     BuildInfo buildInfo,
                                     OpcUaServerConfigLimits limits) {

            this.stackServerConfig = stackServerConfig;

            this.hostname = hostname;
            this.bindAddresses = bindAddresses;
            this.bindPort = bindPort;
            this.securityPolicies = securityPolicies;
            this.identityValidator = identityValidator;
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
        public IdentityValidator getIdentityValidator() {
            return identityValidator;
        }

        @Override
        public BuildInfo getBuildInfo() {
            return buildInfo;
        }

        @Override
        public OpcUaServerConfigLimits getLimits() {
            return limits;
        }

        @Override
        public EnumSet<SecurityPolicy> getSecurityPolicies() {
            return securityPolicies;
        }

        @Override
        public String getServerName() {
            return stackServerConfig.getServerName();
        }

        @Override
        public LocalizedText getApplicationName() {
            return stackServerConfig.getApplicationName();
        }

        @Override
        public String getApplicationUri() {
            return stackServerConfig.getApplicationUri();
        }

        @Override
        public String getProductUri() {
            return stackServerConfig.getProductUri();
        }

        @Override
        public CertificateManager getCertificateManager() {
            return stackServerConfig.getCertificateManager();
        }

        @Override
        public CertificateValidator getCertificateValidator() {
            return stackServerConfig.getCertificateValidator();
        }

        @Override
        public ExecutorService getExecutor() {
            return stackServerConfig.getExecutor();
        }

        @Override
        public List<UserTokenPolicy> getUserTokenPolicies() {
            return stackServerConfig.getUserTokenPolicies();
        }

        @Override
        public List<SignedSoftwareCertificate> getSoftwareCertificates() {
            return stackServerConfig.getSoftwareCertificates();
        }

        @Override
        public ChannelConfig getChannelConfig() {
            return stackServerConfig.getChannelConfig();
        }

    }

}
