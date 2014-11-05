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

import com.inductiveautomation.opcua.sdk.server.api.UaNamespace;

public abstract class DerivedVariableNode extends UaVariableNode {

    public DerivedVariableNode(UaNamespace namespace, UaVariableNode variableNode) {

        super(namespace,
                variableNode.getNodeId(),
                variableNode.getBrowseName(),
                variableNode.getDisplayName(),
                variableNode.getDescription(),
                variableNode.getWriteMask(),
                variableNode.getUserWriteMask(),
                variableNode.getValue(),
                variableNode.getDataType(),
                variableNode.getValueRank(),
                variableNode.getArrayDimensions(),
                variableNode.getAccessLevel(),
                variableNode.getUserAccessLevel(),
                variableNode.getMinimumSamplingInterval(),
                variableNode.getHistorizing());

        addReferences(variableNode.getReferences());
    }

}
