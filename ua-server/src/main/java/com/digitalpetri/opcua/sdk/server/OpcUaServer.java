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

package com.digitalpetri.opcua.sdk.server;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.security.KeyPair;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;

import com.digitalpetri.opcua.sdk.core.ServerTable;
import com.digitalpetri.opcua.sdk.server.api.AbstractUaNodeManager;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.api.config.OpcUaServerConfig;
import com.digitalpetri.opcua.sdk.server.namespaces.OpcUaNamespace;
import com.digitalpetri.opcua.sdk.server.namespaces.VendorNamespace;
import com.digitalpetri.opcua.sdk.server.services.helpers.BrowseHelper.BrowseContinuationPoint;
import com.digitalpetri.opcua.sdk.server.subscriptions.Subscription;
import com.digitalpetri.opcua.stack.core.BuiltinReferenceType;
import com.digitalpetri.opcua.stack.core.ReferenceType;
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
import com.digitalpetri.opcua.stack.server.tcp.UaTcpStackServer;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.eventbus.AsyncEventBus;
import com.google.common.eventbus.EventBus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.google.common.collect.Sets.newHashSet;

public class OpcUaServer {

    public static final String SDK_VERSION =
            ManifestUtil.read("X-SDK-Version").orElse("dev");

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Map<ByteString, BrowseContinuationPoint> browseContinuationPoints = Maps.newConcurrentMap();

    private final UaNodeManager nodeManager = new OpcUaNodeManager();

    private final Map<NodeId, ReferenceType> referenceTypes = Maps.newConcurrentMap();

    private final Map<UInteger, Subscription> subscriptions = Maps.newConcurrentMap();

    private final NamespaceManager namespaceManager = new NamespaceManager();
    private final SessionManager sessionManager = new SessionManager(this);
    private final ServerTable serverTable = new ServerTable();

    private final UaStackServer stackServer;
    private final EventBus eventBus;

    private final OpcUaNamespace uaNamespace;
    private final VendorNamespace vendorNamespace;

    private final OpcUaServerConfig config;

    public OpcUaServer(OpcUaServerConfig config) {
        this.config = config;

        stackServer = new UaTcpStackServer(config);

        stackServer.addServiceSet((AttributeServiceSet) sessionManager);
        stackServer.addServiceSet((MethodServiceSet) sessionManager);
        stackServer.addServiceSet((MonitoredItemServiceSet) sessionManager);
        stackServer.addServiceSet((NodeManagementServiceSet) sessionManager);
        stackServer.addServiceSet((SessionServiceSet) sessionManager);
        stackServer.addServiceSet((SubscriptionServiceSet) sessionManager);
        stackServer.addServiceSet((ViewServiceSet) sessionManager);

        namespaceManager.addNamespace(uaNamespace = new OpcUaNamespace(this));

        vendorNamespace = namespaceManager.registerAndAdd(
                config.getApplicationUri(),
                index -> new VendorNamespace(OpcUaServer.this, config.getApplicationUri()));

        serverTable.addUri(stackServer.getApplicationDescription().getApplicationUri());

        for (ReferenceType referenceType : BuiltinReferenceType.values()) {
            referenceTypes.put(referenceType.getNodeId(), referenceType);
        }

        String configuredHostname = config.getHostname();

        for (String bindAddress : config.getBindAddresses()) {
            Set<String> hostnames = Sets.union(
                    newHashSet(configuredHostname),
                    getHostnames(bindAddress));

            for (String hostname : hostnames) {
                for (SecurityPolicy securityPolicy : config.getSecurityPolicies()) {
                    MessageSecurityMode messageSecurity = securityPolicy == SecurityPolicy.None ?
                            MessageSecurityMode.None : MessageSecurityMode.SignAndEncrypt;

                    String endpointUrl = endpointUrl(hostname, config.getBindPort(), config.getServerName());

                    Set<X509Certificate> certificates = config.getCertificateManager().getCertificates();

                    if (certificates.isEmpty() && securityPolicy == SecurityPolicy.None) {
                        logger.info("Binding endpoint {} to {} [{}/{}]",
                                endpointUrl, bindAddress, securityPolicy, messageSecurity);

                        stackServer.addEndpoint(endpointUrl, bindAddress, null, securityPolicy, messageSecurity);
                    } else {
                        for (X509Certificate certificate : certificates) {
                            logger.info("Binding endpoint {} to {} [{}/{}]",
                                    endpointUrl, bindAddress, securityPolicy, messageSecurity);

                            stackServer.addEndpoint(endpointUrl, bindAddress, certificate, securityPolicy, messageSecurity);
                        }
                    }
                }
            }
        }

        eventBus = new AsyncEventBus("server", stackServer.getExecutorService());

        logger.info("digitalpetri opc-ua stack version: {}", Stack.VERSION);
        logger.info("digitalpetri opc-ua sdk version: {}", SDK_VERSION);
    }

