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

package com.digitalpetri.opcua.sdk.server.namespaces.loader;

import com.digitalpetri.opcua.sdk.server.api.UaNamespace;

public class UaNodeLoader {

    private final UaNamespace nodeManager;

    public UaNodeLoader(UaNamespace nodeManager)
            throws Exception {
        this.nodeManager = nodeManager;
        new UaDataTypeLoader(nodeManager).buildNodes();
        new UaMethodLoader(nodeManager).buildNodes();
        new UaObjectLoader(nodeManager).buildNodes();
        new UaObjectTypeLoader(nodeManager).buildNodes();
        new UaReferenceTypeLoader(nodeManager).buildNodes();
        new UaVariableLoader(nodeManager).buildNodes();
        new UaVariableTypeLoader(nodeManager).buildNodes();
        new UaViewLoader(nodeManager).buildNodes();
    }

}
