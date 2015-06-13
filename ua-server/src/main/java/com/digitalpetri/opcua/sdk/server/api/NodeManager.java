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

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;

import com.digitalpetri.opcua.sdk.server.DiagnosticsContext;
import com.digitalpetri.opcua.sdk.server.OpcUaServer;
import com.digitalpetri.opcua.sdk.server.Session;
import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.StatusCode;
import com.digitalpetri.opcua.stack.core.types.structured.AddNodesItem;
import com.digitalpetri.opcua.stack.core.types.structured.AddNodesResult;
import com.digitalpetri.opcua.stack.core.types.structured.AddReferencesItem;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteNodesItem;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteReferencesItem;

public interface NodeManager {

    default void addNode(AddNodesContext context, List<AddNodesItem> nodesToAdd) {
        AddNodesResult result = new AddNodesResult(
                new StatusCode(StatusCodes.Bad_NotSupported),
                NodeId.NULL_VALUE);

        context.getFuture().complete(Collections.nCopies(nodesToAdd.size(), result));
    }


    default void deleteNode(DeleteNodesContext context, List<DeleteNodesItem> nodesToDelete) {
        StatusCode statusCode = new StatusCode(StatusCodes.Bad_NotSupported);

        context.getFuture().complete(Collections.nCopies(nodesToDelete.size(), statusCode));
    }

    default void addReference(AddReferencesContext context, List<AddReferencesItem> referencesToAdd) {
        StatusCode statusCode = new StatusCode(StatusCodes.Bad_NotSupported);

        context.getFuture().complete(Collections.nCopies(referencesToAdd.size(), statusCode));
    }

    default void deleteReference(DeleteReferencesContext context, List<DeleteReferencesItem> referencesToDelete) {
        StatusCode statusCode = new StatusCode(StatusCodes.Bad_NotSupported);

        context.getFuture().complete(Collections.nCopies(referencesToDelete.size(), statusCode));
    }

    final class AddNodesContext extends OperationContext<AddNodesItem, AddNodesResult> {
        public AddNodesContext(OpcUaServer server, @Nullable Session session,
                               DiagnosticsContext<AddNodesItem> diagnosticsContext) {

            super(server, session, diagnosticsContext);
        }
    }

    final class DeleteNodesContext extends OperationContext<DeleteNodesItem, StatusCode> {
        public DeleteNodesContext(OpcUaServer server, @Nullable Session session,
                                  DiagnosticsContext<DeleteNodesItem> diagnosticsContext) {

            super(server, session, diagnosticsContext);
        }
    }

    final class AddReferencesContext extends OperationContext<AddReferencesItem, StatusCode> {
        public AddReferencesContext(OpcUaServer server, @Nullable Session session,
                                    DiagnosticsContext<AddReferencesItem> diagnosticsContext) {

            super(server, session, diagnosticsContext);
        }
    }

    final class DeleteReferencesContext extends OperationContext<DeleteReferencesItem, StatusCode> {
        public DeleteReferencesContext(OpcUaServer server, @Nullable Session session,
                                       DiagnosticsContext<DeleteReferencesItem> diagnosticsContext) {

            super(server, session, diagnosticsContext);
        }
    }

}
