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

package com.inductiveautomation.opcua.sdk.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.cert.Certificate;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ExecutorService;

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

    public Optional<KeyPair> getKeyPair(ByteString thumbprint) {
        return server.getKeyPair(thumbprint);
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
