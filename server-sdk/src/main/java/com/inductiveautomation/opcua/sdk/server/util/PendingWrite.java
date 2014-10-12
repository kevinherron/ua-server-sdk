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

package com.inductiveautomation.opcua.sdk.server.util;

import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.core.types.builtin.StatusCode;
import com.inductiveautomation.opcua.stack.core.types.structured.WriteValue;

public class PendingWrite implements Pending<WriteValue, StatusCode> {

    private final CompletableFuture<StatusCode> future = new CompletableFuture<>();

    private final WriteValue writeValue;

    public PendingWrite(WriteValue writeValue) {
        this.writeValue = writeValue;
    }

    @Override
    public CompletableFuture<StatusCode> getFuture() {
        return future;
    }

    @Override
    public WriteValue getInput() {
        return writeValue;
    }

}
