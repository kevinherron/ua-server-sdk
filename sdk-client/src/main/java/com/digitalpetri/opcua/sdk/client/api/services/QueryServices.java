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

import com.digitalpetri.opcua.stack.core.types.builtin.ByteString;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.structured.ContentFilter;
import com.digitalpetri.opcua.stack.core.types.structured.NodeTypeDescription;
import com.digitalpetri.opcua.stack.core.types.structured.QueryDataSet;
import com.digitalpetri.opcua.stack.core.types.structured.QueryFirstResponse;
import com.digitalpetri.opcua.stack.core.types.structured.QueryNextResponse;
import com.digitalpetri.opcua.stack.core.types.structured.ViewDescription;

public interface QueryServices {

    /**
     * This service is used to issue a query to the server.
     *
     * @param view                  specifies a View and temporal context to a server.
     * @param nodeTypes             the {@link NodeTypeDescription}.
     * @param filter                the {@link ContentFilter}. Resulting Nodes shall be limited to the Nodes matching
     *                              the criteria defined by the filter.
     * @param maxDataSetsToReturn   the number of {@link QueryDataSet}s that the client wants the server to return in
     *                              the response and on each subsequent continuation call response. The server is
     *                              allowed to further limit the response, but shall not exceed this limit. A value of 0
     *                              indicates that the client is imposing no limitation.
     * @param maxReferencesToReturn the number of References that the client wants the server to return in the response
     *                              for each {@link QueryDataSet} and on each subsequent continuation call response. The
     *                              server is allowed to further limit the response, but shall not exceed this limit. A
     *                              value of 0 indicates that the client is imposing no limitation.
     * @return a {@link CompletableFuture} containing the {@link QueryFirstResponse}.
     */
    CompletableFuture<QueryFirstResponse> queryFirst(ViewDescription view,
                                                     List<NodeTypeDescription> nodeTypes,
                                                     ContentFilter filter,
                                                     UInteger maxDataSetsToReturn,
                                                     UInteger maxReferencesToReturn);

    /**
     * This Service is used to request the next set of QueryFirst or QueryNext response information that is too large to
     * be sent in a single response.
     *
     * @param releaseContinuationPoint if {@code true}, passed continuationPoints shall be reset to free resources in
     *                                 the server. If {@code false}, passed continuationPoints shall be used to get the
     *                                 next set of browse information.
     * @param continuationPoint        a server-defined opaque value that represents the continuation point.
     * @return a {@link CompletableFuture} containing the {@link QueryNextResponse}.
     */
    CompletableFuture<QueryNextResponse> queryNext(boolean releaseContinuationPoint,
                                                   ByteString continuationPoint);

}
