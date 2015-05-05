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

package com.digitalpetri.opcua.sdk.server.services;

import com.digitalpetri.opcua.sdk.server.subscriptions.SubscriptionManager;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.application.services.MonitoredItemServiceSet;
import com.digitalpetri.opcua.stack.core.application.services.ServiceRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ModifyMonitoredItemsRequest;
import com.digitalpetri.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SetMonitoringModeRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SetTriggeringRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetTriggeringResponse;

public class MonitoredItemServices implements MonitoredItemServiceSet {

    private final ServiceMetric createMonitoredItemsMetric = new ServiceMetric();
    private final ServiceMetric modifyMonitoredItemsMetric = new ServiceMetric();
    private final ServiceMetric deleteMonitoredItemsMetric = new ServiceMetric();
    private final ServiceMetric setMonitoringModeMetric = new ServiceMetric();
    private final ServiceMetric setTriggeringMetric = new ServiceMetric();

    private final SubscriptionManager subscriptionManager;

    public MonitoredItemServices(SubscriptionManager subscriptionManager) {
        this.subscriptionManager = subscriptionManager;
    }

    @Override
    public void onCreateMonitoredItems(ServiceRequest<CreateMonitoredItemsRequest, CreateMonitoredItemsResponse> service) {
        createMonitoredItemsMetric.record(service);

        subscriptionManager.createMonitoredItems(service);
    }

    @Override
    public void onModifyMonitoredItems(ServiceRequest<ModifyMonitoredItemsRequest, ModifyMonitoredItemsResponse> service) {
        modifyMonitoredItemsMetric.record(service);

        subscriptionManager.modifyMonitoredItems(service);
    }

    @Override
    public void onDeleteMonitoredItems(ServiceRequest<DeleteMonitoredItemsRequest, DeleteMonitoredItemsResponse> service) {
        deleteMonitoredItemsMetric.record(service);

        subscriptionManager.deleteMonitoredItems(service);
    }

    @Override
    public void onSetMonitoringMode(ServiceRequest<SetMonitoringModeRequest, SetMonitoringModeResponse> service) {
        setMonitoringModeMetric.record(service);

        subscriptionManager.setMonitoringMode(service);
    }

    @Override
    public void onSetTriggering(ServiceRequest<SetTriggeringRequest, SetTriggeringResponse> service) throws UaException {
        setTriggeringMetric.record(service);

        subscriptionManager.setTriggering(service);
    }
}
