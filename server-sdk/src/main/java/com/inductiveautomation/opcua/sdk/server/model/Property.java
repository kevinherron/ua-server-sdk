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

package com.inductiveautomation.opcua.sdk.server.model;

import java.util.Optional;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;

public interface Property<T> {

    QualifiedName getBrowseName();

    NodeId getDataType();

    Integer getValueRank();

    Class<T> getJavaType();

    default Optional<UInteger[]> getArrayDimensions() {
        return Optional.empty();
    }

    public static class BasicProperty<T> implements Property<T> {
        private final QualifiedName browseName;
        private final NodeId dataType;
        private final Integer valueRank;
        private final Class<T> javaType;

        public BasicProperty(QualifiedName browseName, NodeId dataType, Integer valueRank, Class<T> javaType) {
            this.browseName = browseName;
            this.dataType = dataType;
            this.valueRank = valueRank;
            this.javaType = javaType;
        }

        @Override
        public QualifiedName getBrowseName() {
            return browseName;
        }

        @Override
        public NodeId getDataType() {
            return dataType;
        }

        @Override
        public Integer getValueRank() {
            return valueRank;
        }

        @Override
        public Class<T> getJavaType() {
            return javaType;
        }
    }
}
