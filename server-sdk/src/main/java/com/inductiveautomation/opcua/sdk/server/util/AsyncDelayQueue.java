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

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import com.google.common.collect.Maps;

public class AsyncDelayQueue {

    private final DelayQueue<QueueElement> queue = new DelayQueue<>();
    private final Map<UUID, AsyncQueue> mailboxes = Maps.newConcurrentMap();

    public <T extends Delayed> void offer(T element, UUID key) {
        queue.offer(new QueueElement(element, key));
    }

    public <T extends Delayed> CompletableFuture<T> poll(UUID key) {
        AsyncQueue<?> aq = mailboxes.computeIfAbsent(key, id -> new AsyncQueue());

        return aq.poll().thenApply(o -> (T) o);
    }

    private static class QueueElement implements Delayed {

        private final Delayed element;
        private final UUID key;

        private QueueElement(Delayed element, UUID key) {
            this.element = element;
            this.key = key;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return element.getDelay(unit);
        }

        @Override
        public int compareTo(Delayed o) {
            return element.compareTo(o);
        }

    }
}
