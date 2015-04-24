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

package com.digitalpetri.opcua.sdk.server.api;

import java.util.List;

import com.digitalpetri.opcua.sdk.server.DiagnosticsContext;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.Session;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.core.types.structured.WriteValue;

public interface AttributeManager {

    /**
     * Read one or more values from nodes belonging to this {@link AttributeManager}.
     * <p>
     * When the operation is finished, complete {@link ReadContext#getFuture()}.
     *
     * @param maxAge       requested max age.
     * @param timestamps   requested timestamp values.
     * @param readValueIds the values to read.
     * @param context      the {@link ReadContext}.
     */
    void read(Double maxAge,
              TimestampsToReturn timestamps,
              List<ReadValueId> readValueIds,
              ReadContext context);

    /**
     * Write one or more values to nodes belonging to this {@link AttributeManager}.
     * <p>
     * When the operation is finished, complete {@link WriteContext#getFuture()}.
     *
     * @param writeValues the values to write.
     * @param context     the {@link WriteContext}.
     */
    void write(List<WriteValue> writeValues, WriteContext context);

    final class ReadContext extends OperationContext<ReadValueId, DataValue> {
        public ReadContext(OpcUaServer server, Session session,
                           DiagnosticsContext<ReadValueId> diagnosticsContext) {

            super(server, session, diagnosticsContext);
        }
    }

    final class WriteContext extends OperationContext<WriteValue, StatusCode> {
        public WriteContext(OpcUaServer server, Session session,
                            DiagnosticsContext<WriteValue> diagnosticsContext) {

            super(server, session, diagnosticsContext);
        }
    }

}
