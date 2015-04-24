/*
 * Copyright 2015
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

import java.util.Optional;
import javax.annotation.Nullable;

import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;

public enum ReferenceType implements com.digitalpetri.opcua.sdk.core.api.ReferenceType {

    REFERENCES(Identifiers.References, "References", null, true, true, null),
    HIERARCHICAL_REFERENCES(Identifiers.HierarchicalReferences, "HierarchicalReferences", null, false, true, REFERENCES),
    NON_HIERARCHICAL_REFERENCES(Identifiers.NonHierarchicalReferences, "NonHierarchicalReferences", null, true, true, REFERENCES),
    HAS_CHILD(Identifiers.HasChild, "HasChild", null, false, true, HIERARCHICAL_REFERENCES),
    AGGREGATES(Identifiers.Aggregates, "Aggregates", null, false, true, HAS_CHILD),
    ORGANIZES(Identifiers.Organizes, "Organizes", "OrganizedBy", false, false, HIERARCHICAL_REFERENCES),
    HAS_COMPONENT(Identifiers.HasComponent, "HasComponent", "ComponentOf", false, false, AGGREGATES),
    HAS_ORDERED_COMPONENT(Identifiers.HasOrderedComponent, "HasOrderedComponent", "OrderedComponentOf", false, false, HAS_COMPONENT),
    HAS_PROPERTY(Identifiers.HasProperty, "HasProperty", "PropertyOf", false, false, AGGREGATES),
    HAS_SUBTYPE(Identifiers.HasSubtype, "HasSubtype", "SubtypeOf", false, false, HAS_CHILD),
    HAS_MODELLING_RULE(Identifiers.HasModellingRule, "HasModellingRule", "ModellingRuleOf", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_TYPE_DEFINITION(Identifiers.HasTypeDefinition, "HasTypeDefinition", "TypeDefinitionOf", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_ENCODING(Identifiers.HasEncoding, "HasEncoding", "EncodingOf", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_DESCRIPTION(Identifiers.HasDescription, "HasDescription", "DescriptionOf", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_EVENT_SOURCE(Identifiers.HasEventSource, "HasEventSource", "EventSourceOf", false, false, HIERARCHICAL_REFERENCES),
    HAS_NOTIFIER(Identifiers.HasNotifier, "HasNotifier", "NotifierOf", false, false, HAS_EVENT_SOURCE),
    GENERATES_EVENT(Identifiers.GeneratesEvent, "GeneratesEvent", "GeneratedBy", false, false, NON_HIERARCHICAL_REFERENCES),
    ALWAYS_GENERATES_EVENT(Identifiers.AlwaysGeneratesEvent, "AlwaysGeneratesEvent", "AlwaysGeneratedBy", false, false, GENERATES_EVENT),
    FROM_STATE(Identifiers.FromState, "FromState", "ToTransition", false, false, NON_HIERARCHICAL_REFERENCES),
    TO_STATE(Identifiers.ToState, "ToState", "FromTransition", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_CAUSE(Identifiers.HasCause, "HasCause", "MayBeCausedBy", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_EFFECT(Identifiers.HasEffect, "HasEffect", "MayBeEffectedBy", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_SUB_STATE_MACHINE(Identifiers.HasSubStateMachine, "HasSubStateMachine", "SubStateMachineOf", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_TRUE_SUB_STATE(Identifiers.HasTrueSubState, "HasTrueSubState", "IsTrueSubStateOf", false, false, NON_HIERARCHICAL_REFERENCES),
    HAS_FALSE_SUB_STATE(Identifiers.HasFalseSubState, "HasFalseSubState", "IsFalseSubStateOf", false, false, NON_HIERARCHICAL_REFERENCES);


    private final NodeId nodeId;
    private final QualifiedName browseName;
    private final String inverseName;
    private final boolean symmetric;
    private final boolean isAbstract;
    private final ReferenceType superType;

    ReferenceType(NodeId nodeId,
                  String browseName,
                  @Nullable String inverseName,
                  boolean symmetric,
                  boolean isAbstract,
                  @Nullable ReferenceType superType) {

        this.nodeId = nodeId;
        this.browseName = new QualifiedName(0, browseName);
        this.inverseName = inverseName;
        this.symmetric = symmetric;
        this.isAbstract = isAbstract;
        this.superType = superType;
    }

    @Override
    public NodeId getNodeId() {
        return nodeId;
    }

    @Override
    public QualifiedName getBrowseName() {
        return browseName;
    }

    @Override
    public Optional<String> getInverseName() {
        return Optional.ofNullable(inverseName);
    }

    @Override
    public boolean isSymmetric() {
        return symmetric;
    }

    @Override
    public boolean isAbstract() {
        return isAbstract;
    }

    @Override
    public Optional<com.digitalpetri.opcua.sdk.core.api.ReferenceType> getSuperType() {
        return Optional.ofNullable(superType);
    }

}
