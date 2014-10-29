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
import com.inductiveautomation.opcua.sdk.core.Reference;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.nodes.UaDataTypeNode;
import com.inductiveautomation.opcua.sdk.server.nodes.UaMethodNode;
import com.inductiveautomation.opcua.sdk.server.nodes.UaNode;
import com.inductiveautomation.opcua.sdk.server.nodes.UaObjectNode;
import com.inductiveautomation.opcua.sdk.server.nodes.UaObjectTypeNode;
import com.inductiveautomation.opcua.sdk.server.nodes.UaReferenceTypeNode;
import com.inductiveautomation.opcua.sdk.server.nodes.UaVariableNode;
import com.inductiveautomation.opcua.sdk.server.nodes.UaVariableTypeNode;
import com.inductiveautomation.opcua.sdk.server.nodes.UaViewNode;

public class UaNodeBuilder implements NodeBuilder<UaNode, Reference> {

    private final UaNodeManager nodeManager;

    public UaNodeBuilder(UaNodeManager nodeManager) {
        this.nodeManager = nodeManager;
    }

    @Override
    public UaNode buildDataTypeNode(DataTypeNodeAttributes attributes, List<Reference> references) {
        UaDataTypeNode node = new UaDataTypeNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isAbstract()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildMethodNode(MethodNodeAttributes attributes, List<Reference> references) {
        UaMethodNode node = new UaMethodNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isExecutable(),
                attributes.isUserExecutable()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildObjectNode(ObjectNodeAttributes attributes, List<Reference> references) {
        UaObjectNode node =  new UaObjectNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.getEventNotifier()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildObjectTypeNode(ObjectTypeNodeAttributes attributes, List<Reference> references) {
        UaObjectTypeNode node = new UaObjectTypeNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isAbstract()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildReferenceTypeNode(ReferenceTypeNodeAttributes attributes, List<Reference> references) {
        UaReferenceTypeNode node = new UaReferenceTypeNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isAbstract(),
                attributes.isSymmetric(),
                attributes.getInverseName()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildVariableNode(VariableNodeAttributes attributes, List<Reference> references) {
        UaVariableNode node = new UaVariableNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
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
                attributes.isHistorizing()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildVariableTypeNode(VariableTypeNodeAttributes attributes, List<Reference> references) {
        UaVariableTypeNode node = new UaVariableTypeNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.getValue(),
                attributes.getDataType(),
                attributes.getValueRank(),
                attributes.getArrayDimensions(),
                attributes.isAbstract()
        );

        node.addReferences(references);

        return node;
    }

    @Override
    public UaNode buildViewNode(ViewNodeAttributes attributes, List<Reference> references) {
        UaViewNode node = new UaViewNode(
                nodeManager,
                attributes.getNodeAttributes().getNodeId(),
                attributes.getNodeAttributes().getBrowseName(),
                attributes.getNodeAttributes().getDisplayName(),
                attributes.getNodeAttributes().getDescription(),
                attributes.getNodeAttributes().getWriteMask(),
                attributes.getNodeAttributes().getUserWriteMask(),
                attributes.isContainsNoLoops(),
                attributes.getEventNotifier()
        );

        node.addReferences(references);

        return node;
    }

}
