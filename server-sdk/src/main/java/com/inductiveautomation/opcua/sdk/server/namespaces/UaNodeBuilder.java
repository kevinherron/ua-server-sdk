/*
 * OPC-UA SDK
 *
 * Copyright (C) 2014 Kevin Herron
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
