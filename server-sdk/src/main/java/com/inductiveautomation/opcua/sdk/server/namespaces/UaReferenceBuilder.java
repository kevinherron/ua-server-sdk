/*
 * Copyright 2014 Inductive Automation
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

package com.inductiveautomation.opcua.sdk.server.namespaces;

import com.inductiveautomation.opcua.nodeset.ReferenceBuilder;
import com.inductiveautomation.opcua.sdk.server.api.Reference;
import com.inductiveautomation.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public class UaReferenceBuilder implements ReferenceBuilder<Reference> {

    @Override
    public Reference buildReference(NodeId sourceNodeId,
                                    NodeId referenceTypeId,
                                    ExpandedNodeId targetNodeId,
                                    NodeClass targetNodeClass,
                                    boolean forward) {

        return new Reference(sourceNodeId, referenceTypeId, targetNodeId, targetNodeClass, forward);
    }

}
