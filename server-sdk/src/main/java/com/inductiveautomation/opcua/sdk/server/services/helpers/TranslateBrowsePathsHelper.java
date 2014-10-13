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
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.inductiveautomation.opcua.sdk.server.NamespaceManager;
import com.inductiveautomation.opcua.sdk.server.OpcUaServer;
import com.inductiveautomation.opcua.sdk.server.api.Reference;
import com.inductiveautomation.opcua.sdk.server.services.ServiceAttributes;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowsePath;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowsePathResult;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowsePathTarget;
import com.inductiveautomation.opcua.stack.core.types.structured.RelativePath;
import com.inductiveautomation.opcua.stack.core.types.structured.RelativePathElement;
import com.inductiveautomation.opcua.stack.core.types.structured.ResponseHeader;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsResponse;
import com.google.common.collect.Lists;

import static com.inductiveautomation.opcua.sdk.server.util.ConversionUtil.a;

public class TranslateBrowsePathsHelper {

    private final NamespaceManager namespaceManager;

    public TranslateBrowsePathsHelper(NamespaceManager namespaceManager) {
        this.namespaceManager = namespaceManager;
    }

    public void onTranslateBrowsePaths(
            ServiceRequest<TranslateBrowsePathsToNodeIdsRequest, TranslateBrowsePathsToNodeIdsResponse> service) {

        OpcUaServer server = service.attr(ServiceAttributes.ServerKey).get();

        BrowsePath[] browsePaths = service.getRequest().getBrowsePaths();

        long maxNodesPerTranslate =
                server.getConfig().getServerCapabilities().getOperationLimits().getMaxNodesPerTranslateBrowsePaths();

        if (browsePaths.length > maxNodesPerTranslate) {
            service.setServiceFault(StatusCodes.Bad_TooManyOperations);
            return;
        }

        List<BrowsePathResult> results = Arrays.stream(browsePaths)
                .map(this::translate)
                .collect(Collectors.toList());

        ResponseHeader header = service.createResponseHeader();
        TranslateBrowsePathsToNodeIdsResponse response = new TranslateBrowsePathsToNodeIdsResponse(
                header, a(results, BrowsePathResult.class), new DiagnosticInfo[0]);

        service.setResponse(response);
    }

    private BrowsePathResult translate(BrowsePath browsePath) {
        NodeId startingNode = browsePath.getStartingNode();
        RelativePath relativePath = browsePath.getRelativePath();

        try {
            List<BrowsePathTarget> targets = follow(startingNode, Lists.newArrayList(relativePath.getElements()));

            return new BrowsePathResult(StatusCode.Good, targets.toArray(new BrowsePathTarget[targets.size()]));
        } catch (UaException e) {
            return new BrowsePathResult(e.getStatusCode(), new BrowsePathTarget[0]);
        }
    }

    private List<BrowsePathTarget> follow(NodeId nodeId, List<RelativePathElement> elements) throws UaException {
        if (elements.size() == 1) {
            List<ExpandedNodeId> targets = target(nodeId, elements.get(0));

            if (targets.isEmpty()) throw new UaException(StatusCodes.Bad_NoMatch);

            return targets.stream()
                    .map(n -> new BrowsePathTarget(n, 0L))
                    .collect(Collectors.toList());
        } else {
            RelativePathElement e = elements.remove(0);
            ExpandedNodeId eNext = next(nodeId, e);

            Optional<NodeId> next = namespaceManager.toNodeId(eNext);

            if (next.isPresent()) {
                return follow(next.get(), elements);
            } else {
                return Lists.newArrayList(new BrowsePathTarget(eNext, (long) elements.size()));
            }
        }
    }

    private ExpandedNodeId next(NodeId nodeId, RelativePathElement element) throws UaException {
        NodeId referenceTypeId = element.getReferenceTypeId();
        boolean includeSubtypes = element.getIncludeSubtypes();
        QualifiedName targetName = element.getTargetName();

        List<Reference> references = namespaceManager.getReferences(nodeId)
                .orElseThrow(() -> new UaException(StatusCodes.Bad_NodeIdUnknown));

        return references.stream()
                /* Filter for references of the requested type or its subtype, if allowed... */
                .filter(r -> r.getReferenceTypeId().equals(referenceTypeId) ||
                        (includeSubtypes && r.subtypeOf(referenceTypeId, namespaceManager)))

                /* Filter for reference direction... */
                .filter(r -> r.isInverse() == element.getIsInverse())

                /* Map to target ExpandedNodeId... */
                .map(Reference::getTargetNodeId)

                /* Filter on on targets that match the target name... */
                .filter(id -> namespaceManager.getNode(id).filter(n -> n.getBrowseName().equals(targetName)).isPresent())

                /* If we found one, return it. */
                .findFirst().orElseThrow(() -> new UaException(StatusCodes.Bad_NoMatch));
    }

    private List<ExpandedNodeId> target(NodeId nodeId, RelativePathElement element) throws UaException {
        NodeId referenceTypeId = element.getReferenceTypeId();
        boolean includeSubtypes = element.getIncludeSubtypes();
        QualifiedName targetName = element.getTargetName();

        List<Reference> references = namespaceManager.getReferences(nodeId)
                .orElseThrow(() -> new UaException(StatusCodes.Bad_NodeIdUnknown));

        return references.stream()
                /* Filter for references of the requested type or its subtype, if allowed... */
                .filter(r -> r.getReferenceTypeId().equals(referenceTypeId) ||
                        (includeSubtypes && r.subtypeOf(referenceTypeId, namespaceManager)))

                /* Filter for reference direction... */
                .filter(r -> r.isInverse() == element.getIsInverse())

                /* Map to target ExpandedNodeId... */
                .map(Reference::getTargetNodeId)

                /* Filter on on targets that match the target name... */
                .filter(id -> namespaceManager.getNode(id).filter(n -> matchesTarget(n.getBrowseName(), targetName)).isPresent())

                /* Voila! */
                .collect(Collectors.toList());
    }

    private boolean matchesTarget(QualifiedName browseName, QualifiedName targetName) {
        return targetName == null ||
                targetName.equals(QualifiedName.NullValue) ||
                targetName.equals(browseName);
    }

}
