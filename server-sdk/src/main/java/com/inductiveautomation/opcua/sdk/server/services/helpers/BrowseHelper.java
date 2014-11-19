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

package com.inductiveautomation.opcua.sdk.server.services.helpers;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.primitives.Ints;
import com.inductiveautomation.opcua.sdk.core.AttributeIds;
import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.server.NamespaceManager;
import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.services.ServiceAttributes;
import com.inductiveautomation.opcua.sdk.server.util.PendingBrowse;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.BrowseResultMask;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseNextRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseNextResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseResult;
import com.inductiveautomation.opcua.stack.core.types.structured.ReferenceDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.ResponseHeader;
import com.inductiveautomation.opcua.stack.core.util.NonceUtil;

import static com.inductiveautomation.opcua.sdk.server.util.FutureUtils.sequence;
import static com.inductiveautomation.opcua.sdk.server.util.UaEnumUtil.browseResultMasks;
import static com.inductiveautomation.opcua.sdk.server.util.UaEnumUtil.nodeClasses;

public class BrowseHelper {

    private static final StatusCode Bad_ContinuationPointInvalid = new StatusCode(StatusCodes.Bad_ContinuationPointInvalid);
    private static final StatusCode Bad_NoContinuationPoints = new StatusCode(StatusCodes.Bad_NoContinuationPoints);

    private static final BrowseResult NodeIdUnknownResult =
            new BrowseResult(new StatusCode(StatusCodes.Bad_NodeIdUnknown), ByteString.NULL_VALUE, new ReferenceDescription[0]);

    private final Map<ByteString, BrowseContinuationPoint> continuations = Maps.newConcurrentMap();

    public void browse(ServiceRequest<BrowseRequest, BrowseResponse> service) {
        OpcUaServer server = service.attr(ServiceAttributes.ServerKey).get();

        BrowseRequest request = service.getRequest();

        if (request.getNodesToBrowse().length > server.getConfig().getLimits().getMaxNodesPerBrowse().intValue()) {
            service.setServiceFault(StatusCodes.Bad_TooManyOperations);
            return;
        }

        List<PendingBrowse> pendingBrowses = Arrays.stream(request.getNodesToBrowse())
                .map(PendingBrowse::new).collect(Collectors.toList());

        pendingBrowses.stream().forEach(pending -> {
            Browse browse = new Browse(
                    pending.getInput(),
                    request.getRequestedMaxReferencesPerNode(),
                    server,
                    pending.getFuture());

            server.getExecutorService().execute(browse);
        });

        List<CompletableFuture<BrowseResult>> futures =
                pendingBrowses.stream().map(PendingBrowse::getFuture).collect(Collectors.toList());

        sequence(futures).thenAccept(resultList -> {
            ResponseHeader header = service.createResponseHeader();
            BrowseResult[] results = resultList.toArray(new BrowseResult[resultList.size()]);

            BrowseResponse response = new BrowseResponse(header, results, new DiagnosticInfo[0]);

            service.setResponse(response);
        });
    }

    public void browseNext(ServiceRequest<BrowseNextRequest, BrowseNextResponse> service) {
        OpcUaServer server = service.attr(ServiceAttributes.ServerKey).get();

        BrowseNextRequest request = service.getRequest();

        if (request.getContinuationPoints().length >
                server.getConfig().getLimits().getMaxBrowseContinuationPoints().intValue()) {

            service.setServiceFault(StatusCodes.Bad_TooManyOperations);
        } else {
            server.getExecutorService().execute(new BrowseNext(service));
        }
    }

    private class Browse implements Runnable {

        private final BrowseDescription description;
        private final UInteger maxReferences;
        private final OpcUaServer server;
        private final CompletableFuture<BrowseResult> future;

        private Browse(BrowseDescription description,
                       UInteger maxReferences,
                       OpcUaServer server,
                       CompletableFuture<BrowseResult> future) {

            this.description = description;
            this.maxReferences = maxReferences;
            this.server = server;
            this.future = future;
        }

        @Override
        public void run() {
            NamespaceManager namespaceManager = server.getNamespaceManager();

            Optional<List<Reference>> references = namespaceManager.getReferences(description.getNodeId());

            BrowseResult result = references.map(this::browse).orElse(NodeIdUnknownResult);

            future.complete(result);
        }

        private BrowseResult browse(List<Reference> references) {
            Stream<Reference> filtered = references.stream()
                    .filter(this::directionFilter)
                    .filter(this::referenceTypeFilter)
                    .filter(this::nodeClassFilter)
                    .distinct();

            List<ReferenceDescription> descriptions =
                    filtered.map(this::referenceDescription).collect(Collectors.toList());


            int max = maxReferences.longValue() == 0 ?
                    Integer.MAX_VALUE :
                    Ints.saturatedCast(maxReferences.longValue());

            return browseResult(descriptions, max);
        }

