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

package com.digitalpetri.opcua.sdk.client.api.services;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.digitalpetri.opcua.stack.core.types.structured.AddNodesItem;
import com.digitalpetri.opcua.stack.core.types.structured.AddNodesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.AddReferencesItem;
import com.digitalpetri.opcua.stack.core.types.structured.AddReferencesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteNodesItem;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteNodesResponse;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteReferencesItem;
import com.digitalpetri.opcua.stack.core.types.structured.DeleteReferencesResponse;

public interface NodeManagementServices {

    CompletableFuture<AddNodesResponse> addNodes(List<AddNodesItem> nodesToAdd);

    CompletableFuture<AddReferencesResponse> addReferences(List<AddReferencesItem> referencesToAdd);

    CompletableFuture<DeleteNodesResponse> deleteNodes(List<DeleteNodesItem> nodesToDelete);

    CompletableFuture<DeleteReferencesResponse> deleteReferences(List<DeleteReferencesItem> referencesToDelete);

}
