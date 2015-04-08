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

package com.inductiveautomation.opcua.sdk.server.model;

import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;
import com.inductiveautomation.opcua.stack.core.Identifiers;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public class UaFolderNode extends UaObjectNode {

    public UaFolderNode(UaNamespace namespace, NodeId nodeId, QualifiedName browseName, LocalizedText displayName) {
        super(namespace, nodeId, browseName, displayName);

        addReference(new Reference(
                getNodeId(),
                Identifiers.HasTypeDefinition,
                Identifiers.FolderType.expanded(),
                NodeClass.ObjectType,
                true
        ));
    }

    /**
     * Add an 'Organizes' reference from this folder to {@code node} and an inverse 'Organized By' reference from
     * {@code node} back to this folder.
     *
     * @param node the node to be organized by this folder.
     */
    public void addOrganizes(UaNode node) {
        addReference(new Reference(
                getNodeId(),
                Identifiers.Organizes,
                node.getNodeId().expanded(),
                node.getNodeClass(),
                true
        ));

        node.addReference(new Reference(
                node.getNodeId(),
                Identifiers.Organizes,
                getNodeId().expanded(),
                getNodeClass(),
                false
        ));
    }

    /**
     * Remove the 'Organizes' reference from this folder to {@code node} and the inverse 'Organized By' reference from
     * {@code node} back to this folder.
     *
     * @param node the node to be organized by this folder.
     */
    public void removeOrganizes(UaNode node) {
        removeReference(new Reference(
                getNodeId(),
                Identifiers.Organizes,
                node.getNodeId().expanded(),
                node.getNodeClass(),
                true
        ));

        node.removeReference(new Reference(
                node.getNodeId(),
                Identifiers.Organizes,
                getNodeId().expanded(),
                getNodeClass(),
                false
        ));
    }

}
