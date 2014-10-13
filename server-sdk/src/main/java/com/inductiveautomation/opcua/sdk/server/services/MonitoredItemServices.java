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

package com.inductiveautomation.opcua.sdk.server.services;

import com.inductiveautomation.opcua.sdk.server.SubscriptionManager;
import com.inductiveautomation.opcua.stack.core.UaException;
import com.inductiveautomation.opcua.stack.core.application.services.MonitoredItemServiceSet;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifyMonitoredItemsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.SetTriggeringResponse;

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
