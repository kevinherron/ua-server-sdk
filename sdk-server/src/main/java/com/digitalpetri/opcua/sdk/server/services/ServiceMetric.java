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

package com.digitalpetri.opcua.sdk.server.services;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Timer;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ServiceCounterDataType;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

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
                uint(errorCounter.getCount()));
    }

}
