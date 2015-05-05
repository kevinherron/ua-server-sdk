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

package com.digitalpetri.opcua.sdk.server.model;

import java.util.Optional;

import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;

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
