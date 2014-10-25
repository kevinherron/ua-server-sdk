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

package com.inductiveautomation.opcua.sdk.server.nodes.generators;


import java.beans.BeanDescriptor;
import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.google.common.collect.Lists;
import com.inductiveautomation.opcua.sdk.core.AccessLevel;
import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.server.nodes.UaNode;
import com.inductiveautomation.opcua.sdk.server.nodes.UaObjectNode;
import com.inductiveautomation.opcua.sdk.server.nodes.UaVariableNode;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.ubyte;


public class UaObjectNodeGenerator {

    private final Function<String, NodeId> nodeIdFunction;

    public UaObjectNodeGenerator(Function<String, NodeId> nodeIdFunction) {
        this.nodeIdFunction = nodeIdFunction;
    }

    public GeneratedNodes generate(Object bean) throws IntrospectionException {
        Class<?> clazz = bean.getClass();

        BeanInfo beanInfo = Introspector.getBeanInfo(clazz);

        BeanDescriptor beanDescriptor = beanInfo.getBeanDescriptor();
        PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

        List<UaNode> children = Lists.newArrayList();

        UaObjectNode objectNode = UaObjectNode.builder()
                .setNodeId(nodeIdFunction.apply(bean.toString()))
                .setBrowseName(new QualifiedName(0, beanDescriptor.getName()))
                .setDisplayName(LocalizedText.english(beanDescriptor.getDisplayName()))
                .setTypeDefinition(Identifiers.BaseObjectType)
                .build();

        List<Optional<UaVariableNode>> propertyNodes = Arrays.stream(propertyDescriptors)
                .map(pd -> buildPropertyNode(pd, bean))
                .collect(Collectors.toList());

        propertyNodes.stream().forEach(no -> no.ifPresent(n -> {
            Reference reference = new Reference(
                    objectNode.getNodeId(),
                    Identifiers.HasProperty,
                    n.getNodeId().expanded(),
                    NodeClass.Variable,
                    true
            );

            objectNode.addReference(reference);
            children.add(n);
        }));

        return new GeneratedNodes(objectNode, children);
    }

    private Optional<UaVariableNode> buildPropertyNode(PropertyDescriptor propertyDescriptor, Object bean) {
        Optional<NodeId> dataType = getDataType(propertyDescriptor.getPropertyType());

        return dataType.map(dt -> {
            Method readMethod = propertyDescriptor.getReadMethod();
            Method writeMethod = propertyDescriptor.getWriteMethod();

            Supplier<DataValue> supplier = () -> {
                if (readMethod != null) {
                    try {
                        Object v = readMethod.invoke(bean);
                        return new DataValue(new Variant(v));
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        return new DataValue(Variant.NullValue);
                    }
                } else {
                    return new DataValue(Variant.NullValue);
                }
            };

            Consumer<DataValue> consumer = (value) -> {
                if (writeMethod != null) {
                    try {
                        writeMethod.invoke(bean, value.getValue().getValue());
                    } catch (IllegalAccessException | InvocationTargetException ignored) {
                    }
                }
            };

            EnumSet<AccessLevel> accessLevels = EnumSet.noneOf(AccessLevel.class);
            if (readMethod != null) accessLevels.add(AccessLevel.CurrentRead);
            if (writeMethod != null) accessLevels.add(AccessLevel.CurrentWrite);

            UaVariableNode variableNode = UaVariableNode.builder()
                    .setNodeId(nodeIdFunction.apply(bean.toString() + "/" + propertyDescriptor.getName()))
                    .setBrowseName(new QualifiedName(0, propertyDescriptor.getName()))
                    .setDisplayName(LocalizedText.english(propertyDescriptor.getDisplayName()))
                    .setDataType(dt)
                    .setAccessLevel(ubyte(AccessLevel.getMask(accessLevels)))
                    .build();

            variableNode.setValueDelegate(new UaVariableNode.ValueDelegate() {
                @Override
                public void accept(DataValue dataValue) {
                    consumer.accept(dataValue);
                }

                @Override
                public DataValue get() {
                    return supplier.get();
                }
            });

            return variableNode;
        });
    }

    private Optional<NodeId> getDataType(Class<?> propertyType) {
        if (propertyType == Byte.class || propertyType == byte.class) {
            return Optional.of(Identifiers.SByte);
        } else if (propertyType == Short.class || propertyType == short.class) {
            return Optional.of(Identifiers.Int16);
        } else if (propertyType == Integer.class || propertyType == int.class) {
            return Optional.of(Identifiers.Int32);
        } else if (propertyType == Long.class || propertyType == long.class) {
            return Optional.of(Identifiers.Int64);
        } else if (propertyType == String.class) {
            return Optional.of(Identifiers.String);
        } else if (propertyType == Float.class || propertyType == float.class) {
            return Optional.of(Identifiers.Float);
        } else if (propertyType == Double.class || propertyType == double.class) {
            return Optional.of(Identifiers.Double);
        } else {
            return Optional.empty();
        }
    }


    public static class GeneratedNodes {
        private final UaObjectNode root;
        private final List<UaNode> children;

        public GeneratedNodes(UaObjectNode root, List<UaNode> children) {
            this.root = root;
            this.children = children;
        }

        public UaObjectNode getRoot() {
            return root;
        }

        public List<UaNode> getChildren() {
            return children;
        }
    }

}
