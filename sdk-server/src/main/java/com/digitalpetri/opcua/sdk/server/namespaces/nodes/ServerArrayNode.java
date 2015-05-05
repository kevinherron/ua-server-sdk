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

package com.digitalpetri.opcua.sdk.server.namespaces.nodes;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.ServerTable;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.model.UaPropertyNode;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.types.builtin.DataValue;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.Variant;

import static com.digitalpetri.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class ServerArrayNode extends UaPropertyNode {

    private final ServerTable serverTable;

    public ServerArrayNode(UaNamespace nodeManager, ServerTable serverTable) {
        super(nodeManager,
                Identifiers.Server_ServerArray,
                new QualifiedName(0, "ServerArray"),
                LocalizedText.english("ServerArray"));

        this.serverTable = serverTable;

        setDataType(Identifiers.String);
        setWriteMask(Optional.of(uint(0)));
        setUserWriteMask(Optional.of(uint(0)));
    }

    @Override
    public DataValue getValue() {
        return new DataValue(new Variant(serverTable.toArray()));
    }

}
