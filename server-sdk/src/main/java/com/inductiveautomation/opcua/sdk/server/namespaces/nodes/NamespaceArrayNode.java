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

package com.inductiveautomation.opcua.sdk.server.namespaces.nodes;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.NamespaceTable;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.sdk.server.model.UaPropertyNode;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;

import static com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.Unsigned.uint;

public class NamespaceArrayNode extends UaPropertyNode {

    private final NamespaceTable namespaceTable;

    public NamespaceArrayNode(UaNamespace nodeManager, NamespaceTable namespaceTable) {
        super(nodeManager,
                Identifiers.Server_NamespaceArray,
                new QualifiedName(0, "NamespaceArray"),
                LocalizedText.english("NamespaceArray"));

        this.namespaceTable = namespaceTable;

        setDataType(Identifiers.String);
        setWriteMask(Optional.of(uint(0)));
        setUserWriteMask(Optional.of(uint(0)));
    }

    @Override
    public DataValue getValue() {
        return new DataValue(new Variant(namespaceTable.toArray()));
    }

}
