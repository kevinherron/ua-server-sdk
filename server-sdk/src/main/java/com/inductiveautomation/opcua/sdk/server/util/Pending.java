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

import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public interface Pending<I, O> {

    /**
     * @return The {@link CompletableFuture} to complete when the result is ready.
     */
    public abstract CompletableFuture<O> getFuture();

    /**
     * @return The input parameter to the pending operation.
     */
    public abstract I getInput();

    /**
     * Builds a {@link CompletableFuture} suitable for use as a completion callback. When completed, each of the
     * provided {@link Pending}'s {@link CompletableFuture} will be completed.
     * <p>
     * It is assumed that the size of the list this future is completed with matches the number of provided {@link
     * Pending}s. A {@link RuntimeException} will be thrown otherwise.
     *
     * @param pending A list of {@link Pending} operations.
     * @param <I>     Input parameter of {@link Pending} operations.
     * @param <O>     Output parameter of {@link Pending} operations.
     * @return A {@link CompletableFuture} that, when completed, then completes each of the given {@link Pending}'s
     * {@link CompletableFuture}.
     */
    public static <I, O> CompletableFuture<List<O>> callback(List<? extends Pending<I, O>> pending) {
        CompletableFuture<List<O>> future = new CompletableFuture<>();

        future.thenAccept(results -> {
            if (results.size() != pending.size()) {
                String message = String.format("result size (%s) does not match pending size (%s)", results.size(), pending.size());
                throw new RuntimeException(message);
            }

            Iterator<? extends Pending<I, O>> pi = pending.iterator();
            Iterator<O> ri = results.iterator();

            while (pi.hasNext() && ri.hasNext()) {
                pi.next().getFuture().complete(ri.next());
            }
        });

        return future;
    }

}
