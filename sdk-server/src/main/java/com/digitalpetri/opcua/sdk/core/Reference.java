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

package com.digitalpetri.opcua.sdk.core;

import java.util.Map;
import java.util.function.Predicate;

import com.digitalpetri.opcua.sdk.core.api.ReferenceType;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;
import org.slf4j.LoggerFactory;

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

    public boolean subtypeOf(NodeId superTypeId, Map<NodeId, ReferenceType> referenceTypes) {
        return subtypeOf(referenceTypeId, superTypeId, referenceTypes);
    }

    private boolean subtypeOf(NodeId typeId, NodeId superTypeId, Map<NodeId, ReferenceType> referenceTypes) {
        ReferenceType referenceType = referenceTypes.get(typeId);

        if (referenceType == null) {
            LoggerFactory.getLogger(getClass()).warn("Unknown reference type: {}", typeId);
            return false;
        }

        return referenceType.getSuperType().map(superType -> {
            NodeId id = superType.getNodeId();

            return id.equals(superTypeId) || subtypeOf(id, superTypeId, referenceTypes);
        }).orElse(false);
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

    public static final Predicate<Reference> HAS_COMPONENT_PREDICATE =
            (reference) -> reference.isForward() && Identifiers.HasComponent.equals(reference.getReferenceTypeId());

    public static final Predicate<Reference> HAS_PROPERTY_PREDICATE =
            (reference) -> reference.isForward() && Identifiers.HasProperty.equals(reference.getReferenceTypeId());

    public static final Predicate<Reference> HAS_TYPE_DEFINITION_PREDICATE =
            (reference) -> reference.isForward() && Identifiers.HasTypeDefinition.equals(reference.getReferenceTypeId());

    public static final Predicate<Reference> HAS_EVENT_SOURCE_PREDICATE =
            (reference) -> reference.isForward() && Identifiers.HasEventSource.equals(reference.getReferenceTypeId());

    public static final Predicate<Reference> HAS_NOTIFIER_PREDICATE =
            (reference) -> reference.isForward() && Identifiers.HasNotifier.equals(reference.getReferenceTypeId());

    public static final Predicate<Reference> ORGANIZES_PREDICATE =
            (reference) -> reference.isForward() && Identifiers.Organizes.equals(reference.getReferenceTypeId());

    public static final Predicate<Reference> HAS_DESCRIPTION_PREDICATE =
            (reference) -> reference.isForward() && Identifiers.HasDescription.equals(reference.getReferenceTypeId());

    public static final Predicate<Reference> HAS_MODELLING_RULE_PREDICATE =
            (reference) -> reference.isForward() && Identifiers.HasModellingRule.equals(reference.getReferenceTypeId());

    public static final Predicate<Reference> ALWAYS_GENERATES_EVENT_PREDICATE =
            (reference) -> reference.isForward() && Identifiers.AlwaysGeneratesEvent.equals(reference.getReferenceTypeId());

}
