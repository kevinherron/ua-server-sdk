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

package com.digitalpetri.opcua.sdk.server.util;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;

public class PendingRead implements Pending<ReadValueId, DataValue> {

    private final CompletableFuture<DataValue> future = new CompletableFuture<>();

    private final ReadValueId id;

    public PendingRead(ReadValueId id) {
        this.id = id;
    }

    @Override
    public ReadValueId getInput() {
        return id;
    }

    @Override
    public CompletableFuture<DataValue> getFuture() {
        return future;
    }
}