    private Set<String> getHostnames(String bindAddress) {
        Set<String> hostnames = newHashSet();

        try {
            InetAddress inetAddress = InetAddress.getByName(bindAddress);

            if (inetAddress.isAnyLocalAddress()) {
                try {
                    Enumeration<NetworkInterface> nis = NetworkInterface.getNetworkInterfaces();

                    for (NetworkInterface ni : Collections.list(nis)) {
                        Collections.list(ni.getInetAddresses()).stream()
                                .filter(ia -> ia instanceof Inet4Address)
                                .forEach(ia -> {
                                    hostnames.add(ia.getHostName());
                                    hostnames.add(ia.getHostAddress());
                                    hostnames.add(ia.getCanonicalHostName());
                                });
                    }
                } catch (SocketException e) {
                    logger.warn("Failed to NetworkInterfaces for bind address: {}", bindAddress, e);
                }
            } else {
                hostnames.add(inetAddress.getHostName());
                hostnames.add(inetAddress.getHostAddress());
                hostnames.add(inetAddress.getCanonicalHostName());
            }
        } catch (UnknownHostException e) {
            logger.warn("Failed to get InetAddress for bind address: {}", bindAddress, e);
        }

        return hostnames;
    }

    public void startup() {
        stackServer.startup();
    }

    public void shutdown() {
        stackServer.shutdown();
    }

    private static String endpointUrl(String hostname, int port, String serverName) {
        StringBuilder sb = new StringBuilder();

        sb.append(String.format("opc.tcp://%s:%d", hostname, port));
        if (!serverName.isEmpty()) {
            sb.append("/").append(serverName);
        }
        return sb.toString();
    }

    public OpcUaServerConfig getConfig() {
        return config;
    }

    public NamespaceManager getNamespaceManager() {
        return namespaceManager;
    }

    public UaNodeManager getNodeManager() {
        return nodeManager;
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
        return stackServer.getCertificateManager().getKeyPair(thumbprint);
    }

    public Optional<X509Certificate> getCertificate(ByteString thumbprint) {
        return stackServer.getCertificateManager().getCertificate(thumbprint);
    }

    public ExecutorService getExecutorService() {
        return stackServer.getExecutorService();
    }

    public ScheduledExecutorService getScheduledExecutorService() {
        return Stack.sharedScheduledExecutor();
    }

    public ChannelConfig getChannelConfig() {
        return stackServer.getChannelConfig();
    }

    public EndpointDescription[] getEndpointDescriptions() {
        return stackServer.getEndpointDescriptions();
    }

    public List<UserTokenPolicy> getUserTokenPolicies() {
        return stackServer.getUserTokenPolicies();
    }

    public ApplicationDescription getApplicationDescription() {
        return stackServer.getApplicationDescription();
    }

    public SignedSoftwareCertificate[] getSoftwareCertificates() {
        return stackServer.getSoftwareCertificates();
    }

    public void closeSecureChannel(ServerSecureChannel secureChannel) {
        stackServer.closeSecureChannel(secureChannel);
    }

    public UaStackServer getServer() {
        return stackServer;
    }

    public Map<NodeId, ReferenceType> getReferenceTypes() {
        return referenceTypes;
    }

    public Map<ByteString, BrowseContinuationPoint> getBrowseContinuationPoints() {
        return browseContinuationPoints;
    }

    private static class OpcUaNodeManager extends AbstractUaNodeManager {}

}
