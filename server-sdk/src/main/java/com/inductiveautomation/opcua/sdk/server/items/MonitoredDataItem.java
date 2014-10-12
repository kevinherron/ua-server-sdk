///*
// * OPC-UA SDK
// *
// * Copyright (C) 2014 Kevin Herron
// *
// * This program is free software: you can redistribute it and/or modify
// * it under the terms of the GNU Affero General Public License as
// * published by the Free Software Foundation, either version 3 of the
// * License, or (at your option) any later version.
// *
// * This program is distributed in the hope that it will be useful,
// * but WITHOUT ANY WARRANTY; without even the implied warranty of
// * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// * GNU Affero General Public License for more details.
// *
// * You should have received a copy of the GNU Affero General Public License
// * along with this program.  If not, see <http://www.gnu.org/licenses/>.
// */
//
//package com.inductiveautomation.opcua.sdk.server;
//
//import java.util.List;
//import java.util.concurrent.atomic.AtomicLong;
//
//import com.inductiveautomation.opcua.sdk.server.api.IMonitoredItem;
//import com.inductiveautomation.opcua.sdk.server.util.DataChangeMonitoringFilter;
//import com.inductiveautomation.opcua.sdk.server.util.RingBuffer;
//import com.inductiveautomation.opcua.stack.core.StatusCodes;
//import com.inductiveautomation.opcua.stack.core.UaException;
//import com.inductiveautomation.opcua.stack.core.types.builtin.*;
//import com.inductiveautomation.opcua.stack.core.types.enumerated.DataChangeTrigger;
//import com.inductiveautomation.opcua.stack.core.types.enumerated.DeadbandType;
//import com.inductiveautomation.opcua.stack.core.types.enumerated.MonitoringMode;
//import com.inductiveautomation.opcua.stack.core.types.enumerated.TimestampsToReturn;
//import com.inductiveautomation.opcua.stack.core.types.structured.*;
//import com.google.common.primitives.Ints;
//
//public class MonitoredDataItem implements IMonitoredItem {
//
//    public static final AtomicLong INSTANCE_COUNT = new AtomicLong(0L);
//
//    private volatile long clientHandle;
//    private volatile long queueSize;
//    private volatile double samplingInterval;
//    private volatile boolean discardOldest;
//    private volatile ExtensionObject filterResult = null;
//
//    private volatile RingBuffer<DataValue> queue;
//    private volatile DataValue lastValue;
//
//    private final long id;
//    private final ReadValueId readValueId;
//    private volatile MonitoringMode monitoringMode;
//    private volatile TimestampsToReturn timestamps;
//    private volatile DataChangeFilter filter;
//
//    private MonitoredDataItem(long id,
//                              ReadValueId readValueId,
//                              MonitoringMode monitoringMode,
//                              TimestampsToReturn timestamps,
//                              DataChangeFilter filter,
//                              MonitoringParameters parameters) {
//
//        this.id = id;
//        this.readValueId = readValueId;
//        this.monitoringMode = monitoringMode;
//        this.timestamps = timestamps;
//        this.filter = filter;
//
//        clientHandle = parameters.getClientHandle();
//        queueSize = (parameters.getQueueSize().intValue() <= 1) ? 1L : parameters.getQueueSize();
//        samplingInterval = parameters.getSamplingInterval();
//        discardOldest = parameters.getDiscardOldest();
//
//        queue = new RingBuffer<>(Ints.saturatedCast(queueSize));
//    }
//
//    @Override
//    public synchronized void setValue(DataValue value) {
//        boolean valuePassesFilter = DataChangeMonitoringFilter.filter(lastValue, value, filter);
//
//        if (valuePassesFilter) {
//            if (queue.size() < queue.maxSize()) {
//                queue.add(value);
//            } else {
//                if (discardOldest) {
//                    queue.add(value);
//                } else {
//                    queue.set(queue.maxSize() - 1, value);
//                }
//            }
//
//            lastValue = value;
//        }
//    }
//
//    @Override
//    public synchronized void setQuality(StatusCode quality) {
//        if (lastValue == null) {
//            setValue(new DataValue(Variant.NullValue, quality, DateTime.now(), DateTime.now()));
//        } else {
//            DataValue value = new DataValue(
//                    lastValue.getValue(),
//                    quality,
//                    DateTime.now(),
//                    DateTime.now()
//            );
//
//            setValue(value);
//        }
//    }
//
//    public synchronized void notifications(List<MonitoredItemNotification> notifications, int max) {
//        int count = Math.min(queue.size(), max);
//
//        for (int i = 0; i < count; i++) {
//            notifications.add(new MonitoredItemNotification(clientHandle, queue.remove()));
//        }
//    }
//
//    public synchronized boolean hasNotifications() {
//        return queue.size() > 0 && monitoringMode == MonitoringMode.Reporting;
//    }
//
//    public synchronized void modify(MonitoringParameters parameters, TimestampsToReturn ttr) throws UaException {
//        filter = decodeFilter(parameters.getFilter());
//        timestamps = ttr;
//
//        clientHandle = parameters.getClientHandle();
//        samplingInterval = parameters.getSamplingInterval();
//        discardOldest = parameters.getDiscardOldest();
//
//        if (queueSize != parameters.getQueueSize()) {
//            queueSize = (parameters.getQueueSize().intValue() <= 1) ?
//                    1L : parameters.getQueueSize();
//
//            RingBuffer<DataValue> nq = new RingBuffer<>(Ints.saturatedCast(queueSize));
//            while (queue.size() > 0) {
//                nq.add(queue.remove());
//            }
//            queue = nq;
//        }
//    }
//
//    @Override
//    public void setSamplingInterval(Double samplingInterval) {
//        this.samplingInterval = samplingInterval;
//    }
//
//    public void setMonitoringMode(MonitoringMode monitoringMode) {
//        this.monitoringMode = monitoringMode;
//    }
//
//    public ExtensionObject getFilterResult() {
//        return filterResult;
//    }
//
//    public Long getId() {
//        return id;
//    }
//
//    public MonitoringMode getMonitoringMode() {
//        return monitoringMode;
//    }
//
//    public long getQueueSize() {
//        return queueSize;
//    }
//
//    @Override
//    public ReadValueId getReadValueId() {
//        return readValueId;
//    }
//
//    @Override
//    public double getSamplingInterval() {
//        return samplingInterval;
//    }
//
//    public boolean isDiscardOldest() {
//        return discardOldest;
//    }
//
//    @Override
//    public boolean isSamplingEnabled() {
//        return getMonitoringMode() != MonitoringMode.Disabled;
//    }
//
//    @Override
//    protected void finalize() throws Throwable {
//        INSTANCE_COUNT.decrementAndGet();
//        super.finalize();
//    }
//
//    private static final DataChangeFilter DefaultFilter = new DataChangeFilter(
//            DataChangeTrigger.StatusValue,
//            (long) DeadbandType.None.getValue(),
//            0.0);
//
//    public static MonitoredDataItem create(long id,
//                                           ReadValueId readValueId,
//                                           MonitoringMode monitoringMode,
//                                           TimestampsToReturn timestamps,
//                                           MonitoringParameters parameters) throws UaException {
//
//        DataChangeFilter filter = decodeFilter(parameters.getFilter());
//
//        INSTANCE_COUNT.incrementAndGet();
//
//        return new MonitoredDataItem(
//                id, readValueId, monitoringMode, timestamps, filter, parameters);
//    }
//
//    private static DataChangeFilter decodeFilter(ExtensionObject filterXo) throws UaException {
//        if (filterXo == null || filterXo.getObject() == null) {
//            return DefaultFilter;
//        } else {
//            Object filter = filterXo.getObject();
//
//            if (filter instanceof MonitoringFilter) {
//                if (filter instanceof DataChangeFilter) {
//                    return ((DataChangeFilter) filter);
//                } else {
//                    throw new UaException(StatusCodes.Bad_MonitoredItemFilterUnsupported);
//                }
//            } else {
//                throw new UaException(StatusCodes.Bad_MonitoredItemFilterInvalid);
//            }
//        }
//    }
//
//}
