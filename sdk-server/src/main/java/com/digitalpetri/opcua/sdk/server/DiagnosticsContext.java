/*
 * Copyright 2015
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
