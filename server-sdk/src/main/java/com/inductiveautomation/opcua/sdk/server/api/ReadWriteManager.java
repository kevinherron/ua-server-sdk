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

package com.inductiveautomation.opcua.sdk.server.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteValue;

public interface ReadWriteManager {

    /**
     * Read one or more values from nodes belonging to this {@link ReadWriteManager}.
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
     * Write one or more values to nodes belonging to this {@link ReadWriteManager}.
     *
     * @param writeValues The values to write.
     * @param future      The future for the requested write results.
     */
    void write(List<WriteValue> writeValues, CompletableFuture<List<StatusCode>> future);

}
