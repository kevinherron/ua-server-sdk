/*
 * OPC-UA SDK
 *
 * Copyright (C) 2014 Kevin Herron
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

package com.inductiveautomation.opcua.sdk.server.services;

import com.inductiveautomation.opcua.sdk.server.services.helpers.ReadHelper;
import com.inductiveautomation.opcua.sdk.server.services.helpers.WriteHelper;
import com.inductiveautomation.opcua.stack.core.application.services.AttributeServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteResponse;

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
