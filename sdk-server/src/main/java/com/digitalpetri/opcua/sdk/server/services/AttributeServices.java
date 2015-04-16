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

package com.digitalpetri.opcua.sdk.server.services;

import com.digitalpetri.opcua.sdk.server.services.helpers.ReadHelper;
import com.digitalpetri.opcua.sdk.server.services.helpers.WriteHelper;
import com.digitalpetri.opcua.stack.core.application.services.AttributeServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ReadRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ReadResponse;
import com.digitalpetri.opcua.stack.core.types.structured.WriteRequest;
import com.digitalpetri.opcua.stack.core.types.structured.WriteResponse;

public class AttributeServices implements AttributeServiceSet {

    private final ServiceMetric readMetric = new ServiceMetric();
    private final ServiceMetric writeMetric = new ServiceMetric();

    @Override
    public void onRead(ServiceRequest<ReadRequest, ReadResponse> service) {
        readMetric.record(service);

        ReadHelper.read(service);
    }

    @Override
    public void onWrite(ServiceRequest<WriteRequest, WriteResponse> service) {
        writeMetric.record(service);

        WriteHelper.write(service);
    }

}
