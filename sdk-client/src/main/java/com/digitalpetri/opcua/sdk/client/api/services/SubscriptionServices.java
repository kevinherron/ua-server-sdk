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

package com.digitalpetri.opcua.sdk.client.api.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.MonitoringMode;
import com.digitalpetri.opcua.stack.core.types.structured.CreateSubscriptionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteSubscriptionsResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ModifySubscriptionResponse;
import com.digitalpetri.opcua.stack.core.types.structured.PublishResponse;
import com.digitalpetri.opcua.stack.core.types.structured.RepublishResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SetPublishingModeResponse;
import com.digitalpetri.opcua.stack.core.types.structured.SubscriptionAcknowledgement;
import com.digitalpetri.opcua.stack.core.types.structured.TransferSubscriptionsResponse;

public interface SubscriptionServices {

    /**
     * This service is used to create a subscription. Subscriptions monitor a set of monitored items for notifications
     * and return them to the client in response to Publish requests.
     *
     * @param requestedPublishingInterval this interval defines the cyclic rate that the subscription is being requested
     *                                    to return notifications to the client. This interval is expressed in
     *                                    milliseconds.
     * @param requestedLifetimeCount      the requested lifetime count. The lifetime count shall be a minimum of three
     *                                    times the keep keep-alive count.
     * @param requestedMaxKeepAliveCount  the requested maximum keep-alive count. When the publishing timer has expired
     *                                    this number of times without requiring any notification to be sent, the
     *                                    subscription sends a keep-alive message to the client.
     * @param maxNotificationsPerPublish  the maximum number of notifications that the client wishes to receive in a
     *                                    single publish response. A value of zero indicates that there is no limit.
     * @param publishingEnabled           if {@code true}, publishing is enabled for this subscription.
     * @param priority                    indicates the relative priority of the subscription.
     * @return a {@link CompletableFuture} containing the {@link CreateSubscriptionResponse}.
     */
    CompletableFuture<CreateSubscriptionResponse> createSubscription(double requestedPublishingInterval,
                                                                     UInteger requestedLifetimeCount,
                                                                     UInteger requestedMaxKeepAliveCount,
                                                                     UInteger maxNotificationsPerPublish,
                                                                     boolean publishingEnabled,
                                                                     UByte priority);

    /**
     * This service is used to modify a subscription.
     *
     * @param subscriptionId              the server-assigned identifier for the subscription.
     * @param requestedPublishingInterval this interval defines the cyclic rate that the subscription is being requested
     *                                    to return notifications to the client. This interval is expressed in
     *                                    milliseconds.
     * @param requestedLifetimeCount      the requested lifetime count. The lifetime count shall be a minimum of three
     *                                    times the keep keep-alive count.
     * @param requestedMaxKeepAliveCount  the requested maximum keep-alive count. When the publishing timer has expired
     *                                    this number of times without requiring any notification to be sent, the
     *                                    subscription sends a keep-alive message to the client.
     * @param maxNotificationsPerPublish  the maximum number of notifications that the client wishes to receive in a
     *                                    single publish response. A value of zero indicates that there is no limit.
     * @param priority                    indicates the relative priority of the subscription.
     * @return a {@link CompletableFuture} containing the {@link ModifySubscriptionResponse}.
     */
    CompletableFuture<ModifySubscriptionResponse> modifySubscription(UInteger subscriptionId,
                                                                     double requestedPublishingInterval,
                                                                     UInteger requestedLifetimeCount,
                                                                     UInteger requestedMaxKeepAliveCount,
                                                                     UInteger maxNotificationsPerPublish,
                                                                     UByte priority);

    /**
     * This service is invoked to delete one or more subscriptions that belong to the client's session.
     *
     * @param subscriptionIds the server-assigned identifiers for the subscriptions.
     * @return a {@link CompletableFuture} containing the {@link DeleteSubscriptionsResponse}.
     */
    CompletableFuture<DeleteSubscriptionsResponse> deleteSubscriptions(List<UInteger> subscriptionIds);

    /**
     * This service is used to transfer a subscription and its monitored items from one session to another.
     *
     * @param subscriptionIds   the server-assigned identifiers for the subscriptions to transfer.
     * @param sendInitialValues if {@code true}, the first Publish response after the TransferSubscriptions service
     *                          call shall contain the current values of all monitored items in the
     *                          subscription where the {@link MonitoringMode} is set to {@link MonitoringMode#Reporting}.
     * @return a {@link CompletableFuture} containing the {@link TransferSubscriptionsResponse}.
     */
    CompletableFuture<TransferSubscriptionsResponse> transferSubscriptions(List<UInteger> subscriptionIds,
                                                                           boolean sendInitialValues);

    /**
     * This service is used to enable sending of notifications on one or more subscriptions.
     *
     * @param publishingEnabled {@code true} if publishing of notification messages is to be enabled.
     * @param subscriptionIds   a list of server-assigned subscription identifiers to enable or disable publishing on.
     * @return a {@link CompletableFuture} containing the {@link SetPublishingModeResponse}.
     */
    CompletableFuture<SetPublishingModeResponse> setPublishingMode(boolean publishingEnabled,
                                                                   List<UInteger> subscriptionIds);

    /**
     * This service is used for two purposes. First, it is used to acknowledge the receipt of notification messages for
     * one or more subscriptions. Second, it is used to request the server to return a notification message or a
     * keep-alive message.
     *
     * @param subscriptionAcknowledgements the list of acknowledgements for one or more subscriptions. This list may
     *                                     contain multiple acknowledgements for the same subscription (multiple entries
     *                                     with the same subscriptionId).
     * @return a {@link CompletableFuture} containing the {@link PublishResponse}.
     */
    CompletableFuture<PublishResponse> publish(List<SubscriptionAcknowledgement> subscriptionAcknowledgements);

    /**
     * This service requests the subscription to republish a notification message from its retransmission queue. If the
     * server does not have the requested message in its retransmission queue, it returns an error response.
     *
     * @param subscriptionId           the server-assigned identifier for the subscription to be republished.
     * @param retransmitSequenceNumber the sequence number of a specific notification message to be republished.
     * @return
     */
    CompletableFuture<RepublishResponse> republish(UInteger subscriptionId, UInteger retransmitSequenceNumber);

}
