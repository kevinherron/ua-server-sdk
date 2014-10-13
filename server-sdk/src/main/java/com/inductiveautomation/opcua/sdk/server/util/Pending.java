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
