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

package com.inductiveautomation.opcua.sdk.server.api.nodes;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;

import com.google.common.base.Preconditions;
import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimaps;
import com.inductiveautomation.opcua.sdk.server.api.Reference;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.NodeClass;

public class UaMethodNode extends UaNode implements MethodNode {

    private final ListMultimap<NodeId, Reference> referenceMap =
            Multimaps.synchronizedListMultimap(ArrayListMultimap.create());

    private final AtomicBoolean executable;
    private final AtomicBoolean userExecutable;

    public UaMethodNode(NodeId nodeId,
                        NodeClass nodeClass,
                        QualifiedName browseName,
                        LocalizedText displayName,
                        Optional<LocalizedText> description,
                        Optional<UInteger> writeMask,
                        Optional<UInteger> userWriteMask,
                        boolean executable,
                        boolean userExecutable,
                        List<Reference> references) {

        super(nodeId, nodeClass, browseName, displayName, description, writeMask, userWriteMask);

        Preconditions.checkArgument(nodeClass == NodeClass.Method);

        this.executable = new AtomicBoolean(executable);
        this.userExecutable = new AtomicBoolean(userExecutable);

        references.stream().forEach(reference -> {
            referenceMap.put(reference.getReferenceTypeId(), reference);
        });
    }

    @Override
    public void addReference(Reference reference) {
        referenceMap.put(reference.getReferenceTypeId(), reference);
    }

    @Override
    public List<Reference> getReferences() {
        synchronized (referenceMap) {
            return ImmutableList.copyOf(referenceMap.values());
        }
    }

    @Override
    public boolean isExecutable() {
        return executable.get();
    }

    @Override
    public boolean isUserExecutable() {
        return userExecutable.get();
    }

    public void setExecutable(boolean executable) {
        this.executable.set(executable);
    }

    public void setUserExecutable(boolean userExecutable) {
        this.userExecutable.set(userExecutable);
    }

}
