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

import java.util.List;

import com.inductiveautomation.opcua.nodeset.NodeBuilder;
import com.inductiveautomation.opcua.nodeset.attributes.DataTypeNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.MethodNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.ObjectNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.ObjectTypeNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.ReferenceTypeNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.VariableNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.VariableTypeNodeAttributes;
import com.inductiveautomation.opcua.nodeset.attributes.ViewNodeAttributes;
import com.inductiveautomation.opcua.sdk.server.api.Reference;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaDataTypeNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaMethodNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaObjectNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaObjectTypeNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaReferenceTypeNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaVariableNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaVariableTypeNode;
import com.inductiveautomation.opcua.sdk.server.api.nodes.UaViewNode;

public class UaNodeBuilder implements NodeBuilder<UaNode, Reference> {

    @Override
    public UaNode buildDataTypeNode(DataTypeNodeAttributes attributes, List<Reference> references) {
        return new UaDataTypeNode(
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getNodeClass(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isAbstract(),
                references
        );
    }

    @Override
    public UaNode buildMethodNode(MethodNodeAttributes attributes, List<Reference> references) {
        return new UaMethodNode(
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getNodeClass(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isExecutable(),
                attributes.isUserExecutable(),
                references
        );
    }

    @Override
    public UaNode buildObjectNode(ObjectNodeAttributes attributes, List<Reference> references) {
        return new UaObjectNode(
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getNodeClass(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.getEventNotifier(),
                references
        );
    }

    @Override
    public UaNode buildObjectTypeNode(ObjectTypeNodeAttributes attributes, List<Reference> references) {
        return new UaObjectTypeNode(
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getNodeClass(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isAbstract(),
                references
        );
    }

    @Override
    public UaNode buildReferenceTypeNode(ReferenceTypeNodeAttributes attributes, List<Reference> references) {
        return new UaReferenceTypeNode(
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getNodeClass(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isAbstract(),
                attributes.isSymmetric(),
                attributes.getInverseName(),
                references
        );
    }

    @Override
    public UaNode buildVariableNode(VariableNodeAttributes attributes, List<Reference> references) {
        return new UaVariableNode(
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getNodeClass(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.getValue(),
                attributes.getDataType(),
                attributes.getValueRank(),
                attributes.getArrayDimensions(),
                attributes.getAccessLevel(),
                attributes.getUserAccessLevel(),
                attributes.getMinimumSamplingInterval(),
                attributes.isHistorizing(),
                references
        );
    }

    @Override
    public UaNode buildVariableTypeNode(VariableTypeNodeAttributes attributes, List<Reference> references) {
        return new UaVariableTypeNode(
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getNodeClass(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.getValue(),
                attributes.getDataType(),
                attributes.getValueRank(),
                attributes.getArrayDimensions(),
                attributes.isAbstract(),
                references
        );
    }

    @Override
    public UaNode buildViewNode(ViewNodeAttributes attributes, List<Reference> references) {
        return new UaViewNode(
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getNodeClass(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isContainsNoLoops(),
                attributes.getEventNotifier(),
                references
        );
    }
}
