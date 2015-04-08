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

package com.inductiveautomation.opcua.sdk.server.services;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Timer;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ServiceCounterDataType;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

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
        return new ServiceCounterDataType(
                uint(requestTimer.getCount()),
                uint(errorCounter.getCount())
        );
    }

}
