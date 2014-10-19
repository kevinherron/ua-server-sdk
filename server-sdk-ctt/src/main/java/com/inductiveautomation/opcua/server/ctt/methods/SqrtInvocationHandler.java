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

package com.inductiveautomation.opcua.server.ctt.methods;

import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.sdk.server.api.MethodInvocationHandler;
import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.structured.CallMethodRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CallMethodResult;

public class SqrtInvocationHandler implements MethodInvocationHandler {

    @Override
    public void invoke(CallMethodRequest request, CompletableFuture<CallMethodResult> result) {
        Variant[] inputs = request.getInputArguments();

        if (inputs.length != 1) {
            result.complete(new CallMethodResult(
                    new StatusCode(StatusCodes.Bad_ArgumentsMissing),
                    new StatusCode[0],
                    new DiagnosticInfo[0],
                    new Variant[0]
            ));
        }

        Object value = inputs[0].getValue();

        if (value instanceof Double) {
            Double x = (Double) value;
            Double x_sqrt = Math.sqrt(x);

            result.complete(new CallMethodResult(
                    StatusCode.Good,
                    new StatusCode[]{StatusCode.Good},
                    new DiagnosticInfo[0],
                    new Variant[]{new Variant(x_sqrt)}
            ));
        } else {
            result.complete(new CallMethodResult(
                    new StatusCode(StatusCodes.Bad_TypeMismatch),
                    new StatusCode[]{new StatusCode(StatusCodes.Bad_TypeMismatch)},
                    new DiagnosticInfo[0],
                    new Variant[0]
            ));
        }
    }

}
