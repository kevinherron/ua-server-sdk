/*
 * Copyright 2015
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

package com.digitalpetri.opcua.sdk.client.api.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.enumerated.TimestampsToReturn;
import com.digitalpetri.opcua.stack.core.types.structured.CreateMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ModifyMonitoredItemsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemCreateRequest;
import com.digitalpetri.opcua.stack.core.types.structured.MonitoredItemModifyRequest;
import com.digitalpetri.opcua.stack.core.types.structured.SetMonitoringModeResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SetTriggeringResponse;

public interface MonitoredItemServices {

    /**
     * This service is used to create and add one or more monitored items to a subscription.
     *
     * @param subscriptionId     the server-assigned identifier for the subscription that will report notifications for
     *                           the monitored items.
     * @param timestampsToReturn the {@link TimestampsToReturn}.
     * @param itemsToCreate      a list of monitored items to be created and assigned to the specified subscription.
     * @return a {@link CompletableFuture} containing the {@link CreateMonitoredItemsResponse}.
     */
    CompletableFuture<CreateMonitoredItemsResponse> createMonitoredItems(UInteger subscriptionId,
                                                                         TimestampsToReturn timestampsToReturn,
                                                                         List<MonitoredItemCreateRequest> itemsToCreate);

    /**
     * This service is used to modify monitored items of a subscription.
     *
     * @param subscriptionId     the server-assigned identifier of the subscription the items to modify belong to.
     * @param timestampsToReturn the {@link TimestampsToReturn}.
     * @param itemsToModify      a list of monitored items to modify.
     * @return a {@link CompletableFuture} containing the {@link ModifyMonitoredItemsResponse}.
     */
    CompletableFuture<ModifyMonitoredItemsResponse> modifyMonitoredItems(UInteger subscriptionId,
                                                                         TimestampsToReturn timestampsToReturn,
                                                                         List<MonitoredItemModifyRequest> itemsToModify);

    /**
     * This service is used to remove one or more monitored items of a subscription.
     *
     * @param subscriptionId   the server-assigned identifier of the subscription the items to delete belong to.
     * @param monitoredItemIds a list of server-assigned identifiers for the items to delete.
     * @return a {@link CompletableFuture} containing the {@link DeleteMonitoredItemsResponse}.
     */
    CompletableFuture<DeleteMonitoredItemsResponse> deleteMonitoredItems(UInteger subscriptionId,
                                                                         List<UInteger> monitoredItemIds);

    /**
     * This service is used to set the {@link MonitoringMode} for one or more monitored items of a subscription.
     * <p>
     * Setting the mode to {@link MonitoringMode#Disabled} causes all queued notifications to be deleted.
     *
     * @param subscriptionId   the server-assigned identifier of the subscription the items belong to.
     * @param monitoringMode   the {@link MonitoringMode} to be set for the monitored items.
     * @param monitoredItemIds a list of server-assigned identifiers for the items whose {@link MonitoringMode} will
     *                         be set.
     * @return a {@link CompletableFuture} containing the {@link SetMonitoringModeResponse}.
     */
    CompletableFuture<SetMonitoringModeResponse> setMonitoringMode(UInteger subscriptionId,
                                                                   MonitoringMode monitoringMode,
                                                                   List<UInteger> monitoredItemIds);

    /**
     * This service is used to create and delete triggering links for a triggering item. The triggering item and the
     * items to report shall belong to the same subscription.
     *
     * @param subscriptionId   the server-assigned identifier for the subscription that contains the triggering item and
     *                         the items to report.
     * @param triggeringItemId the server-assigned identifier for the monitored item used as the triggering item.
     * @param linksToAdd       the list of server-assigned identifiers of the items to report that are to be added as
     *                         triggering links. The list of links to remove is processed before the links to add.
     * @param linksToRemove    the list of server-assigned identifiers of the items to report for the triggering links
     *                         to be removed. The list of links to remove is processed before the links to add.
     * @return a {@link CompletableFuture} containing the {@link SetTriggeringResponse}.
     */
    CompletableFuture<SetTriggeringResponse> setTriggering(UInteger subscriptionId,
                                                           UInteger triggeringItemId,
                                                           List<UInteger> linksToAdd,
                                                           List<UInteger> linksToRemove);

}
