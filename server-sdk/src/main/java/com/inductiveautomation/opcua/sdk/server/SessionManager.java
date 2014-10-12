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

import java.math.RoundingMode;
import java.nio.ByteBuffer;
import java.security.KeyPair;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.math.DoubleMath;
import com.google.common.primitives.Bytes;
import com.inductiveautomation.opcua.sdk.server.services.ServiceAttributes;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.UaRuntimeException;
import com.inductiveautomation.opcua.stack.core.application.services.AttributeServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.MethodServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.MonitoredItemServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.NodeManagementServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.QueryServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.application.services.SessionServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.SubscriptionServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.ViewServiceSet;
import com.inductiveautomation.opcua.stack.core.channel.ServerSecureChannel;
import com.inductiveautomation.opcua.stack.core.security.SecurityAlgorithm;
import com.inductiveautomation.opcua.stack.core.security.SecurityPolicy;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.structured.ActivateSessionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ActivateSessionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.AddNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.AddNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.AddReferencesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.AddReferencesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseNextRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseNextResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CallRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CallResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CancelRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CancelResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CloseSessionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CloseSessionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSessionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSessionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteReferencesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteReferencesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteSubscriptionsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteSubscriptionsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.EndpointDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryReadRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryReadResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryUpdateRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.HistoryUpdateResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifyMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifySubscriptionRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifySubscriptionResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryFirstRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryFirstResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryNextRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.QueryNextResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RegisterNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RegisterNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.RepublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.RepublishResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetPublishingModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SignatureData;
import com.inductiveautomation.opcua.stack.core.types.structured.SignedSoftwareCertificate;
import com.inductiveautomation.opcua.stack.core.types.structured.TransferSubscriptionsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TransferSubscriptionsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.UnregisterNodesRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.UnregisterNodesResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteResponse;
import com.inductiveautomation.opcua.stack.core.util.NonceUtil;
import com.inductiveautomation.opcua.stack.core.util.SignatureUtil;

