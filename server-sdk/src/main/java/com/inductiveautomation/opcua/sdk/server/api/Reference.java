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

package com.inductiveautomation.opcua.sdk.server.api;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import com.inductiveautomation.opcua.sdk.server.NamespaceManager;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public class Reference {

    private final NodeId sourceNodeId;
    private final NodeId referenceTypeId;
    private final ExpandedNodeId targetNodeId;
    private final NodeClass targetNodeClass;
    private final boolean forward;

    public Reference(NodeId sourceNodeId,
                     NodeId referenceTypeId,
                     ExpandedNodeId targetNodeId,
                     NodeClass targetNodeClass,
                     boolean forward) {

        this.sourceNodeId = sourceNodeId;
        this.referenceTypeId = referenceTypeId;
        this.targetNodeId = targetNodeId;
        this.targetNodeClass = targetNodeClass;
        this.forward = forward;
    }

    public NodeId getSourceNodeId() {
        return sourceNodeId;
    }

    public NodeId getReferenceTypeId() {
        return referenceTypeId;
    }

    public ExpandedNodeId getTargetNodeId() {
        return targetNodeId;
    }

    public NodeClass getTargetNodeClass() {
        return targetNodeClass;
    }

    public boolean isForward() {
        return forward;
    }

    public boolean isInverse() {
        return !isForward();
    }

    public boolean subtypeOf(NodeId superTypeId, NamespaceManager namespaceManager) {
        return checkType(superTypeId, new ExpandedNodeId(getReferenceTypeId()), namespaceManager);
    }

    private boolean checkType(NodeId superTypeId, ExpandedNodeId typeId, NamespaceManager namespaceManager) {
        List<Reference> references = namespaceManager.getNode(typeId)
                .flatMap(n -> namespaceManager.getReferences(n.getNodeId()))
                .orElse(Collections.emptyList());

        Optional<ExpandedNodeId> parentTypeId = references.stream()
                .filter(r -> r.isInverse() && r.getReferenceTypeId().equals(Identifiers.HasSubtype))
                .findFirst()
                .map(r -> r.targetNodeId);

        return parentTypeId.map(id -> superTypeId.expanded().equals(id) || checkType(superTypeId, id, namespaceManager)).orElse(false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Reference reference = (Reference) o;

        return forward == reference.forward &&
                referenceTypeId.equals(reference.referenceTypeId) &&
                sourceNodeId.equals(reference.sourceNodeId) &&
                targetNodeClass == reference.targetNodeClass &&
                targetNodeId.equals(reference.targetNodeId);
    }

    @Override
    public int hashCode() {
        int result = sourceNodeId.hashCode();
        result = 31 * result + referenceTypeId.hashCode();
        result = 31 * result + targetNodeId.hashCode();
        result = 31 * result + targetNodeClass.hashCode();
        result = 31 * result + (forward ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Reference{" +
                "sourceNodeId=" + sourceNodeId +
                ", referenceTypeId=" + referenceTypeId +
                ", targetNodeId=" + targetNodeId +
                ", targetNodeClass=" + targetNodeClass +
                ", forward=" + forward +
                '}';
    }


}
