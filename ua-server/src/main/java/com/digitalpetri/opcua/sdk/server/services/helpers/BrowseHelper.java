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

package com.digitalpetri.opcua.sdk.server.services.helpers;

import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.core.AttributeIds;
import com.digitalpetri.opcua.sdk.core.Reference;
import com.digitalpetri.opcua.sdk.server.DiagnosticsContext;
import com.digitalpetri.opcua.sdk.server.NamespaceManager;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.api.AttributeManager.ReadContext;
import com.digitalpetri.opcua.sdk.server.api.Namespace;
import com.digitalpetri.opcua.sdk.server.services.ServiceAttributes;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequest;
import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.BrowseResultMask;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseDescription;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseNextRequest;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseNextResponse;
import com.digitalpetri.opcua.stack.core.types.structured.BrowseResult;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.ReferenceDescription;
import com.digitalpetri.opcua.stack.core.types.structured.ResponseHeader;
import com.digitalpetri.opcua.stack.core.types.structured.ViewDescription;
import com.digitalpetri.opcua.stack.core.util.NonceUtil;
import com.google.common.collect.Lists;
import com.google.common.primitives.Ints;
import org.jooq.lambda.tuple.Tuple3;

import static com.digitalpetri.opcua.sdk.server.util.FutureUtils.sequence;
import static com.digitalpetri.opcua.sdk.server.util.UaEnumUtil.browseResultMasks;
import static com.digitalpetri.opcua.sdk.server.util.UaEnumUtil.nodeClasses;
import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;
import static java.util.stream.Collectors.toList;

public class BrowseHelper {

    private static final StatusCode BAD_CONTINUATION_POINT_INVALID =
            new StatusCode(StatusCodes.Bad_ContinuationPointInvalid);

    private static final StatusCode BAD_NO_CONTINUATION_POINTS =
            new StatusCode(StatusCodes.Bad_NoContinuationPoints);

    private static final BrowseResult NODE_ID_UNKNOWN_RESULT = new BrowseResult(
            new StatusCode(StatusCodes.Bad_NodeIdUnknown),
            ByteString.NULL_VALUE, new ReferenceDescription[0]);

    public void browseNext(ServiceRequest<BrowseNextRequest, BrowseNextResponse> service) {
        OpcUaServer server = service.attr(ServiceAttributes.SERVER_KEY).get();

        BrowseNextRequest request = service.getRequest();

        if (request.getContinuationPoints().length >
                server.getConfig().getLimits().getMaxBrowseContinuationPoints().intValue()) {

            service.setServiceFault(StatusCodes.Bad_TooManyOperations);
        } else {
            server.getExecutorService().execute(new BrowseNext(server, service));
        }
    }

    public static CompletableFuture<BrowseResult> browse(OpcUaServer server,
                                                         ViewDescription view,
                                                         UInteger maxReferencesPerNode,
                                                         BrowseDescription browseDescription) {

        Browse browse = new Browse(
                server,
                maxReferencesPerNode,
                browseDescription);

        server.getExecutorService().execute(browse);

        return browse.getFuture();
    }

    private static class Browse implements Runnable {

        private final CompletableFuture<BrowseResult> future = new CompletableFuture<>();

        private final OpcUaServer server;
        private final UInteger maxReferencesPerNode;
        private final BrowseDescription browseDescription;

        private Browse(OpcUaServer server,
                       UInteger maxReferencesPerNode,
                       BrowseDescription browseDescription) {

            this.browseDescription = browseDescription;
            this.maxReferencesPerNode = maxReferencesPerNode;
            this.server = server;
        }

        public CompletableFuture<BrowseResult> getFuture() {
            return future;
        }

        @Override
        public void run() {
            NamespaceManager namespaceManager = server.getNamespaceManager();
            Namespace namespace = namespaceManager.getNamespace(browseDescription.getNodeId().getNamespaceIndex());

            CompletableFuture<List<Reference>> referencesFuture =
                    namespace.getReferences(browseDescription.getNodeId());

            referencesFuture.whenComplete((references, ex) -> {
                if (references != null) {
                    browse(references).whenComplete((result, ex2) -> {
                       if (result != null) future.complete(result);
                       else future.complete(NODE_ID_UNKNOWN_RESULT);
                    });
                } else {
                    future.complete(NODE_ID_UNKNOWN_RESULT);
                }
            });
        }

        private CompletableFuture<BrowseResult> browse(List<Reference> references) {
            List<CompletableFuture<ReferenceDescription>> fs = references.stream()
                    .filter(this::directionFilter)
                    .filter(this::referenceTypeFilter)
                    .filter(this::nodeClassFilter)
                    .distinct()
                    .map(this::referenceDescription)
                    .collect(toList());

            return sequence(fs).thenApply(referenceDescriptions -> {
                int max = maxReferencesPerNode.longValue() == 0 ?
                        Integer.MAX_VALUE :
                        Ints.saturatedCast(maxReferencesPerNode.longValue());

                return browseResult(referenceDescriptions, max);
            });
        }

