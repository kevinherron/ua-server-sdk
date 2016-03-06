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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;

public class FutureUtils {

    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        if (futures.isEmpty()) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }

        CompletableFuture[] fa = futures.toArray(new CompletableFuture[futures.size()]);

        return CompletableFuture.allOf(fa).thenApply(v -> {
            List<T> results = new ArrayList<>(futures.size());

            for (CompletableFuture<T> cf : futures) {
                results.add(cf.join());
            }

            return results;
        });
    }

    public static <T> CompletableFuture<List<T>> sequence(CompletableFuture<T>[] futures) {
        if (futures.length == 0) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }

        return CompletableFuture.allOf(futures).thenApply(v -> {
            List<T> results = new ArrayList<>(futures.length);

            for (CompletableFuture<T> cf : futures) {
                results.add(cf.join());
            }

            return results;
        });
    }

    /**
     * Return a {@link CompletableFuture} that has been completed exceptionally using the provided {@link Throwable}.
     *
     * @param ex the {@link Throwable} to complete with.
     * @return a {@link CompletableFuture} that has been completed exceptionally using the provided {@link Throwable}.
     */
    public static <T> CompletableFuture<T> failedFuture(Throwable ex) {
        CompletableFuture<T> f = new CompletableFuture<>();
        f.completeExceptionally(ex);
        return f;
    }

    /**
     * Return a {@link CompletableFuture} that has been completed exceptionally with a {@link UaException} built from
     * {@code statusCode}.
     *
     * @param statusCode the status code to build the {@link UaException} with.
     * @return a {@link CompletableFuture} that has been completed exceptionally with a {@link UaException} built from
     * {@code statusCode}.
     */
    public static <T> CompletableFuture<T> failedUaFuture(long statusCode) {
        return failedUaFuture(new StatusCode(statusCode));
    }

    /**
     * Return a {@link CompletableFuture} that has been completed exceptionally with a {@link UaException} built from
     * {@code statusCode}.
     *
     * @param statusCode the {@link StatusCode} to build the {@link UaException} with.
     * @return a {@link CompletableFuture} that has been completed exceptionally with a {@link UaException} built from
     * {@code statusCode}.
     */
    public static <T> CompletableFuture<T> failedUaFuture(StatusCode statusCode) {
        return failedFuture(new UaException(statusCode));
    }

}
