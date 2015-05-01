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

    ERR_CONNECTION_LOST,

    CREATE_AND_ACTIVATE_REQUESTED,
    CREATE_AND_ACTIVATE_SUCCEEDED,
    ERR_CREATE_AND_ACTIVATE_FAILED,

    CLOSE_SESSION_REQUESTED,
    CLOSE_SESSION_SUCCEEDED,
    ERR_CLOSE_SESSION_FAILED,

    CREATE_SUBSCRIPTIONS_REQUESTED,
    CREATE_SUBSCRIPTIONS_SUCCEEDED,
    ERR_CREATE_SUBSCRIPTIONS_FAILED,

    DISCONNECT_SUCCEEDED,

    REACTIVATE_REQUESTED,
    REACTIVATE_SUCCEEDED,
    ERR_REACTIVATE_FAILED,

    RESUME_SESSION_REQUESTED,
    RESUME_SESSION_SUCCEEDED,
    ERR_RESUME_SESSION_FAILED,

    REPUBLISH_REQUESTED,
    REPUBLISH_SUCCEEDED,
    ERR_REPUBLISH_MESSAGE_NOT_AVAILABLE,
    ERR_REPUBLISH_SUBSCRIPTION_ID_INVALID,

    TRANSFER_SUBSCRIPTION_REQUESTED,
    TRANSFER_SUBSCRIPTION_SUCCEEDED,
    ERR_TRANSFER_SUBSCRIPTION_FAILED

}
