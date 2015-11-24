package com.digitalpetri.opcua.sdk.server.model;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.digitalpetri.opcua.sdk.core.Reference;
import com.digitalpetri.opcua.sdk.core.nodes.VariableNode;
import com.digitalpetri.opcua.sdk.core.nodes.VariableTypeNode;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.UaNodeManager.UaNodeSource;
import com.digitalpetri.opcua.sdk.server.model.variables.AnalogItemNode;
import com.digitalpetri.opcua.sdk.server.util.StreamUtil;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaRuntimeException;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.structured.Range;
import org.reflections.Reflections;

public class VariableNodeFactory {

    public static void main(String[] args) {
        VariableNodeFactory factory = new VariableNodeFactory(null);

        AnalogItemNode node = factory.create(
                new NodeId(2, "MyAnalogItem"),
                Identifiers.AnalogItemType,
                AnalogItemNode.class
        );

        node.setDataType(Identifiers.Float);
        node.setValue(new DataValue(new Variant(3.14f)));
        node.setEURange(new Range((double) Float.MIN_VALUE, (double) Float.MAX_VALUE));
    }

    private final UaNodeSource nodeManager;

    public VariableNodeFactory(UaNodeSource nodeManager) {
        this.nodeManager = nodeManager;
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

        List<UaPropertyNode> propertyDeclarations = typeDefinitionNode.getReferences().stream()
                .filter(Reference.HAS_PROPERTY_PREDICATE)
                .map(r -> nodeManager.getNode(r.getTargetNodeId()))
                .flatMap(StreamUtil::opt2stream)
                .map(UaPropertyNode.class::cast)
                .collect(Collectors.toList());

        for (UaPropertyNode declaration : propertyDeclarations) {
            UaVariableTypeNode typeDefinition = (UaVariableTypeNode) declaration.getTypeDefinitionNode();
            UaVariableNode instance = create(node.getNodeId(), typeDefinition.getNodeId());

            node.addComponent(instance);
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
            UaVariableNode instance = create(node.getNodeId(), typeDefinition.getNodeId());

            node.addComponent(instance);
            nodeManager.addNode(instance);
        }

        return clazz.cast(node);
    }

    private UaVariableNode instanceFromTypeDefinition(NodeId nodeId, UaVariableTypeNode typeDefinitionNode) {
        QualifiedName browseName = typeDefinitionNode.getBrowseName();

        Reflections reflections = new Reflections("com.digitalpetri.opcua.server.model");

        Set<Class<?>> classes = reflections.getTypesAnnotatedWith(
                variableAnnotation(browseName.toParseableString()));

        Class<?> clazz = classes.stream().findFirst().orElseThrow(() ->
                new UaRuntimeException(
                        StatusCodes.Bad_TypeDefinitionInvalid,
                        "unknown variable type: " + browseName));

        try {
            Class[] UA_VARIABLE_NODE_CTOR_PARAMS = {
                    UaNamespace.class,
                    NodeId.class,
                    VariableTypeNode.class
            };

            Constructor<?> ctor = clazz.getDeclaredConstructor(UA_VARIABLE_NODE_CTOR_PARAMS);

            Object[] initArgs = {
                    typeDefinitionNode.getNamespace(),
                    nodeId,
                    typeDefinitionNode
            };

            return (UaVariableNode) ctor.newInstance(initArgs);
        } catch (Exception e) {
            throw new UaRuntimeException(e);
        }
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