        private BrowseResult browseResult(List<ReferenceDescription> references, int max) {
            if (references.size() > max) {
                if (continuations.size() >
                        server.getConfig().getLimits().getMaxBrowseContinuationPoints().intValue()) {

                    return new BrowseResult(Bad_NoContinuationPoints, null, new ReferenceDescription[0]);
                } else {
                    List<ReferenceDescription> subList = references.subList(0, max);
                    List<ReferenceDescription> current = Lists.newArrayList(subList);
                    subList.clear();

                    BrowseContinuationPoint c = new BrowseContinuationPoint(references, max);
                    continuations.put(c.identifier, c);

                    return new BrowseResult(StatusCode.GOOD, c.identifier, current.toArray(new ReferenceDescription[current.size()]));
                }
            } else {
                return new BrowseResult(StatusCode.GOOD, null, references.toArray(new ReferenceDescription[references.size()]));
            }
        }

        private boolean directionFilter(Reference reference) {
            switch (description.getBrowseDirection()) {
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
            NodeId referenceTypeId = description.getReferenceTypeId();

            boolean includeAny = referenceTypeId == null || referenceTypeId.isNull();
            boolean includeSubtypes = description.getIncludeSubtypes();

            return includeAny || reference.getReferenceTypeId().equals(referenceTypeId) ||
                    (includeSubtypes && reference.subtypeOf(referenceTypeId, server.getNamespaceManager()));
        }

        private boolean nodeClassFilter(Reference reference) {
            long mask = description.getNodeClassMask().longValue();

            EnumSet<NodeClass> nodeClasses = (mask == 0L) ?
                    EnumSet.allOf(NodeClass.class) : nodeClasses(mask);

            return nodeClasses.contains(reference.getTargetNodeClass());
        }

        private ReferenceDescription referenceDescription(Reference reference) {
            NamespaceManager namespaceManager = server.getNamespaceManager();

            EnumSet<BrowseResultMask> resultMaskSet = browseResultMasks(description.getResultMask().longValue());

            ExpandedNodeId targetNodeId = reference.getTargetNodeId();

            Optional<NodeId> referenceTypeId = resultMaskSet.contains(BrowseResultMask.ReferenceTypeId) ?
                    Optional.of(reference.getReferenceTypeId()) : Optional.empty();

            Optional<QualifiedName> browseName = resultMaskSet.contains(BrowseResultMask.BrowseName) ?
                    namespaceManager.getAttribute(targetNodeId, AttributeIds.BrowseName) : Optional.empty();

            Optional<LocalizedText> displayName = resultMaskSet.contains(BrowseResultMask.DisplayName) ?
                    namespaceManager.getAttribute(targetNodeId, AttributeIds.DisplayName) : Optional.empty();

            NodeClass nodeClass = resultMaskSet.contains(BrowseResultMask.NodeClass) ?
                    namespaceManager.<NodeClass>getAttribute(targetNodeId, AttributeIds.NodeClass)
                            .orElse(reference.getTargetNodeClass()) : NodeClass.Unspecified;

            Optional<ExpandedNodeId> typeDefinition = Optional.empty();

            if (resultMaskSet.contains(BrowseResultMask.TypeDefinition)) {
                Optional<List<Reference>> references = namespaceManager.getReferences(targetNodeId);

                Optional<Reference> typeReference = references.map(Collection::stream)
                        .orElse(Stream.empty())
                        .filter(r -> r.getReferenceTypeId().equals(Identifiers.HasTypeDefinition))
                        .findFirst();

                typeDefinition = typeReference.map(Reference::getTargetNodeId);
            }

            return new ReferenceDescription(
                    referenceTypeId.orElse(NodeId.NULL_VALUE),
                    reference.isForward(),
                    targetNodeId,
                    browseName.orElse(QualifiedName.NULL_VALUE),
                    displayName.orElse(LocalizedText.NULL_VALUE),
                    nodeClass,
                    typeDefinition.orElse(ExpandedNodeId.NULL_VALUE));
        }

    }

    private class BrowseNext implements Runnable {

        private final ServiceRequest<BrowseNextRequest, BrowseNextResponse> service;

        private BrowseNext(ServiceRequest<BrowseNextRequest, BrowseNextResponse> service) {
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
            BrowseContinuationPoint c = continuations.remove(bs);

            return c != null ?
                    new BrowseResult(StatusCode.GOOD, null, null) :
                    new BrowseResult(Bad_ContinuationPointInvalid, null, null);
        }

        private BrowseResult references(ByteString bs) {
            BrowseContinuationPoint c = continuations.remove(bs);

            if (c != null) {
                int max = c.max;
                List<ReferenceDescription> references = c.references;

                if (references.size() > max) {
                    List<ReferenceDescription> subList = references.subList(0, max);
                    List<ReferenceDescription> current = Lists.newArrayList(subList);
                    subList.clear();

                    continuations.put(c.identifier, c);

                    return new BrowseResult(
                            StatusCode.GOOD,
                            c.identifier,
                            current.toArray(new ReferenceDescription[current.size()])
                    );
                } else {
                    return new BrowseResult(
                            StatusCode.GOOD,
                            null,
                            references.toArray(new ReferenceDescription[references.size()])
                    );
                }
            } else {
                return new BrowseResult(Bad_ContinuationPointInvalid, null, null);
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

        private static ByteString generateId() {
            return NonceUtil.generateNonce(16);
        }
    }
}
