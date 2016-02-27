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

import com.digitalpetri.opcua.sdk.core.Reference;
import com.digitalpetri.opcua.sdk.server.api.UaNodeManager;
import com.digitalpetri.opcua.stack.core.Identifiers;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

public class UaFolderNode extends UaObjectNode {

    public UaFolderNode(UaNodeManager nodeManager, NodeId nodeId, QualifiedName browseName, LocalizedText displayName) {
        super(nodeManager, nodeId, browseName, displayName);

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
