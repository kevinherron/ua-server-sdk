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
