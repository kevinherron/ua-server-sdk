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
