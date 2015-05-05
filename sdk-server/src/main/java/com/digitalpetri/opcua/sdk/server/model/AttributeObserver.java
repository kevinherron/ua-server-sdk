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

package com.digitalpetri.opcua.sdk.server.model;

public interface AttributeObserver {

    /**
     * The Attribute indicated by {@code attributeId} on {@code node} changed.
     *
     * @param node        the {@link UaNode} the change originated from.
     * @param attributeId the id of the Attribute that changed.
     * @param value       the new value of the attribute.
     */
    void attributeChanged(UaNode node, int attributeId, Object value);

}
