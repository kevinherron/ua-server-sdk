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

package com.digitalpetri.opcua.sdk.server.util;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

import com.digitalpetri.opcua.sdk.core.ValueRank;
import com.digitalpetri.opcua.sdk.server.api.MethodInvocationHandler;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.model.UaObjectNode;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.Argument;
import com.digitalpetri.opcua.stack.core.types.structured.CallMethodRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CallMethodResult;
import com.digitalpetri.opcua.stack.core.util.TypeUtil;
import com.google.common.collect.Lists;

import static com.digitalpetri.opcua.stack.core.util.ConversionUtil.a;

public class AnnotationBasedInvocationHandler implements MethodInvocationHandler {

    private final Method annotatedMethod;

    private final UaNamespace nodeManager;
    private final List<Argument> inputArguments;
    private final List<Argument> outputArguments;
    private final Object annotatedObject;

    public AnnotationBasedInvocationHandler(UaNamespace nodeManager,
                                            Argument[] inputArguments,
                                            Argument[] outputArguments,
                                            Object annotatedObject) {

        this(nodeManager, Lists.newArrayList(inputArguments), Lists.newArrayList(outputArguments), annotatedObject);
    }

    public AnnotationBasedInvocationHandler(UaNamespace nodeManager,
                                            List<Argument> inputArguments,
                                            List<Argument> outputArguments,
                                            Object annotatedObject) {

        this.nodeManager = nodeManager;
        this.inputArguments = inputArguments;
        this.outputArguments = outputArguments;
        this.annotatedObject = annotatedObject;

        annotatedMethod = Arrays.stream(annotatedObject.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(UaMethod.class))
                .findFirst().orElseThrow(() -> new RuntimeException("no @UaMethod annotated annotatedMethod found"));
    }

    public Argument[] getInputArguments() {
        return a(inputArguments, Argument.class);
    }

    public Argument[] getOutputArguments() {
        return a(outputArguments, Argument.class);
    }

    @Override
    public void invoke(CallMethodRequest request, CompletableFuture<CallMethodResult> future) {
        NodeId objectId = request.getObjectId();

        Variant[] inputVariants = request.getInputArguments();

        if (inputVariants.length != inputArguments.size()) {
            future.complete(new CallMethodResult(
                    new StatusCode(StatusCodes.Bad_ArgumentsMissing),
                    new StatusCode[0], new DiagnosticInfo[0], new Variant[0]
            ));
        }

        Object[] inputs = new Object[inputVariants.length];
        StatusCode[] inputArgumentResults = new StatusCode[inputVariants.length];

        for (int i = 0; i < inputVariants.length; i++) {
            Argument argument = inputArguments.get(i);

            Variant variant = inputVariants[i];

            boolean dataTypeMatch = variant.getDataType()
                    .map(type -> type.equals(argument.getDataType()))
                    .orElse(false);

            if (!dataTypeMatch) {
                inputArgumentResults[i] = new StatusCode(StatusCodes.Bad_TypeMismatch);
            } else {
                inputArgumentResults[i] = StatusCode.GOOD;
            }

            inputs[i] = variant.getValue();
        }

        int outputCount = outputArguments.size();
        CountDownLatch latch = new CountDownLatch(outputCount);
        Object[] outputs = new Object[outputCount];

        for (int i = 0; i < outputCount; i++) {
            outputs[i] = new OutImpl<>(latch);
        }

        // TODO Implement an AsyncCountDownLatch and ditch this thread

        new Thread(() -> {
            try {
                Object[] parameters = new Object[1 + inputs.length + outputs.length];

                UaObjectNode objectNode = (UaObjectNode) nodeManager.getNode(objectId)
                        .orElseThrow(() -> new Exception("owner Object node found"));

                InvocationContext context = new InvocationContextImpl(objectNode, future, inputArgumentResults, latch);

                parameters[0] = context;

                System.arraycopy(inputs, 0, parameters, 1, inputs.length);
                System.arraycopy(outputs, 0, parameters, 1 + inputs.length, outputs.length);

                annotatedMethod.invoke(annotatedObject, parameters);
                latch.await();

                // Check if they called context.setFailure(...)
                if (!future.isDone()) {
                    Variant[] values = new Variant[outputCount];
                    for (int i = 0; i < outputCount; i++) {
                        values[i] = new Variant(((OutImpl<?>) outputs[i]).get());
                    }

                    future.complete(new CallMethodResult(
                            StatusCode.GOOD, inputArgumentResults,
                            new DiagnosticInfo[0], values
                    ));
                }
            } catch (InvocationTargetException e) {
                Throwable targetException = e.getTargetException();

                if (targetException instanceof UaException) {
                    StatusCode statusCode = ((UaException) targetException).getStatusCode();

                    future.complete(new CallMethodResult(
                            statusCode, inputArgumentResults,
                            new DiagnosticInfo[0], new Variant[0]
                    ));
                } else {
                    future.complete(new CallMethodResult(
                            new StatusCode(StatusCodes.Bad_InternalError),
                            inputArgumentResults, new DiagnosticInfo[0], new Variant[0]
                    ));
                }
            } catch (Throwable t) {
                future.complete(new CallMethodResult(
                        new StatusCode(StatusCodes.Bad_InternalError),
                        inputArgumentResults, new DiagnosticInfo[0], new Variant[0]
                ));
            }
        }).start();
    }