        private BrowseResult browseResult(List<ReferenceDescription> references, int max) {
            if (references.size() > max) {
                if (server.getBrowseContinuationPoints().size() >
                        server.getConfig().getLimits().getMaxBrowseContinuationPoints().intValue()) {

                    return new BrowseResult(BAD_NO_CONTINUATION_POINTS, null, new ReferenceDescription[0]);
                } else {
                    List<ReferenceDescription> subList = references.subList(0, max);
                    List<ReferenceDescription> current = Lists.newArrayList(subList);
                    subList.clear();

                    BrowseContinuationPoint c = new BrowseContinuationPoint(references, max);
                    server.getBrowseContinuationPoints().put(c.identifier, c);

                    return new BrowseResult(StatusCode.GOOD, c.identifier, current.toArray(new ReferenceDescription[current.size()]));
                }
            } else {
                return new BrowseResult(StatusCode.GOOD, null, references.toArray(new ReferenceDescription[references.size()]));
            }
        }

        private boolean directionFilter(Reference reference) {
            switch (browseDescription.getBrowseDirection()) {
                case Forward:
                    return reference.isForward();
                case Inverse:
                    return reference.isInverse();
                case Both:
                default:
                    return true;
            }
        }

        private boolean referenceTypeFilter(Reference reference) {
            NodeId referenceTypeId = browseDescription.getReferenceTypeId();

            boolean includeAny = referenceTypeId == null || referenceTypeId.isNull();
            boolean includeSubtypes = browseDescription.getIncludeSubtypes();

            return includeAny || reference.getReferenceTypeId().equals(referenceTypeId) ||
                    (includeSubtypes && reference.subtypeOf(referenceTypeId, server.getReferenceTypes()));
        }

        private boolean nodeClassFilter(Reference reference) {
            long mask = browseDescription.getNodeClassMask().longValue();

            EnumSet<NodeClass> nodeClasses = (mask == 0L) ?
                    EnumSet.allOf(NodeClass.class) : nodeClasses(mask);

            return nodeClasses.contains(reference.getTargetNodeClass());
        }

        private CompletableFuture<ReferenceDescription> referenceDescription(Reference reference) {
            EnumSet<BrowseResultMask> masks = browseResultMasks(browseDescription.getResultMask().longValue());

            ExpandedNodeId targetNodeId = reference.getTargetNodeId();

            NodeId referenceTypeId = masks.contains(BrowseResultMask.ReferenceTypeId) ?
                    reference.getReferenceTypeId() : NodeId.NULL_VALUE;

            return targetNodeId.local().map(nodeId -> {
                CompletableFuture<BrowseAttributes> af = browseAttributes(nodeId, masks);

                return af.thenCombine(getTypeDefinition(nodeId), (as, typeDefinition) ->
                        new ReferenceDescription(
                                referenceTypeId,
                                reference.isForward(),
                                targetNodeId,
                                as.getBrowseName(),
                                as.getDisplayName(),
                                as.getNodeClass(),
                                typeDefinition));
            }).orElse(
                    CompletableFuture.completedFuture(new ReferenceDescription(
                            referenceTypeId, reference.isForward(), targetNodeId,
                            QualifiedName.NULL_VALUE, LocalizedText.NULL_VALUE,
                            NodeClass.Unspecified, ExpandedNodeId.NULL_VALUE))
            );
        }

        private CompletableFuture<BrowseAttributes> browseAttributes(NodeId nodeId, EnumSet<BrowseResultMask> masks) {
            List<ReadValueId> readValueIds = Lists.newArrayList();

            readValueIds.add(new ReadValueId(nodeId, uint(AttributeIds.BrowseName), null, QualifiedName.NULL_VALUE));
            readValueIds.add(new ReadValueId(nodeId, uint(AttributeIds.DisplayName), null, QualifiedName.NULL_VALUE));
            readValueIds.add(new ReadValueId(nodeId, uint(AttributeIds.NodeClass), null, QualifiedName.NULL_VALUE));

            CompletableFuture<List<DataValue>> future = new CompletableFuture<>();

            ReadContext context = new ReadContext(
                    server, null, future,
                    new DiagnosticsContext<>());

            server.getNamespaceManager().getNamespace(nodeId.getNamespaceIndex()).read(
                    context, 0.0, TimestampsToReturn.Neither, readValueIds);

            return future.thenApply(values -> {
                QualifiedName browseName = QualifiedName.NULL_VALUE;
                LocalizedText displayName = LocalizedText.NULL_VALUE;
                NodeClass nodeClass = NodeClass.Unspecified;

                if (masks.contains(BrowseResultMask.BrowseName)) {
                    DataValue value0 = values.get(0);
                    if (value0.getStatusCode().isGood()) {
                        browseName = (QualifiedName) value0.getValue().getValue();
                    }
                }

                if (masks.contains(BrowseResultMask.DisplayName)) {
                    DataValue value1 = values.get(1);
                    if (value1.getStatusCode().isGood()) {
                        displayName = (LocalizedText) value1.getValue().getValue();
                    }
                }

                if (masks.contains(BrowseResultMask.NodeClass)) {
                    DataValue value2 = values.get(2);
                    if (value2.getStatusCode().isGood()) {
                        nodeClass = (NodeClass) value2.getValue().getValue();
                    }
                }

                return new BrowseAttributes(browseName, displayName, nodeClass);
            });
        }

