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

package com.digitalpetri.opcua.sdk.server.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;

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
     * Complete the operation with {@link ReadContext#complete(List)}.
     *
     * @param context      the {@link ReadContext}.
     * @param maxAge       requested max age.
     * @param timestamps   requested timestamp values.
     * @param readValueIds the values to read.
     */
    void read(ReadContext context,
              Double maxAge,
              TimestampsToReturn timestamps,
              List<ReadValueId> readValueIds);

    /**
     * Write one or more values to nodes belonging to this {@link AttributeManager}.
     * <p>
     * Complete the operation with {@link WriteContext#complete(List)}.
     *
     * @param context     the {@link WriteContext}.
     * @param writeValues the values to write.
     */
    void write(WriteContext context, List<WriteValue> writeValues);

    final class ReadContext extends OperationContext<ReadValueId, DataValue> {
        public ReadContext(OpcUaServer server,
                           @Nullable Session session,
                           DiagnosticsContext<ReadValueId> diagnosticsContext) {

            super(server, session, diagnosticsContext);
        }

        public ReadContext(OpcUaServer server,
                           @Nullable Session session,
                           CompletableFuture<List<DataValue>> future,
                           DiagnosticsContext<ReadValueId> diagnosticsContext) {

            super(server, session, future, diagnosticsContext);
        }
    }

    final class WriteContext extends OperationContext<WriteValue, StatusCode> {
        public WriteContext(OpcUaServer server,
                            @Nullable Session session,
                            DiagnosticsContext<WriteValue> diagnosticsContext) {

            super(server, session, diagnosticsContext);
        }

        public WriteContext(OpcUaServer server,
                            @Nullable Session session,
                            CompletableFuture<List<StatusCode>> future,
                            DiagnosticsContext<WriteValue> diagnosticsContext) {

            super(server, session, future, diagnosticsContext);
        }
    }

}
