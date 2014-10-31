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

package com.inductiveautomation.opcua.sdk.server.namespaces;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.inductiveautomation.opcua.nodeset.NodeBuilder;
import com.inductiveautomation.opcua.nodeset.attributes.DataTypeNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.MethodNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.ObjectNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.ObjectTypeNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.ReferenceTypeNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.VariableNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.VariableTypeNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.ViewNodeAttributes;
import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.model.UaDataTypeNode;
import com.inductiveautomation.opcua.sdk.server.model.UaMethodNode;
import com.inductiveautomation.opcua.sdk.server.model.UaNode;
import com.inductiveautomation.opcua.sdk.server.model.UaObjectNode;
import com.inductiveautomation.opcua.sdk.server.model.UaObjectTypeNode;
import com.inductiveautomation.opcua.sdk.server.model.UaReferenceTypeNode;
import com.inductiveautomation.opcua.sdk.server.model.UaVariableNode;
import com.inductiveautomation.opcua.sdk.server.model.UaVariableTypeNode;
import com.inductiveautomation.opcua.sdk.server.model.UaViewNode;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.sdk.server.util.UaVariableType;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UaNodeBuilder implements NodeBuilder<UaNode, Reference> {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final Reflections objectReflections =
            new Reflections("com.inductiveautomation.opcua.sdk.server.model.objects");

    private final Reflections variableReflections =
            new Reflections("com.inductiveautomation.opcua.sdk.server.model.variables");

    private final UaNodeManager nodeManager;

    public UaNodeBuilder(UaNodeManager nodeManager) {
        this.nodeManager = nodeManager;
    }

    @Override
    public UaNode buildDataTypeNode(DataTypeNodeAttributes attributes, List<Reference> references) {
        UaDataTypeNode node = new UaDataTypeNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isAbstract()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildMethodNode(MethodNodeAttributes attributes, List<Reference> references) {
        UaMethodNode node = new UaMethodNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isExecutable(),
                attributes.isUserExecutable()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildObjectNode(ObjectNodeAttributes attributes, List<Reference> references, Map<NodeId, UaNode> nodeMap) {
        UaNode typeDefinitionNode = references.stream()
                .filter(Reference.HAS_TYPE_DEFINITION_PREDICATE)
                .findFirst()
                .flatMap(r -> r.getTargetNodeId().local())
                .map(nodeMap::get)
                .orElseThrow(() -> new RuntimeException("no type definition found"));

        String typeDefinitionName = typeDefinitionNode.getBrowseName().getName();

        Set<Class<?>> annotated = objectReflections.getTypesAnnotatedWith(new UaObjectType() {
            @Override
            public String name() {
                return typeDefinitionName;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return UaObjectType.class;
            }
        });

        for (Class<?> clazz : annotated) {
            try {
                Constructor<?> ctor = clazz.getConstructors()[0];

                UaObjectNode n = (UaObjectNode) ctor.newInstance(
                        nodeManager,
                        attributes.getNodeAttributes().getNodeId(),
                        attributes.getNodeAttributes().getBrowseName(),
                        attributes.getNodeAttributes().getDisplayName(),
                        attributes.getNodeAttributes().getDescription(),
                        attributes.getNodeAttributes().getWriteMask(),
                        attributes.getNodeAttributes().getUserWriteMask(),
                        attributes.getEventNotifier()
                );

                n.addReferences(references);

                return n;
            } catch (Throwable t) {
                logger.error("Error constructing class: " + clazz, t);
            }
        }

        UaObjectNode node =  new UaObjectNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.getEventNotifier()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildObjectTypeNode(ObjectTypeNodeAttributes attributes, List<Reference> references) {
        UaObjectTypeNode node = new UaObjectTypeNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isAbstract()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildReferenceTypeNode(ReferenceTypeNodeAttributes attributes, List<Reference> references) {


        UaReferenceTypeNode node = new UaReferenceTypeNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isAbstract(),
                attributes.isSymmetric(),
                attributes.getInverseName()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildVariableNode(VariableNodeAttributes attributes, List<Reference> references, Map<NodeId, UaNode> nodeMap) {
        UaNode typeDefinitionNode = references.stream()
                .filter(Reference.HAS_TYPE_DEFINITION_PREDICATE)
                .findFirst()
                .flatMap(r -> r.getTargetNodeId().local())
                .map(nodeMap::get)
                .orElseThrow(() -> new RuntimeException("no type definition found"));

        String typeDefinitionName = typeDefinitionNode.getBrowseName().getName();

        Set<Class<?>> annotated = variableReflections.getTypesAnnotatedWith(new UaVariableType() {
            @Override
            public String name() {
                return typeDefinitionName;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return UaVariableType.class;
            }
        });

        for (Class<?> clazz : annotated) {
            try {
                Constructor<?> ctor = clazz.getConstructors()[0];

                UaVariableNode n = (UaVariableNode) ctor.newInstance(
                        nodeManager,
                        attributes.getNodeAttributes().getNodeId(),
                        attributes.getNodeAttributes().getBrowseName(),
                        attributes.getNodeAttributes().getDisplayName(),
                        attributes.getNodeAttributes().getDescription(),
                        attributes.getNodeAttributes().getWriteMask(),
                        attributes.getNodeAttributes().getUserWriteMask(),
                        attributes.getValue(),
                        attributes.getDataType(),
                        attributes.getValueRank(),
                        attributes.getArrayDimensions(),
                        attributes.getAccessLevel(),
                        attributes.getUserAccessLevel(),
                        attributes.getMinimumSamplingInterval(),
                        attributes.isHistorizing()
                );

                n.addReferences(references);

                return n;
            } catch (Throwable t) {
                logger.error("Error constructing class: " + clazz, t);
            }
        }

        UaVariableNode node = new UaVariableNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.getValue(),
                attributes.getDataType(),
                attributes.getValueRank(),
                attributes.getArrayDimensions(),
                attributes.getAccessLevel(),
                attributes.getUserAccessLevel(),
                attributes.getMinimumSamplingInterval(),
                attributes.isHistorizing()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildVariableTypeNode(VariableTypeNodeAttributes attributes, List<Reference> references) {
        UaVariableTypeNode node = new UaVariableTypeNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.getValue(),
                attributes.getDataType(),
                attributes.getValueRank(),
                attributes.getArrayDimensions(),
                attributes.isAbstract()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildViewNode(ViewNodeAttributes attributes, List<Reference> references) {
        UaViewNode node = new UaViewNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isContainsNoLoops(),
                attributes.getEventNotifier()
        );

        node.addReferences(references);

        return node;
    }

}
