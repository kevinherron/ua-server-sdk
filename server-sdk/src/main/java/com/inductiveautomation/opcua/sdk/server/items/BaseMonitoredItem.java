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

package com.inductiveautomation.opcua.sdk.server.items;

import java.util.List;

import com.google.common.primitives.Ints;
import com.inductiveautomation.opcua.sdk.server.api.MonitoredItem;
import com.inductiveautomation.opcua.sdk.server.util.RingBuffer;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.serialization.UaStructure;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExtensionObject;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;
import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.inductiveautomation.opcua.stack.core.types.structured.ReadValueId;

public abstract class BaseMonitoredItem<ValueType> implements MonitoredItem {

    private static final int MAX_QUEUE_SIZE = 0xFFFF;

    protected volatile RingBuffer<ValueType> queue;

    protected volatile long clientHandle;
    protected volatile int queueSize;
    protected volatile double samplingInterval;
    protected volatile boolean discardOldest;

    protected final UInteger id;
    protected final ReadValueId readValueId;
    protected volatile MonitoringMode monitoringMode;
    protected volatile TimestampsToReturn timestamps;

    protected BaseMonitoredItem(UInteger id,
                                ReadValueId readValueId,
                                MonitoringMode monitoringMode,
                                TimestampsToReturn timestamps,
                                UInteger clientHandle,
                                double samplingInterval,
                                UInteger queueSize,
                                boolean discardOldest) {

        this.id = id;
        this.readValueId = readValueId;
        this.monitoringMode = monitoringMode;
        this.timestamps = timestamps;
        this.clientHandle = clientHandle.longValue();
        this.samplingInterval = samplingInterval;
        this.discardOldest = discardOldest;

        setQueueSize(queueSize);

        queue = new RingBuffer<>(this.queueSize);
    }

    protected void setQueueSize(UInteger queueSize) {
        int qs = Ints.saturatedCast(queueSize.longValue());
        qs = Math.min(qs, MAX_QUEUE_SIZE);
        qs = Math.max(qs, 1);
        this.queueSize = qs;
    }

    public synchronized boolean getNotifications(List<UaStructure> notifications, int max) {
        int queueSize = queue.size();
        int count = Math.min(queueSize, max);

        for (int i = 0; i < count; i++) {
            notifications.add(wrapQueueValue(queue.remove()));
        }

        return queue.isEmpty();
    }

    public synchronized boolean hasNotifications() {
        return queue.size() > 0 && monitoringMode == MonitoringMode.Reporting;
    }

    public synchronized void modify(TimestampsToReturn timestamps,
                                    UInteger clientHandle,
                                    double samplingInterval,
                                    ExtensionObject filter,
                                    UInteger queueSize,
                                    boolean discardOldest) throws UaException {

        installFilter(filter);

        this.timestamps = timestamps;
        this.clientHandle = clientHandle.longValue();
        this.samplingInterval = samplingInterval;
        this.discardOldest = discardOldest;

        if (queueSize.intValue() != this.queueSize) {
            setQueueSize(queueSize);

            RingBuffer<ValueType> nq = new RingBuffer<>(this.queueSize);
            while (queue.size() > 0) {
                nq.add(queue.remove());
            }
            queue = nq;
        }
    }

    public void setMonitoringMode(MonitoringMode monitoringMode) {
        this.monitoringMode = monitoringMode;

        if (monitoringMode == MonitoringMode.Disabled) {
            queue.clear();
        }
    }

    @Override
    public UInteger getId() {
        return id;
    }

    @Override
    public ReadValueId getReadValueId() {
        return readValueId;
    }

    @Override
    public TimestampsToReturn getTimestampsToReturn() {
        return timestamps;
    }

    public long getClientHandle() {
        return clientHandle;
    }

    public int getQueueSize() {
        return queueSize;
    }

    public double getSamplingInterval() {
        return samplingInterval;
    }

    public boolean isDiscardOldest() {
        return discardOldest;
    }

    public MonitoringMode getMonitoringMode() {
        return monitoringMode;
    }

    public abstract ExtensionObject getFilterResult();

    protected abstract void installFilter(ExtensionObject filterXo) throws UaException;

    protected abstract UaStructure wrapQueueValue(ValueType value);

}
