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

package com.digitalpetri.opcua.sdk.server.api;

import java.util.Optional;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

public interface NodeManager {

    default NodeId addNode(Optional<ExpandedNodeId> requestedNodeId,
                           QualifiedName browseName,
                           NodeClass nodeClass,
                           ExtensionObject nodeAttributes,
                           ExpandedNodeId typeDefinition) throws UaException {

        throw new UaException(StatusCodes.Bad_NotSupported);
    }


    default void deleteNode(NodeId nodeId, boolean deleteTargetReferences) throws UaException {
        throw new UaException(StatusCodes.Bad_NotSupported);
    }

    default void addReference(NodeId sourceNodeId,
                              NodeId referenceTypeId,
                              boolean forward,
                              String targetServerUri,
                              ExpandedNodeId targetNodeId,
                              NodeClass targetNodeClass) throws UaException {

        throw new UaException(StatusCodes.Bad_NotSupported);
    }

    default void deleteReference(NodeId sourceNodeId,
                                 NodeId referenceTypeId,
                                 boolean forward,
                                 ExpandedNodeId targetNodeId,
                                 boolean bidirectional) throws UaException {

        throw new UaException(StatusCodes.Bad_NotSupported);
    }

}
