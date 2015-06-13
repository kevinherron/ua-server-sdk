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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

public class FutureUtils {


    public static <T> CompletableFuture<List<T>> sequence(List<CompletableFuture<T>> futures) {
        if (futures.isEmpty()) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }

        CompletableFuture[] fa = futures.toArray(new CompletableFuture[futures.size()]);

        return CompletableFuture.allOf(fa).thenApply(
                v -> futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }

    public static <T> CompletableFuture<List<T>> sequence(CompletableFuture<T>[] futures) {
        if (futures.length == 0) {
            return CompletableFuture.completedFuture(Collections.emptyList());
        }

        return CompletableFuture.allOf(futures).thenApply(
                v -> Arrays.stream(futures).map(CompletableFuture::join).collect(Collectors.toList()));
    }

}