public class SessionManager implements
        AttributeServiceSet,
        MethodServiceSet,
        MonitoredItemServiceSet,
        NodeManagementServiceSet,
        QueryServiceSet,
        SessionServiceSet,
        SubscriptionServiceSet,
        ViewServiceSet {

    private final Map<NodeId, Session> createdSessions = Maps.newConcurrentMap();
    private final Map<NodeId, Session> activeSessions = Maps.newConcurrentMap();
    private final Map<NodeId, Session> inactiveSessions = Maps.newConcurrentMap();

    private final OpcUaServer server;

    public SessionManager(OpcUaServer server) {
        this.server = server;
    }

    public List<Session> getActiveSessions() {
        return Lists.newArrayList(activeSessions.values());
    }

    public List<Session> getInactiveSessions() {
        return Lists.newArrayList(inactiveSessions.values());
    }

    private Session session(ServiceRequest<?, ?> service) throws UaException {
        NodeId authToken = service.getRequest().getRequestHeader().getAuthenticationToken();

        Session session = activeSessions.get(authToken);

        if (session == null) {
            session = createdSessions.get(authToken);

            if (session == null) {
                throw new UaException(StatusCodes.Bad_SessionIdInvalid);
            } else {
                throw new UaException(StatusCodes.Bad_SessionNotActivated);
            }
        }

        session.updateLastActivity();

        service.attr(ServiceAttributes.ServerKey).set(server);
        service.attr(ServiceAttributes.SessionKey).set(session);

        return session;
    }

    //region Session Services
    @Override
    public void onCreateSession(ServiceRequest<CreateSessionRequest, CreateSessionResponse> serviceRequest) throws UaException {
        CreateSessionRequest request = serviceRequest.getRequest();

        ByteString serverNonce = NonceUtil.generateNonce(32);
        NodeId authenticationToken = new NodeId(0, NonceUtil.generateNonce(32));
        long maxRequestMessageSize = serviceRequest.getServer().getChannelConfig().getMaxMessageSize();
        double revisedSessionTimeout = Math.max(5000, Math.min(30000, request.getRequestedSessionTimeout()));

        ServerSecureChannel secureChannel = serviceRequest.getSecureChannel();
        ByteString serverCertificate = secureChannel.getLocalCertificateBytes();
        SignedSoftwareCertificate[] serverSoftwareCertificates = server.getSoftwareCertificates();
        EndpointDescription[] serverEndpoints = server.getEndpointDescriptions();

        ByteString clientNonce = request.getClientNonce();
        ByteString clientCertificate = request.getClientCertificate();

        SecurityPolicy securityPolicy = secureChannel.getSecurityPolicy();
        SignatureData serverSignature = getServerSignature(
                clientNonce,
                clientCertificate,
                securityPolicy,
                secureChannel.getKeyPair()
        );

        NodeId sessionId = new NodeId(1, "Session:" + UUID.randomUUID());
        Duration sessionTimeout = Duration.ofMillis(DoubleMath.roundToLong(revisedSessionTimeout, RoundingMode.UP));
        Session session = new Session(server, sessionId, sessionTimeout);
        createdSessions.put(authenticationToken, session);

        session.addLifecycleListener((s, remove) -> {
            createdSessions.remove(s.getSessionId());
            activeSessions.remove(s.getSessionId());
        });

        CreateSessionResponse response = new CreateSessionResponse(
                serviceRequest.createResponseHeader(),
                sessionId,
                authenticationToken,
                revisedSessionTimeout,
                serverNonce,
                serverCertificate,
                serverEndpoints,
                serverSoftwareCertificates,
                serverSignature,
                maxRequestMessageSize
        );


        serviceRequest.setResponse(response);
    }

    @Override
    public void onActivateSession(ServiceRequest<ActivateSessionRequest, ActivateSessionResponse> serviceRequest) throws UaException {
        ActivateSessionRequest request = serviceRequest.getRequest();

        NodeId authToken = request.getRequestHeader().getAuthenticationToken();
        SignedSoftwareCertificate[] clientSoftwareCertificates = request.getClientSoftwareCertificates();

        Session session = createdSessions.remove(authToken);

        if (session == null) {
            ActivateSessionResponse response = new ActivateSessionResponse(
                    serviceRequest.createResponseHeader(StatusCodes.Bad_SessionIdInvalid),
                    NonceUtil.generateNonce(32),
                    new StatusCode[0],
                    new DiagnosticInfo[0]
            );

            serviceRequest.setResponse(response);
        } else {
            activeSessions.put(authToken, session);

            StatusCode[] results = new StatusCode[clientSoftwareCertificates.length];
            Arrays.fill(results, StatusCode.Good);

            ActivateSessionResponse response = new ActivateSessionResponse(
                    serviceRequest.createResponseHeader(),
                    NonceUtil.generateNonce(32),
                    results,
                    new DiagnosticInfo[0]
            );

            serviceRequest.setResponse(response);
        }
    }

    @Override
    public void onCloseSession(ServiceRequest<CloseSessionRequest, CloseSessionResponse> service) throws UaException {
        session(service).onCloseSession(service);

        activeSessions.remove(service.getRequest().getRequestHeader().getAuthenticationToken());
    }

    @Override
    public void onCancel(ServiceRequest<CancelRequest, CancelResponse> service) throws UaException {
        session(service).onCancel(service);
    }

    private SignatureData getServerSignature(ByteString clientNonce,
                                                       ByteString clientCertificate,
                                                       SecurityPolicy securityPolicy,
                                                       KeyPair keyPair) throws UaException {

        if (clientNonce.isNull() || clientCertificate.isNull() || keyPair == null) {
            return new SignatureData(null, null);
        }

        try {
            SecurityAlgorithm algorithm = securityPolicy.getAsymmetricSignatureAlgorithm();

            byte[] data = Bytes.concat(clientCertificate.bytes(), clientNonce.bytes());

            byte[] signature = SignatureUtil.sign(
                    algorithm,
                    keyPair.getPrivate(),
                    ByteBuffer.wrap(data)
            );

            return new SignatureData(algorithm.getUri(), ByteString.of(signature));
        } catch (UaRuntimeException e) {
            throw new UaException(StatusCodes.Bad_SecurityChecksFailed);
        }
    }
    //endregion

    //region Attribute Services
    @Override
    public void onRead(ServiceRequest<ReadRequest, ReadResponse> service) throws UaException {
        Session session = session(service);

        session.getAttributeServices().onRead(service);
    }

    @Override
    public void onWrite(ServiceRequest<WriteRequest, WriteResponse> service) throws UaException {
        Session session = session(service);

        session.getAttributeServices().onWrite(service);
    }

    @Override
    public void onHistoryRead(ServiceRequest<HistoryReadRequest, HistoryReadResponse> service) throws UaException {
        Session session = session(service);

        session.getAttributeServices().onHistoryRead(service);
    }

    @Override
    public void onHistoryUpdate(ServiceRequest<HistoryUpdateRequest, HistoryUpdateResponse> service) throws UaException {
        Session session = session(service);

        session.getAttributeServices().onHistoryUpdate(service);
    }
    //endregion

    //region View Services
    @Override
    public void onBrowse(ServiceRequest<BrowseRequest, BrowseResponse> service) throws UaException {
        Session session = session(service);

        session.getViewServices().onBrowse(service);
    }

    @Override
    public void onBrowseNext(ServiceRequest<BrowseNextRequest, BrowseNextResponse> service) throws UaException {
        Session session = session(service);

        session.getViewServices().onBrowseNext(service);
    }

    @Override
    public void onTranslateBrowsePaths(ServiceRequest<TranslateBrowsePathsToNodeIdsRequest, TranslateBrowsePathsToNodeIdsResponse> service) throws UaException {
        Session session = session(service);

        session.getViewServices().onTranslateBrowsePaths(service);
    }

    @Override
    public void onRegisterNodes(ServiceRequest<RegisterNodesRequest, RegisterNodesResponse> service) throws UaException {
        Session session = session(service);

        session.getViewServices().onRegisterNodes(service);
    }

    @Override
    public void onUnregisterNodes(ServiceRequest<UnregisterNodesRequest, UnregisterNodesResponse> service) throws UaException {
        Session session = session(service);

        session.getViewServices().onUnregisterNodes(service);
    }
    //endregion

    //region NodeManagement Services
    @Override
    public void onAddNodes(ServiceRequest<AddNodesRequest, AddNodesResponse> service) throws UaException {
        Session session = session(service);

        session.getNodeManagementServices().onAddNodes(service);
    }

    @Override
    public void onAddReferences(ServiceRequest<AddReferencesRequest, AddReferencesResponse> service) throws UaException {
        Session session = session(service);

        session.getNodeManagementServices().onAddReferences(service);
    }

    @Override
    public void onDeleteNodes(ServiceRequest<DeleteNodesRequest, DeleteNodesResponse> service) throws UaException {
        Session session = session(service);

        session.getNodeManagementServices().onDeleteNodes(service);
    }

    @Override
    public void onDeleteReferences(ServiceRequest<DeleteReferencesRequest, DeleteReferencesResponse> service) throws UaException {
        Session session = session(service);

        session.getNodeManagementServices().onDeleteReferences(service);
    }
    //endregion

    //region Subscription Services
    @Override
    public void onCreateSubscription(ServiceRequest<CreateSubscriptionRequest, CreateSubscriptionResponse> service) throws UaException {
        Session session = session(service);

        session.getSubscriptionServices().onCreateSubscription(service);
    }

    @Override
    public void onModifySubscription(ServiceRequest<ModifySubscriptionRequest, ModifySubscriptionResponse> service) throws UaException {
        Session session = session(service);

        session.getSubscriptionServices().onModifySubscription(service);
    }

    @Override
    public void onSetPublishingMode(ServiceRequest<SetPublishingModeRequest, SetPublishingModeResponse> service) throws UaException {
        Session session = session(service);

        session.getSubscriptionServices().onSetPublishingMode(service);
    }

    @Override
    public void onPublish(ServiceRequest<PublishRequest, PublishResponse> service) throws UaException {
        Session session = session(service);

        session.getSubscriptionServices().onPublish(service);
    }

    @Override
    public void onRepublish(ServiceRequest<RepublishRequest, RepublishResponse> service) throws UaException {
        Session session = session(service);

        session.getSubscriptionServices().onRepublish(service);
    }

    @Override
    public void onTransferSubscriptions(ServiceRequest<TransferSubscriptionsRequest, TransferSubscriptionsResponse> service) throws UaException {
        Session session = session(service);

        session.getSubscriptionServices().onTransferSubscriptions(service);
    }

    @Override
    public void onDeleteSubscriptions(ServiceRequest<DeleteSubscriptionsRequest, DeleteSubscriptionsResponse> service) throws UaException {
        Session session = session(service);

        session.getSubscriptionServices().onDeleteSubscriptions(service);
    }
    //endregion

    //region MonitoredItem Services
    @Override
    public void onCreateMonitoredItems(ServiceRequest<CreateMonitoredItemsRequest, CreateMonitoredItemsResponse> service) throws UaException {
        Session session = session(service);

        session.getMonitoredItemServices().onCreateMonitoredItems(service);
    }

    @Override
    public void onModifyMonitoredItems(ServiceRequest<ModifyMonitoredItemsRequest, ModifyMonitoredItemsResponse> service) throws UaException {
        Session session = session(service);

        session.getMonitoredItemServices().onModifyMonitoredItems(service);
    }

    @Override
    public void onSetMonitoringMode(ServiceRequest<SetMonitoringModeRequest, SetMonitoringModeResponse> service) throws UaException {
        Session session = session(service);

        session.getMonitoredItemServices().onSetMonitoringMode(service);
    }

    @Override
    public void onSetTriggering(ServiceRequest<SetTriggeringRequest, SetTriggeringResponse> service) throws UaException {
        Session session = session(service);

        session.getMonitoredItemServices().onSetTriggering(service);
    }

    @Override
    public void onDeleteMonitoredItems(ServiceRequest<DeleteMonitoredItemsRequest, DeleteMonitoredItemsResponse> service) throws UaException {
        Session session = session(service);

        session.getMonitoredItemServices().onDeleteMonitoredItems(service);
    }
    //endregion

    //region Method Services
    @Override
    public void onCall(ServiceRequest<CallRequest, CallResponse> service) throws UaException {
        Session session = session(service);

        session.getMethodServices().onCall(service);
    }
    //endregion

    //region Query Services
    @Override
    public void onQueryFirst(ServiceRequest<QueryFirstRequest, QueryFirstResponse> service) throws UaException {
        Session session = session(service);

        session.getQueryServices().onQueryFirst(service);
    }

    @Override
    public void onQueryNext(ServiceRequest<QueryNextRequest, QueryNextResponse> service) throws UaException {
        Session session = session(service);

        session.getQueryServices().onQueryNext(service);
    }
    //endregion

}
