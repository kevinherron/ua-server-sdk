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
