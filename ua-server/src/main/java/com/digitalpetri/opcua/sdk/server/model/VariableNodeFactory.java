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

package com.digitalpetri.opcua.sdk.server.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.digitalpetri.opcua.sdk.core.Reference;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableTypeNode;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.util.StreamUtil;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaRuntimeException;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;
import org.reflections.Reflections;

public class VariableNodeFactory {

    private final Reflections reflections = new Reflections("com.digitalpetri.opcua.sdk.server.model");

    private final UaNodeManager nodeManager;

    public VariableNodeFactory(UaNodeManager nodeManager) {
        this.nodeManager = nodeManager;
    }

    public UaVariableNode create(
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            NodeId typeDefinitionId) {

        UaVariableNode variableNode = create(nodeId, typeDefinitionId);

        variableNode.setBrowseName(browseName);
        variableNode.setDisplayName(displayName);

        return variableNode;
    }

    public <T extends VariableNode> T create(
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            NodeId typeDefinitionId,
            Class<T> clazz) {

        T variableNode = create(nodeId, typeDefinitionId, clazz);

        variableNode.setBrowseName(browseName);
        variableNode.setDisplayName(displayName);

        return variableNode;
    }

    public UaVariableNode create(NodeId nodeId, NodeId typeDefinitionId) throws UaRuntimeException {
        return create(nodeId, typeDefinitionId, UaVariableNode.class);
    }

    public <T extends VariableNode> T create(NodeId nodeId, NodeId typeDefinitionId, Class<T> clazz) throws UaRuntimeException {
        UaVariableTypeNode typeDefinitionNode = (UaVariableTypeNode) nodeManager.getNode(typeDefinitionId)
                .orElseThrow(() ->
                        new UaRuntimeException(
                                StatusCodes.Bad_NodeIdUnknown,
                                "unknown type definition: " + typeDefinitionId));

        UaVariableNode node = instanceFromTypeDefinition(nodeId, typeDefinitionNode);
        nodeManager.addNode(node);

        List<UaVariableNode> propertyDeclarations = typeDefinitionNode.getReferences().stream()
                .filter(Reference.HAS_PROPERTY_PREDICATE)
                .distinct()
                .map(r -> nodeManager.getNode(r.getTargetNodeId()))
                .flatMap(StreamUtil::opt2stream)
                .map(UaVariableNode.class::cast)
                .filter(vn ->
                        vn.getReferences().stream().anyMatch(r ->
                                Identifiers.HasModellingRule.equals(r.getReferenceTypeId()) &&
                                        Identifiers.ModellingRule_Mandatory.expanded().equals(r.getTargetNodeId())))
                .collect(Collectors.toList());

        for (UaVariableNode declaration : propertyDeclarations) {
            UaVariableTypeNode typeDefinition = (UaVariableTypeNode) declaration.getTypeDefinitionNode();

            NodeId instanceId = createNodeId(nodeId, declaration.getBrowseName().getName());

            UaVariableNode instance = create(instanceId, typeDefinition.getNodeId());
            instance.setBrowseName(declaration.getBrowseName());
            instance.setDisplayName(declaration.getDisplayName());
            instance.setDescription(declaration.getDescription());
            instance.setWriteMask(declaration.getWriteMask());
            instance.setUserWriteMask(declaration.getUserWriteMask());
            instance.setValue(declaration.getValue());
            instance.setDataType(declaration.getDataType());
            instance.setValueRank(declaration.getValueRank());
            instance.setArrayDimensions(declaration.getArrayDimensions());

            node.addProperty(instance);
            nodeManager.addNode(instance);
        }

        List<UaVariableNode> variableDeclarations = typeDefinitionNode.getReferences().stream()
                .filter(Reference.HAS_COMPONENT_PREDICATE)
                .map(r -> nodeManager.getNode(r.getTargetNodeId()))
                .flatMap(StreamUtil::opt2stream)
                .map(UaVariableNode.class::cast)
                .collect(Collectors.toList());

        for (UaVariableNode declaration : variableDeclarations) {
            UaVariableTypeNode typeDefinition = (UaVariableTypeNode) declaration.getTypeDefinitionNode();

            NodeId instanceId = createNodeId(nodeId, declaration.getBrowseName().getName());

            UaVariableNode instance = create(instanceId, typeDefinition.getNodeId());
            instance.setBrowseName(declaration.getBrowseName());
            instance.setDisplayName(declaration.getDisplayName());
            instance.setDescription(declaration.getDescription());
            instance.setWriteMask(declaration.getWriteMask());
            instance.setUserWriteMask(declaration.getUserWriteMask());
            instance.setValue(declaration.getValue());
            instance.setDataType(declaration.getDataType());
            instance.setValueRank(declaration.getValueRank());
            instance.setArrayDimensions(declaration.getArrayDimensions());

            node.addComponent(instance);
            nodeManager.addNode(instance);
        }

        return clazz.cast(node);
    }

    private UaVariableNode instanceFromTypeDefinition(NodeId nodeId, UaVariableTypeNode typeDefinitionNode) {
        QualifiedName browseName = typeDefinitionNode.getBrowseName();

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(
                variableAnnotation(browseName.toParseableString()));

        Class<?> clazz = classes.stream().findFirst().orElseThrow(() ->
                new UaRuntimeException(
                        StatusCodes.Bad_TypeDefinitionInvalid,
                        "unknown variable type: " + browseName));

        try {
            Class[] UA_VARIABLE_NODE_CTOR_PARAMS = {
                    UaNodeManager.class,
                    NodeId.class,
                    VariableTypeNode.class
            };

            Constructor<?> ctor = clazz.getDeclaredConstructor(UA_VARIABLE_NODE_CTOR_PARAMS);

            Object[] initArgs = {
                    nodeManager,
                    nodeId,
                    typeDefinitionNode
            };

            UaVariableNode variableNode = (UaVariableNode) ctor.newInstance(initArgs);

            variableNode.addReference(new Reference(
                    nodeId,
                    Identifiers.HasTypeDefinition,
                    new ExpandedNodeId(typeDefinitionNode.getNodeId()),
                    NodeClass.VariableType,
                    true
            ));

            return variableNode;
        } catch (Exception e) {
            throw new UaRuntimeException(e);
        }
    }

    private NodeId createNodeId(NodeId nodeId, String toAppend) {
        return new NodeId(nodeId.getNamespaceIndex(), nodeId.getIdentifier() + "." + toAppend);
    }

    private com.digitalpetri.opcua.sdk.server.util.UaVariableNode variableAnnotation(final String typeName) {
        return new com.digitalpetri.opcua.sdk.server.util.UaVariableNode() {
            @Override
            public String typeName() {
                return typeName;
            }

            @Override
            public Class<? extends Annotation> annotationType() {
                return com.digitalpetri.opcua.sdk.server.util.UaVariableNode.class;
            }
        };
    }

}
