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
import java.time.Duration;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Lists;
import com.inductiveautomation.opcua.sdk.server.services.AttributeServices;
import com.inductiveautomation.opcua.sdk.server.services.MethodServices;
import com.inductiveautomation.opcua.sdk.server.services.MonitoredItemServices;
import com.inductiveautomation.opcua.sdk.server.services.NodeManagementServices;
import com.inductiveautomation.opcua.sdk.server.services.QueryServices;
import com.inductiveautomation.opcua.sdk.server.services.SubscriptionServices;
import com.inductiveautomation.opcua.sdk.server.services.ViewServices;
import com.inductiveautomation.opcua.sdk.server.subscriptions.SubscriptionManager;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.application.services.NodeManagementServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.application.services.SessionServiceSet;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.structured.ActivateSessionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ActivateSessionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CancelRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CancelResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CloseSessionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CloseSessionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSessionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSessionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.UserIdentityToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class Session implements SessionServiceSet {

    private static final ScheduledExecutorService ScheduledExecutor = Executors.newSingleThreadScheduledExecutor();

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final List<LifecycleListener> listeners = Lists.newCopyOnWriteArrayList();

    private final SubscriptionManager subscriptionManager;

    private volatile long secureChannelId;

    private volatile UserIdentityToken identityToken;
    private volatile ByteString clientCertificateBytes;

    private volatile long lastActivity = System.nanoTime();
    private volatile ScheduledFuture<?> checkTimeoutFuture;

    private final AttributeServices attributeServices;
    private final MethodServices methodServices;
    private final MonitoredItemServices monitoredItemServices;
    private final NodeManagementServiceSet nodeManagementServices;
    private final QueryServices queryServices;
    private final SubscriptionServices subscriptionServices;
    private final ViewServices viewServices;

    private final NodeId sessionId;
    private final Duration sessionTimeout;

    public Session(OpcUaServer server,
                   NodeId sessionId,
                   Duration sessionTimeout,
                   long secureChannelId) {

        this.sessionId = sessionId;
        this.sessionTimeout = sessionTimeout;
        this.secureChannelId = secureChannelId;

        subscriptionManager = new SubscriptionManager(this, server);

        attributeServices = new AttributeServices();
        methodServices = new MethodServices();
        monitoredItemServices = new MonitoredItemServices(subscriptionManager);
        nodeManagementServices = new NodeManagementServices();
        queryServices = new QueryServices();
        subscriptionServices = new SubscriptionServices(subscriptionManager);
        viewServices = new ViewServices();

        checkTimeoutFuture = ScheduledExecutor.schedule(
                this::checkTimeout, sessionTimeout.toNanos(), TimeUnit.NANOSECONDS);
    }

    public long getSecureChannelId() {
        return secureChannelId;
    }

    @Nullable
    public UserIdentityToken getIdentityToken() {
        return identityToken;
    }

    @Nullable
    public ByteString getClientCertificateBytes() {
        return clientCertificateBytes;
    }

    public void setSecureChannelId(long secureChannelId) {
        this.secureChannelId = secureChannelId;
    }

    public void setIdentityToken(UserIdentityToken identityToken) {
        this.identityToken = identityToken;
    }

    public void setClientCertificateBytes(ByteString clientCertificateBytes) {
        this.clientCertificateBytes = clientCertificateBytes;
    }

    void addLifecycleListener(LifecycleListener listener) {
        listeners.add(listener);
    }

    void updateLastActivity() {
        lastActivity = System.nanoTime();
    }

    private void checkTimeout() {
        long elapsed = Math.abs(System.nanoTime() - lastActivity);

        if (elapsed > sessionTimeout.toNanos()) {
            logger.debug("Session id={} timed out.", sessionId);

            subscriptionManager.sessionClosed(true);

            listeners.forEach(listener -> listener.onSessionClosed(this, true));
        } else {
            long remaining = sessionTimeout.toNanos() - elapsed;
            logger.trace("Session id={} timeout scheduled for +{}s.",
                    sessionId, Duration.ofNanos(remaining).getSeconds());

            checkTimeoutFuture = ScheduledExecutor.schedule(this::checkTimeout, remaining, TimeUnit.NANOSECONDS);
        }
    }

    public NodeId getSessionId() {
        return sessionId;
    }

    public AttributeServices getAttributeServices() {
        return attributeServices;
    }

    public MethodServices getMethodServices() {
        return methodServices;
    }

    public MonitoredItemServices getMonitoredItemServices() {
        return monitoredItemServices;
    }

    public NodeManagementServiceSet getNodeManagementServices() {
        return nodeManagementServices;
    }

    public QueryServices getQueryServices() {
        return queryServices;
    }

    public SubscriptionServices getSubscriptionServices() {
        return subscriptionServices;
    }

    public ViewServices getViewServices() {
        return viewServices;
    }

    public SubscriptionManager getSubscriptionManager() {
        return subscriptionManager;
    }

    //region Session Services
    @Override
    public void onCreateSession(ServiceRequest<CreateSessionRequest, CreateSessionResponse> req) throws UaException {
        throw new UaException(StatusCodes.Bad_InternalError);
    }

    @Override
    public void onActivateSession(ServiceRequest<ActivateSessionRequest, ActivateSessionResponse> req) throws UaException {
        throw new UaException(StatusCodes.Bad_InternalError);
    }

    @Override
    public void onCloseSession(ServiceRequest<CloseSessionRequest, CloseSessionResponse> serviceRequest) throws UaException {
        if (checkTimeoutFuture != null) {
            checkTimeoutFuture.cancel(false);
        }

        boolean deleteSubscriptions = serviceRequest.getRequest().getDeleteSubscriptions();

        subscriptionManager.sessionClosed(deleteSubscriptions);

        listeners.forEach(listener -> listener.onSessionClosed(this, deleteSubscriptions));

        serviceRequest.setResponse(new CloseSessionResponse(serviceRequest.createResponseHeader()));
    }

    @Override
    public void onCancel(ServiceRequest<CancelRequest, CancelResponse> serviceRequest) throws UaException {
        serviceRequest.setResponse(new CancelResponse(serviceRequest.createResponseHeader(), uint(0)));
    }
    //endregion

    public static interface LifecycleListener {
        void onSessionClosed(Session session, boolean subscriptionsDeleted);
    }
}
