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

package com.digitalpetri.opcua.sdk.server;

import java.util.EnumSet;
import java.util.Map;

import com.digitalpetri.opcua.stack.core.types.builtin.DiagnosticInfo;
import com.google.common.collect.Maps;

public class DiagnosticsContext<T> {

    private final Map<T, DiagnosticInfo> diagnosticsMap = Maps.newConcurrentMap();

    public EnumSet<OperationDiagnostic> getRequestedOperationDiagnostics(T t) {
        return null;
    }

    public void setOperationDiagnostic(T t, DiagnosticInfo diagnosticInfo) {

    }

    public Map<T, DiagnosticInfo> getDiagnosticsMap() {
        return diagnosticsMap;
    }

    public DiagnosticInfo[] getDiagnosticInfos(T[] ts) {
        if (diagnosticsMap.isEmpty()) {
            return new DiagnosticInfo[0];
        } else {
            DiagnosticInfo[] diagnostics = new DiagnosticInfo[ts.length];

            for (int i = 0; i < ts.length; i++) {
                DiagnosticInfo diagnosticInfo = diagnosticsMap.getOrDefault(
                        ts[i], DiagnosticInfo.NULL_VALUE);

                diagnostics[i] = diagnosticInfo;
            }

            return diagnostics;
        }
    }

    public enum OperationDiagnostic {
        SYMBOLIC_ID,
        LOCALIZED_TEXT,
        INNER_STATUS_CODE,
        INNER_DIAGNOSTICS
    }

}
