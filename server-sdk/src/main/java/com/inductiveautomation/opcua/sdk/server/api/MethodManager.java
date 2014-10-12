package com.inductiveautomation.opcua.sdk.server.api;

import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.core.StatusCodes;
import com.inductiveautomation.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.structured.CallMethodRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CallMethodResult;

public interface MethodManager {

    /**
     * Invoke one or more methods belonging to this {@link MethodManager}.
     *
     * @param requests The {@link CallMethodRequest}s for the methods to invoke.
     * @param future   The future to complete with the {@link CallMethodResult}s.
     */
    default void call(List<CallMethodRequest> requests, CompletableFuture<List<CallMethodResult>> future) {
        CallMethodResult notSupported = new CallMethodResult(
                new StatusCode(StatusCodes.Bad_NotSupported),
                new StatusCode[0],
                new DiagnosticInfo[0],
                new Variant[0]);

        future.complete(Collections.nCopies(requests.size(), notSupported));
    }

}
