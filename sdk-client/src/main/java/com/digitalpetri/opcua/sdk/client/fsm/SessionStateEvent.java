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

package com.digitalpetri.opcua.sdk.client.fsm;

public enum SessionStateEvent {

    CONNECTION_LOST,

    CREATE_SESSION_REQUESTED,
    CREATE_SESSION_SUCCEEDED,
    CREATE_SESSION_FAILED,

    ACTIVATE_SESSION_REQUESTED,
    ACTIVATE_SESSION_SUCCEEDED,
    ACTIVATE_SESSION_FAILED,

    CREATE_SUBSCRIPTIONS_REQUESTED,
    CREATE_SUBSCRIPTIONS_SUCCEEDED,
    CREATE_SUBSCRIPTIONS_FAILED,

    RESUME_SESSION_REQUESTED,
    RESUME_SESSION_SUCCEEDED,
    RESUME_SESSION_FAILED,

    REPUBLISH_REQUESTED,
    REPUBLISH_SUCCEEDED,
    REPUBLISH_FAILED_MESSAGE_NOT_AVAILABLE,
    REPUBLISH_FAILED_SUBSCRIPTION_ID_INVALID,

    TRANSFER_SUBSCRIPTION_REQUESTED,
    TRANSFER_SUBSCRIPTION_SUCCEEDED,
    TRANSFER_SUBSCRIPTION_FAILED

}
