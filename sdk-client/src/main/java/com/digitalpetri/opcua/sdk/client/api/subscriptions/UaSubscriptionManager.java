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

package com.digitalpetri.opcua.sdk.client.api.subscriptions;

import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.sdk.client.api.UaSession;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.DateTime;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UByte;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.google.common.collect.ImmutableList;

public interface UaSubscriptionManager {

    /**
     * Create a {@link UaSubscription} using default parameters.
     *
     * @param requestedPublishingInterval the requested publishing interval of the subscription.
     * @return a {@link CompletableFuture} containing the {@link UaSubscription}.
     */
    CompletableFuture<UaSubscription> createSubscription(double requestedPublishingInterval);

    /**
     * Create a {@link UaSubscription}.
     *
     * @param requestedPublishingInterval the requested publishing interval.
     * @param requestedLifetimeCount      the requested lifetime count.
     * @param requestedMaxKeepAliveCount  the requested max keep-alive count.
     * @param maxNotificationsPerPublish  the maximum number of notifications allowed in a publish response.
     * @param priority                    the relative priority to assign to the subscription.
     * @return a {@link CompletableFuture} containing the {@link UaSubscription}.
     */
    CompletableFuture<UaSubscription> createSubscription(double requestedPublishingInterval,
                                                         UInteger requestedLifetimeCount,
                                                         UInteger requestedMaxKeepAliveCount,
                                                         UInteger maxNotificationsPerPublish,
                                                         boolean publishingEnabled,
                                                         UByte priority);

    /**
     * Request a new publishing interval for a {@link UaSubscription}.
     * <p>
     * The requested max keep-alive count and lifetime count will be derived from the requested publishing interval.
     *
     * @param subscriptionId              the server-assigned id of the {@link UaSubscription} to modify.
     * @param requestedPublishingInterval the requested publishing interval.
     * @return a {@link CompletableFuture} containing the {@link UaSubscription}.
     */
    CompletableFuture<UaSubscription> modifySubscription(UInteger subscriptionId,
                                                         double requestedPublishingInterval);

    /**
     * Modify a {@link UaSubscription}.
     *
     * @param subscriptionId              the server-assigned id of the {@link UaSubscription} to modify.
     * @param requestedPublishingInterval the requested publishing interval.
     * @param requestedLifetimeCount      the requested lifetime count.
     * @param requestedMaxKeepAliveCount  the requested max keep-alive count.
     * @param maxNotificationsPerPublish  the maximum number of notifications allowed in a publish response.
     * @param priority                    the relative priority to assign to the subscription.
     * @return a {@link CompletableFuture} containing the {@link UaSubscription}.
     */
    public CompletableFuture<UaSubscription> modifySubscription(UInteger subscriptionId,
                                                                double requestedPublishingInterval,
                                                                UInteger requestedLifetimeCount,
                                                                UInteger requestedMaxKeepAliveCount,
                                                                UInteger maxNotificationsPerPublish,
                                                                UByte priority);

    /**
     * Delete a {@link UaSubscription}.
     *
     * @param subscriptionId the server-assigned id of the {@link UaSubscription} to delete.
     * @return a {@link CompletableFuture} containing the deleted {@link UaSubscription}.
     */
    CompletableFuture<UaSubscription> deleteSubscription(UInteger subscriptionId);

    /**
     * @return an {@link ImmutableList} of {@link UaSubscription}s managed by this {@link UaSubscriptionManager}.
     */
    ImmutableList<UaSubscription> getSubscriptions();

    /**
     * Add a {@link SubscriptionListener}.
     *
     * @param listener the {@link SubscriptionListener} to add.
     */
    void addSubscriptionListener(SubscriptionListener listener);

    /**
     * Remove a {@link SubscriptionListener}.
     *
     * @param listener the {@link SubscriptionListener} to remove.
     */
    void removeSubscriptionListener(SubscriptionListener listener);

    interface SubscriptionListener {

        /**
         * A keep-alive message was received.
         *
         * @param subscription the {@link UaSubscription} that received the keep-alive.
         * @param publishTime  the time the server published the keep-alive.
         */
        void onKeepAlive(UaSubscription subscription, DateTime publishTime);

        /**
         * A status change notification was received.
         *
         * @param subscription the {@link UaSubscription} that received the status change.
         * @param status       the new subscription status.
         */
        void onStatusChanged(UaSubscription subscription, StatusCode status);

        /**
         * A publish failure has occurred.
         * <p>
         * No additional action is required.
         *
         * @param exception the cause of the failure.
         */
        void onPublishFailure(UaException exception);

        /**
         * Attempts to recover missed notification data have failed.
         * <p>
         * When a notification is missed a series of Republish requests are initiated to recover the missing data. If
         * republishing fails, or any of the notifications are no longer available, this callback will be invoked.
         *
         * @param subscription the subscription that missed notification data.
         */
        void onNotificationDataLost(UaSubscription subscription);

        /**
         * A new {@link UaSession} was established, and upon attempting to transfer an existing subscription to this
         * new session, a failure occurred.
         * <p>
         * This subscription will be removed from {@link UaSubscriptionManager}'s bookkeeping. It must be re-created.
         *
         * @param subscription the {@link UaSubscription} that could not be transferred.
         * @param statusCode   the {@link StatusCode} for the transfer failure.
         */
        void onSubscriptionTransferFailed(UaSubscription subscription, StatusCode statusCode);

    }

}
