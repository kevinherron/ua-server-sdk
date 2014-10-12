package com.inductiveautomation.opcua.sdk.server.util;

import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.core.types.structured.CallMethodRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CallMethodResult;

public class PendingCall implements Pending<CallMethodRequest, CallMethodResult> {

    private final CompletableFuture<CallMethodResult> future = new CompletableFuture<>();

    private final CallMethodRequest request;

    public PendingCall(CallMethodRequest request) {
        this.request = request;
    }

    @Override
    public CompletableFuture<CallMethodResult> getFuture() {
        return future;
    }

    @Override
    public CallMethodRequest getInput() {
        return request;
    }

}
