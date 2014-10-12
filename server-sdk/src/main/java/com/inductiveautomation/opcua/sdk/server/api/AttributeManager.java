package com.inductiveautomation.opcua.sdk.server.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteValue;

public interface AttributeManager {

    /**
     * Read one or more values from nodes belonging to this {@link NodeManager}.
     *
     * @param readValueIds The values to read.
     * @param maxAge       Requested max age.
     * @param timestamps   Requested timestamp values.
     * @param future       The future for the requested read results.
     */
    void read(List<ReadValueId> readValueIds,
              Double maxAge,
              TimestampsToReturn timestamps,
              CompletableFuture<List<DataValue>> future);

    /**
     * Write one or more values to nodes belonging to this {@link NodeManager}.
     *
     * @param writeValues The values to write.
     * @param future      The future for the requested write results.
     */
    void write(List<WriteValue> writeValues, CompletableFuture<List<StatusCode>> future);

}
