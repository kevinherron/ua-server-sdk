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
