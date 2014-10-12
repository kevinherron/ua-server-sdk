package com.inductiveautomation.opcua.sdk.server.services;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Timer;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ServiceCounterDataType;

public class ServiceMetric {

    private final Timer requestTimer = new Timer();
    private final Counter errorCounter = new Counter();

    public void record(ServiceRequest<?, ?> service) {
        Timer.Context context = requestTimer.time();

        service.getFuture().whenComplete((r, ex) -> {
            context.stop();
            if (ex != null) errorCounter.inc();
        });
    }

    public Timer getRequestTimer() {
        return requestTimer;
    }

    public Counter getErrorCounter() {
        return errorCounter;
    }

    public ServiceCounterDataType getServiceCounter() {
        return new ServiceCounterDataType(requestTimer.getCount(), errorCounter.getCount());
    }

}
