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

package com.digitalpetri.opcua.sdk.server;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadFactory;

import com.digitalpetri.opcua.sdk.core.ServerTable;
import com.digitalpetri.opcua.sdk.core.api.ReferenceType;
import com.digitalpetri.opcua.sdk.server.api.OpcUaServerConfig;
import com.digitalpetri.opcua.sdk.server.namespaces.OpcUaNamespace;
import com.digitalpetri.opcua.sdk.server.services.helpers.BrowseHelper.BrowseContinuationPoint;
import com.digitalpetri.opcua.sdk.server.subscriptions.Subscription;
import com.digitalpetri.opcua.stack.core.Stack;
import com.digitalpetri.opcua.stack.core.application.UaStackServer;
import com.digitalpetri.opcua.stack.core.application.services.AttributeServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.MethodServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.MonitoredItemServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.NodeManagementServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.SessionServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.SubscriptionServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.ViewServiceSet;
import com.digitalpetri.opcua.stack.core.channel.ChannelConfig;
import com.digitalpetri.opcua.stack.core.channel.ServerSecureChannel;
import com.digitalpetri.opcua.stack.core.security.SecurityPolicy;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MessageSecurityMode;
import com.digitalpetri.opcua.stack.core.types.structured.ApplicationDescription;
import com.digitalpetri.opcua.stack.core.types.structured.EndpointDescription;
import com.digitalpetri.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.digitalpetri.opcua.stack.core.types.structured.UserTokenPolicy;
import com.digitalpetri.opcua.stack.core.util.ManifestUtil;
import com.digitalpetri.opcua.stack.server.tcp.UaTcpServerBuilder;
import com.google.common.collect.Maps;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class OpcUaServer {

    public static final String SDK_VERSION =
            ManifestUtil.read("X-SDK-Version").orElse("dev");

    private static final ThreadFactory THREAD_FACTORY = new ThreadFactoryBuilder()
            .setNameFormat("ua-shared-scheduler-%d")
            .setDaemon(true).build();

    private static final ScheduledExecutorService SHARED_SCHEDULED_EXECUTOR =
            Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors(), THREAD_FACTORY);

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<ByteString, BrowseContinuationPoint> browseContinuationPoints = Maps.newConcurrentMap();

    private final Map<NodeId, ReferenceType> referenceTypes = Maps.newConcurrentMap();

    private final Map<UInteger, Subscription> subscriptions = Maps.newConcurrentMap();

    private final NamespaceManager namespaceManager = new NamespaceManager();
    private final SessionManager sessionManager = new SessionManager(this);
    private final ServerTable serverTable = new ServerTable();

    private final UaStackServer server;
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

        for (ReferenceType referenceType : com.digitalpetri.opcua.sdk.core.ReferenceType.values()) {
            referenceTypes.put(referenceType.getNodeId(), referenceType);
        }

        String hostname = config.getHostname();

        for (String address : config.getBindAddresses()) {
            String bindUrl = String.format("opc.tcp://%s:%d/%s", address, config.getBindPort(), config.getServerName());

            for (SecurityPolicy securityPolicy : config.getSecurityPolicies()) {
                MessageSecurityMode messageSecurity = securityPolicy == SecurityPolicy.None ?
                        MessageSecurityMode.None : MessageSecurityMode.SignAndEncrypt;

                String endpointUrl = endpointUrl(
                        hostname,
                        address,
                        config.getBindPort());

                if (securityPolicy == SecurityPolicy.None) {
                    logger.info("Binding endpoint {} to {} [{}/{}]",
                            endpointUrl, bindUrl, securityPolicy, messageSecurity);

                    server.addEndpoint(endpointUrl, address, null, securityPolicy, messageSecurity);
                } else {
                    for (X509Certificate certificate : config.getCertificateManager().getCertificates()) {
                        logger.info("Binding endpoint {} to {} [{}/{}]",
                                endpointUrl, bindUrl, securityPolicy, messageSecurity);

                        server.addEndpoint(endpointUrl, address, certificate, securityPolicy, messageSecurity);
                    }
                }
            }
        }

        eventBus = new AsyncEventBus("server", server.getExecutorService());

        logger.info("digitalpetri opc-ua stack version: {}", Stack.VERSION);
        logger.info("digitalpetri opc-ua sdk version: {}", SDK_VERSION);
    }

    public void startup() {
        server.startup();
    }

    public void shutdown() {
        server.shutdown();
    }

    private UaStackServer buildServer() {
        UaTcpServerBuilder bootstrap = new UaTcpServerBuilder();

        bootstrap.setServerName(config.getServerName());
        bootstrap.setApplicationName(config.getApplicationName());
        bootstrap.setApplicationUri(config.getApplicationUri());
        bootstrap.setProductUri(config.getProductUri());
        bootstrap.setCertificateManager(config.getCertificateManager());

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

    public Map<UInteger, Subscription> getSubscriptions() {
        return subscriptions;
    }

    public Optional<KeyPair> getKeyPair(ByteString thumbprint) {
        return server.getCertificateManager().getKeyPair(thumbprint);
    }

    public Optional<X509Certificate> getCertificate(ByteString thumbprint) {
        return server.getCertificateManager().getCertificate(thumbprint);
    }

    public ExecutorService getExecutorService() {
        return server.getExecutorService();
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return SHARED_SCHEDULED_EXECUTOR;
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

    public UaStackServer getServer() {
        return server;
    }

    public Map<NodeId, ReferenceType> getReferenceTypes() {
        return referenceTypes;
    }

    public Map<ByteString, BrowseContinuationPoint> getBrowseContinuationPoints() {
        return browseContinuationPoints;
    }
}
