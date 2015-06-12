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
    ERR_SESSION_INVALID,

    SESSION_REQUESTED,

    CREATE_REQUESTED,
    CREATE_SUCCEEDED,
    ERR_CREATE_FAILED,

    ACTIVATE_REQUESTED,
    ACTIVATE_SUCCEEDED,
    ERR_ACTIVATE_FAILED,

    DISCONNECT_REQUESTED,
    DISCONNECT_SUCCEEDED,

    CREATE_SUBSCRIPTIONS_REQUESTED,
    CREATE_SUBSCRIPTIONS_SUCCEEDED,
    ERR_CREATE_SUBSCRIPTIONS_FAILED,

    REACTIVATE_REQUESTED,
    REACTIVATE_SUCCEEDED,
    ERR_REACTIVATE_FAILED,
    ERR_REACTIVATE_INVALID,

    REPUBLISH_REQUESTED,
    REPUBLISH_SUCCEEDED,
    ERR_REPUBLISH_FAILED,
    ERR_REPUBLISH_UNSUPPORTED,

    TRANSFER_REQUESTED,
    TRANSFER_SUCCEEDED,
    ERR_TRANSFER_FAILED,
    ERR_TRANSFER_UNSUPPORTED;

}
