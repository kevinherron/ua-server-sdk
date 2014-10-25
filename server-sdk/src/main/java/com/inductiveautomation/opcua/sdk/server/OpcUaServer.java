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

package com.inductiveautomation.opcua.sdk.server;

import javax.annotation.Nullable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.inductiveautomation.opcua.sdk.core.ServerTable;
import com.inductiveautomation.opcua.sdk.server.api.OpcUaServerConfig;
import com.inductiveautomation.opcua.sdk.server.namespaces.OpcUaNamespace;
import com.inductiveautomation.opcua.stack.core.application.UaServer;
import com.inductiveautomation.opcua.stack.core.application.services.AttributeServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.MethodServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.MonitoredItemServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.NodeManagementServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.SessionServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.SubscriptionServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.ViewServiceSet;
import com.inductiveautomation.opcua.stack.core.channel.ChannelConfig;
import com.inductiveautomation.opcua.stack.core.channel.ServerSecureChannel;
import com.inductiveautomation.opcua.stack.core.security.SecurityPolicy;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.inductiveautomation.opcua.stack.core.types.structured.ApplicationDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.inductiveautomation.opcua.stack.core.types.structured.UserTokenPolicy;
import com.inductiveautomation.opcua.stack.server.tcp.UaTcpServerBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpcUaServer {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final NamespaceManager namespaceManager = new NamespaceManager();
    private final SessionManager sessionManager = new SessionManager(this);
    private final ServerTable serverTable = new ServerTable();

    private final UaServer server;
    private final EventBus eventBus;

    private final OpcUaNamespace uaNamespace;
    private final OpcUaServerConfig config;

    public OpcUaServer(OpcUaServerConfig config) {
        this.config = config;

        server = buildServer();

        server.addServiceSet((AttributeServiceSet) sessionManager);
        server.addServiceSet((MethodServiceSet) sessionManager);
        server.addServiceSet((MonitoredItemServiceSet) sessionManager);
        server.addServiceSet((NodeManagementServiceSet) sessionManager);
        server.addServiceSet((SessionServiceSet) sessionManager);
        server.addServiceSet((SubscriptionServiceSet) sessionManager);
        server.addServiceSet((ViewServiceSet) sessionManager);

        namespaceManager.addNamespace(uaNamespace = new OpcUaNamespace(this));
        serverTable.addUri(server.getApplicationDescription().getApplicationUri());

        for (String address : config.getBindAddresses()) {
            String bindUrl = String.format("opc.tcp://%s:%d/%s", address, config.getBindPort(), config.getServerName());

            String endpointUrl = endpointUrl(config.getHostname(), address, config.getBindPort());

            for (SecurityPolicy securityPolicy : config.getSecurityPolicies()) {
                MessageSecurityMode messageSecurityMode = securityPolicy == SecurityPolicy.None ?
                        MessageSecurityMode.None : MessageSecurityMode.SignAndEncrypt;

                logger.info("Binding endpoint {} to {} [{}/{}]",
                        endpointUrl, bindUrl, securityPolicy, messageSecurityMode);

                server.addEndpoint(endpointUrl, address, securityPolicy, messageSecurityMode);
            }
        }

        eventBus = new AsyncEventBus("server", server.getExecutorService());
    }

    public void startup() {
        server.startup();
    }

    public void shutdown() {
        server.shutdown();
    }

    private UaServer buildServer() {
        UaTcpServerBuilder bootstrap = new UaTcpServerBuilder();

        bootstrap.setApplicationName(config.getApplicationName());
        bootstrap.setApplicationUri(config.getApplicationUri());
        bootstrap.setProductUri(config.getProductUri());
        bootstrap.setKeyPair(config.getKeyPair());
        bootstrap.setCertificate(config.getCertificate());

        config.getUserTokenPolicies().stream().forEach(bootstrap::addUserTokenPolicy);

        return bootstrap.build();
    }

    private String endpointUrl(String hostname, String address, int port) {
        StringBuilder sb = new StringBuilder();

        try {
            InetAddress byName = InetAddress.getByName(address);

            if (byName.isLinkLocalAddress() || byName.isLoopbackAddress()) {
                sb.append(String.format("opc.tcp://%s:%d", byName.getHostName(), port));
                if (!config.getServerName().isEmpty()) {
                    sb.append("/").append(config.getServerName());
                }
                return sb.toString();
            }
        } catch (UnknownHostException ignored) {
        }

        sb.append(String.format("opc.tcp://%s:%d", hostname, port));
        if (!config.getServerName().isEmpty()) {
            sb.append("/").append(config.getServerName());
        }
        return sb.toString();
    }

    public OpcUaServerConfig getConfig() {
        return config;
    }

    public NamespaceManager getNamespaceManager() {
        return namespaceManager;
    }

    public SessionManager getSessionManager() {
        return sessionManager;
    }

    public OpcUaNamespace getUaNamespace() {
        return uaNamespace;
    }

    public ServerTable getServerTable() {
        return serverTable;
    }

    public EventBus getEventBus() {
        return eventBus;
    }

    public Optional<KeyPair> getKeyPair(ByteString thumbprint) {
        return server.getKeyPair(thumbprint);
    }

    @Nullable
    public Certificate getCertificate() {
        return server.getCertificate();
    }

    public Optional<Certificate> getCertificate(ByteString thumbprint) {
        return server.getCertificate(thumbprint);
    }

    public ExecutorService getExecutorService() {
        return server.getExecutorService();
    }

    public ChannelConfig getChannelConfig() {
        return server.getChannelConfig();
    }

    public EndpointDescription[] getEndpointDescriptions() {
        return server.getEndpointDescriptions();
    }

    public List<UserTokenPolicy> getUserTokenPolicies() {
        return server.getUserTokenPolicies();
    }

    public ApplicationDescription getApplicationDescription() {
        return server.getApplicationDescription();
    }

    public SignedSoftwareCertificate[] getSoftwareCertificates() {
        return server.getSoftwareCertificates();
    }

    public void closeSecureChannel(ServerSecureChannel secureChannel) {
        server.closeSecureChannel(secureChannel);
    }

    public UaServer getServer() {
        return server;
    }


}