    public static AnnotationBasedInvocationHandler fromAnnotatedObject(UaNamespace namespace, Object annotatedObject) throws Exception {
        // TODO Make this work when parameter types are not built-in types

        Method annotatedMethod = Arrays.stream(annotatedObject.getClass().getMethods())
                .filter(m -> m.isAnnotationPresent(UaMethod.class))
                .findFirst().orElseThrow(() -> new Exception("no @UaMethod annotated annotatedMethod found"));

        Parameter[] parameters = annotatedMethod.getParameters();
        Type[] parameterTypes = annotatedMethod.getGenericParameterTypes();

        assert (parameters.length == parameterTypes.length);

        List<Argument> inputArguments = Lists.newArrayList();
        List<Argument> outputArguments = Lists.newArrayList();

        for (int i = 0; i < parameters.length; i++) {
            Parameter parameter = parameters[i];

            if (parameter.isAnnotationPresent(UaInputArgument.class)) {
                String name = parameter.getAnnotation(UaInputArgument.class).name();
                String description = parameter.getAnnotation(UaInputArgument.class).description();

                Class<?> parameterType = (Class<?>) parameterTypes[i];

                int dimensions = 0;
                while (parameterType.isArray()) {
                    parameterType = parameterType.getComponentType();
                    dimensions++;
                }

                UInteger[] arrayDimensions = dimensions > 0 ? new UInteger[dimensions] : null;

                inputArguments.add(new Argument(
                        name,
                        getDataType(parameterType),
                        dimensions > 0 ? dimensions : ValueRank.Scalar,
                        arrayDimensions,
                        LocalizedText.english(description)
                ));
            }

            if (parameter.isAnnotationPresent(UaOutputArgument.class)) {
                String name = parameter.getAnnotation(UaOutputArgument.class).name();
                String description = parameter.getAnnotation(UaOutputArgument.class).description();

                ParameterizedType parameterType = (ParameterizedType) parameterTypes[i];
                Class<?> actualType = (Class<?>) parameterType.getActualTypeArguments()[0];

                int dimensions = 0;
                while (actualType.isArray()) {
                    actualType = actualType.getComponentType();
                    dimensions++;
                }

                UInteger[] arrayDimensions = dimensions > 0 ? new UInteger[dimensions] : null;

                outputArguments.add(new Argument(
                        name,
                        getDataType(actualType),
                        dimensions > 0 ? dimensions : ValueRank.Scalar,
                        arrayDimensions,
                        LocalizedText.english(description)
                ));
            }
        }

        return new AnnotationBasedInvocationHandler(
                namespace,
                inputArguments,
                outputArguments,
                annotatedObject
        );
    }

    private static NodeId getDataType(Class<?> clazz) {
        return new NodeId(0, TypeUtil.getBuiltinTypeId(clazz));
    }

    public static interface Out<T> {
        void set(T value);
    }

    public static interface InvocationContext {
        UaObjectNode getObjectNode();
        void setFailure(UaException failure);
    }

    private static class OutImpl<T> implements Out<T> {

        private final AtomicReference<T> value = new AtomicReference<>();

        private volatile boolean set = false;
        private final CountDownLatch latch;

        public OutImpl(CountDownLatch latch) {
            this.latch = latch;
        }

        @Override
        public synchronized void set(T value) {
            this.value.set(value);

            if (!set) {
                latch.countDown();
                set = true;
            }
        }

        T get() {
            return value.get();
        }

    }

    private static class InvocationContextImpl implements InvocationContext {
        private final UaObjectNode objectNode;
        private final CompletableFuture<CallMethodResult> future;
        private final StatusCode[] inputArgumentResults;
        private final CountDownLatch latch;

        private InvocationContextImpl(UaObjectNode objectNode,
                                      CompletableFuture<CallMethodResult> future,
                                      StatusCode[] inputArgumentResults,
                                      CountDownLatch latch) {

            this.objectNode = objectNode;
            this.inputArgumentResults = inputArgumentResults;
            this.future = future;
            this.latch = latch;
        }

        @Override
        public UaObjectNode getObjectNode() {
            return objectNode;
        }

        @Override
        public void setFailure(UaException failure) {
            StatusCode statusCode = failure.getStatusCode();

            future.complete(new CallMethodResult(
                    statusCode, inputArgumentResults,
                    new DiagnosticInfo[0], new Variant[0]
            ));

            while (latch.getCount() > 0) latch.countDown();
        }
    }

}
