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