        private CompletableFuture<ExpandedNodeId> getTypeDefinition(NodeId nodeId) {
            Namespace namespace = server.getNamespaceManager().getNamespace(nodeId.getNamespaceIndex());

            return namespace.getReferences(nodeId).thenApply(references ->
                    references.stream()
                            .filter(r -> Identifiers.HasTypeDefinition.equals(r.getReferenceTypeId()))
                            .findFirst()
                            .map(Reference::getTargetNodeId)
                            .orElse(ExpandedNodeId.NULL_VALUE));
        }

    }

    private class BrowseNext implements Runnable {

        private final OpcUaServer server;
        private final ServiceRequest<BrowseNextRequest, BrowseNextResponse> service;

        private BrowseNext(OpcUaServer server,
                           ServiceRequest<BrowseNextRequest, BrowseNextResponse> service) {

            this.server = server;
            this.service = service;
        }

        @Override
        public void run() {
            BrowseNextRequest request = service.getRequest();

            List<BrowseResult> results = Lists.newArrayList();

            ByteString[] cs = request.getContinuationPoints() != null ?
                    request.getContinuationPoints() : new ByteString[0];

            for (ByteString bs : cs) {
                if (request.getReleaseContinuationPoints()) {
                    results.add(release(bs));
                } else {
                    results.add(references(bs));
                }
            }

            ResponseHeader header = service.createResponseHeader();
            BrowseNextResponse response = new BrowseNextResponse(
                    header, results.toArray(new BrowseResult[results.size()]), new DiagnosticInfo[0]);

            service.setResponse(response);
        }

        private BrowseResult release(ByteString bs) {
            BrowseContinuationPoint c = server.getBrowseContinuationPoints().remove(bs);

            return c != null ?
                    new BrowseResult(StatusCode.GOOD, null, null) :
                    new BrowseResult(BAD_CONTINUATION_POINT_INVALID, null, null);
        }

        private BrowseResult references(ByteString bs) {
            BrowseContinuationPoint c = server.getBrowseContinuationPoints().remove(bs);

            if (c != null) {
                int max = c.max;
                List<ReferenceDescription> references = c.references;

                if (references.size() > max) {
                    List<ReferenceDescription> subList = references.subList(0, max);
                    List<ReferenceDescription> current = Lists.newArrayList(subList);
                    subList.clear();

                    server.getBrowseContinuationPoints().put(c.identifier, c);

                    return new BrowseResult(
                            StatusCode.GOOD,
                            c.identifier,
                            current.toArray(new ReferenceDescription[current.size()]));
                } else {
                    return new BrowseResult(
                            StatusCode.GOOD,
                            null,
                            references.toArray(new ReferenceDescription[references.size()]));
                }
            } else {
                return new BrowseResult(BAD_CONTINUATION_POINT_INVALID, null, null);
            }
        }

    }

    public static class BrowseContinuationPoint {

        private final List<ReferenceDescription> references;
        private final int max;
        private final ByteString identifier;

        public BrowseContinuationPoint(List<ReferenceDescription> references, int max) {
            this(references, max, generateId());
        }

        public BrowseContinuationPoint(List<ReferenceDescription> references, int max, ByteString identifier) {
            this.references = Collections.synchronizedList(references);
            this.max = max;
            this.identifier = identifier;
        }

        public List<ReferenceDescription> getReferences() {
            return references;
        }

        public int getMax() {
            return max;
        }

        public ByteString getIdentifier() {
            return identifier;
        }

        public static ByteString generateId() {
            return NonceUtil.generateNonce(16);
        }

    }

    private static class BrowseAttributes extends Tuple3<QualifiedName, LocalizedText, NodeClass> {

        private BrowseAttributes(QualifiedName browseName, LocalizedText displayName, NodeClass nodeClass) {
            super(browseName, displayName, nodeClass);
        }

        private QualifiedName getBrowseName() {
            return v1();
        }

        private LocalizedText getDisplayName() {
            return v2();
        }

        private NodeClass getNodeClass() {
            return v3();
        }

    }
}
