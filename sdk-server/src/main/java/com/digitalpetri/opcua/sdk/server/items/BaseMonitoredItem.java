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

package com.digitalpetri.opcua.sdk.server.items;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.digitalpetri.opcua.sdk.server.api.MonitoredItem;
import com.digitalpetri.opcua.sdk.server.util.RingBuffer;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.serialization.UaStructure;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.ReadValueId;
import com.google.common.primitives.Ints;

public abstract class BaseMonitoredItem<ValueType> implements MonitoredItem {

    private static final int MAX_QUEUE_SIZE = 0xFFFF;

    protected volatile Map<UInteger, BaseMonitoredItem<?>> triggeredItems;
    protected volatile boolean triggered = false;

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

        boolean queueIsEmpty = queue.isEmpty();

        if (queueIsEmpty && triggered) {
            triggered = false;
        }

        return queueIsEmpty;
    }

    public synchronized boolean hasNotifications() {
        return (queue.size() > 0 && monitoringMode == MonitoringMode.Reporting);
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

            RingBuffer<ValueType> oldQueue = queue;
            queue = new RingBuffer<>(this.queueSize);

            while (oldQueue.size() > 0) {
                enqueue(oldQueue.remove());
            }
        }
    }

    protected abstract void enqueue(ValueType value);

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

    public synchronized Map<UInteger, BaseMonitoredItem<?>> getTriggeredItems() {
        if (triggeredItems == null) triggeredItems = new HashMap<>();

        return triggeredItems;
    }

    public synchronized boolean isTriggered() {
        return triggered;
    }

    public abstract ExtensionObject getFilterResult();

    protected abstract void installFilter(ExtensionObject filterXo) throws UaException;

    protected abstract UaStructure wrapQueueValue(ValueType value);

}
