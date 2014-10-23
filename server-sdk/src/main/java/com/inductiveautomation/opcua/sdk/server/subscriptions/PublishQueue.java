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

package com.inductiveautomation.opcua.sdk.server.subscriptions;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

import com.google.common.collect.Lists;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.PublishResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class PublishQueue {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final LinkedList<ServiceRequest<PublishRequest, PublishResponse>> serviceQueue = new LinkedList<>();

    private final LinkedHashMap<UInteger, WaitingSubscription> waitList = new LinkedHashMap<>();

    /**
     * Add a Publish {@link ServiceRequest} to the queue.
     * <p>
     * If there are wait-listed Subscriptions this request will be used immediately, otherwise it will be queued for
     * later use by a Subscription whose publish timer has expired and has notifications to send.
     *
     * @param service the Publish {@link ServiceRequest}.
     */
    public synchronized void addRequest(ServiceRequest<PublishRequest, PublishResponse> service) {
        List<WaitingSubscription> waitingSubscriptions = Lists.newArrayList(waitList.values());

        if (waitingSubscriptions.isEmpty()) {
            serviceQueue.add(service);

            logger.debug("Queued PublishRequest, size={}", serviceQueue.size());
        } else {
            WaitingSubscription subscription = null;

            int maxPriority = 0;
            long minWaitingSince = Long.MAX_VALUE;

            for (WaitingSubscription waiting : waitingSubscriptions) {
                int priority = waiting.subscription.getPriority();
                long waitingSince = waiting.waitingSince.getTime();

                if (priority > maxPriority) {
                    maxPriority = priority;
                    minWaitingSince = Long.MAX_VALUE;
                }
                if (priority >= maxPriority && waitingSince < minWaitingSince) {
                    minWaitingSince = waitingSince;
                    subscription = waiting;
                }
            }

            if (subscription != null) {
                waitList.remove(subscription.subscription.getId());

                logger.debug("Delivering PublishRequest to Subscription [id={}]",
                        subscription.getSubscription().getId());

                subscription.subscription.onPublish(service);
            } else {
                serviceQueue.add(service);
            }
        }
    }

    /**
     * Add a subscription to the wait list.
     * <p>
     * A subscription should be added to the wait list when either:
     * <p>
     * a) The previous Publish response indicated that there were still more Notifications ready to be transferred and
     * there were no more Publish requests queued to transfer them.
     * <p>
     * b) The publishing timer of a Subscription expired and there were either Notifications to be sent or a keep-alive
     * Message to be sent.
     *
     * @param subscription the subscription to wait-list.
     */
    public synchronized void addSubscription(Subscription subscription) {
        if (waitList.isEmpty() && !serviceQueue.isEmpty()) {
            subscription.onPublish(serviceQueue.poll());
        } else {
            waitList.putIfAbsent(subscription.getId(), new WaitingSubscription(subscription));
        }
    }

    public boolean isEmpty() {
        return serviceQueue.isEmpty();
    }

    public boolean isNotEmpty() {
        return !isEmpty();
    }

    public ServiceRequest<PublishRequest, PublishResponse> poll() {
        return serviceQueue.poll();
    }

    public static class WaitingSubscription {

        private final Date waitingSince = new Date();

        private final Subscription subscription;

        public WaitingSubscription(Subscription subscription) {
            this.subscription = subscription;
        }

        public Subscription getSubscription() {
            return subscription;
        }

        public Date getWaitingSince() {
            return waitingSince;
        }

    }

}
