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

package com.inductiveautomation.opcua.sdk.server.services;

import com.inductiveautomation.opcua.sdk.server.NamespaceManager;
import com.inductiveautomation.opcua.sdk.server.services.helpers.BrowseHelper;
import com.inductiveautomation.opcua.sdk.server.services.helpers.TranslateBrowsePathsHelper;
import com.inductiveautomation.opcua.stack.core.application.services.ServiceRequest;
import com.inductiveautomation.opcua.stack.core.application.services.ViewServiceSet;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseNextRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseNextResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.BrowseResponse;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsRequest;
import com.inductiveautomation.opcua.stack.core.types.structured.TranslateBrowsePathsToNodeIdsResponse;

public class ViewServices implements ViewServiceSet {

    private final ServiceMetric browseCounter = new ServiceMetric();
    private final ServiceMetric browseNextCounter = new ServiceMetric();
    private final ServiceMetric translateBrowsePathsCounter = new ServiceMetric();

    private final BrowseHelper browseHelper = new BrowseHelper();

    @Override
    public void onBrowse(ServiceRequest<BrowseRequest, BrowseResponse> service) {
        browseCounter.record(service);

        browseHelper.browse(service);
    }

    @Override
    public void onBrowseNext(ServiceRequest<BrowseNextRequest, BrowseNextResponse> service) {
        browseNextCounter.record(service);

        browseHelper.browseNext(service);
    }

    @Override
    public void onTranslateBrowsePaths(ServiceRequest<TranslateBrowsePathsToNodeIdsRequest, TranslateBrowsePathsToNodeIdsResponse> service) {
        translateBrowsePathsCounter.record(service);

        NamespaceManager namespaceManager = service.attr(ServiceAttributes.ServerKey).get().getNamespaceManager();

        new TranslateBrowsePathsHelper(namespaceManager).onTranslateBrowsePaths(service);
    }

}
