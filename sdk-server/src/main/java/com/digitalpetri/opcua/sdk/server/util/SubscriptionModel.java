/*
 * Copyright 2014
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

package com.digitalpetri.opcua.sdk.server.util;

import java.math.RoundingMode;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import com.digitalpetri.opcua.sdk.core.AttributeIds;
import com.digitalpetri.opcua.sdk.server.DiagnosticsContext;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.api.AttributeManager;
import com.digitalpetri.opcua.sdk.server.api.AttributeManager.ReadContext;
import com.digitalpetri.opcua.sdk.server.api.DataItem;
import com.digitalpetri.opcua.sdk.server.api.MonitoredItem;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.digitalpetri.opcua.stack.core.util.ExecutionQueue;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.math.DoubleMath;

public class SubscriptionModel {

    private final Set<DataItem> itemSet = Collections.newSetFromMap(Maps.newConcurrentMap());

    private final List<ScheduledUpdate> schedule = Lists.newCopyOnWriteArrayList();

    private final ExecutorService executor;
    private final ScheduledExecutorService scheduler;
    private final ExecutionQueue executionQueue;

    private final OpcUaServer server;
    private final AttributeManager attributeServices;

    public SubscriptionModel(OpcUaServer server, AttributeManager attributeServices) {
        this.server = server;

        this.attributeServices = attributeServices;

        executor = server.getExecutorService();
        scheduler = server.getScheduledExecutorService();

        executionQueue = new ExecutionQueue(executor);
    }

    public void onDataItemsCreated(List<DataItem> items) {
        executionQueue.submit(() -> {
            itemSet.addAll(items);
            reschedule();
        });
    }

    public void onDataItemsModified(List<DataItem> items) {
        executionQueue.submit(this::reschedule);
    }

    public void onDataItemsDeleted(List<DataItem> items) {
        executionQueue.submit(() -> {
            itemSet.removeAll(items);
            reschedule();
        });
    }

    public void onMonitoringModeChanged(List<MonitoredItem> items) {
        executionQueue.submit(this::reschedule);
    }

    private void reschedule() {
        Map<Double, List<DataItem>> bySamplingInterval = itemSet.stream()
                .filter(DataItem::isSamplingEnabled)
                .collect(Collectors.groupingBy(DataItem::getSamplingInterval));

        List<ScheduledUpdate> updates = bySamplingInterval.keySet().stream().map(samplingInterval -> {
            List<DataItem> items = bySamplingInterval.get(samplingInterval);

            return new ScheduledUpdate(samplingInterval, items);
        }).collect(Collectors.toList());

        schedule.forEach(ScheduledUpdate::cancel);
        schedule.clear();
        schedule.addAll(updates);
        schedule.forEach(scheduler::execute);
    }

    private class ScheduledUpdate implements Runnable {

        private volatile boolean cancelled = false;

        private final long samplingInterval;
        private final List<DataItem> items;

        private ScheduledUpdate(double samplingInterval, List<DataItem> items) {
            this.samplingInterval = DoubleMath.roundToLong(samplingInterval, RoundingMode.UP);
            this.items = items;
        }

        private void cancel() {
            cancelled = true;
        }

        @Override
        public void run() {
            List<PendingRead> pending = items.stream()
                    .map(item -> new PendingRead(item.getReadValueId()))
                    .collect(Collectors.toList());

            List<ReadValueId> ids = pending.stream()
                    .map(PendingRead::getInput)
                    .collect(Collectors.toList());

            ReadContext context = new ReadContext(
                    server, null, new DiagnosticsContext<>());

            context.getFuture().thenAcceptAsync(values -> {
                Iterator<DataItem> ii = items.iterator();
                Iterator<DataValue> vi = values.iterator();

                while (ii.hasNext() && vi.hasNext()) {
                    DataItem item = ii.next();
                    DataValue value = vi.next();

                    TimestampsToReturn timestamps = item.getTimestampsToReturn();

                    if (timestamps != null) {
                        value = (item.getReadValueId().getAttributeId().intValue() == AttributeIds.Value) ?
                                DataValue.derivedValue(value, timestamps) :
                                DataValue.derivedNonValue(value, timestamps);
                    }

                    item.setValue(value);
                }

                if (!cancelled) {
                    scheduler.schedule(this, samplingInterval, TimeUnit.MILLISECONDS);
                }
            }, executor);

            executor.execute(() -> attributeServices.read(0d, TimestampsToReturn.Both, ids, context));
        }

    }

}
