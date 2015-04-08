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

package com.inductiveautomation.opcua.sdk.server.api;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import com.inductiveautomation.opcua.stack.core.types.builtin.ByteString;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseDescription;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseResult;
import com.inductiveautomation.opcua.stack.core.types.structured.ViewDescription;

public interface ViewManager {

    void browse(ViewDescription view,
                UInteger maxReferencesPerNode,
                List<BrowseDescription> browseDescription,
                CompletableFuture<List<BrowseResult>> future);

    void browseNext(List<ByteString> continuationPoints,
                    CompletableFuture<List<BrowseResult>> future);

}
