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

package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.sdk.core.model.variables.PropertyType;
import com.digitalpetri.opcua.sdk.core.model.variables.TwoStateVariableType;
import com.digitalpetri.opcua.sdk.server.model.Property;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;

public interface DialogConditionType extends ConditionType {

    Property<LocalizedText> PROMPT = new Property.BasicProperty<>(
            QualifiedName.parse("0:Prompt"),
            NodeId.parse("ns=0;i=21"),
            -1,
            LocalizedText.class
    );

    Property<LocalizedText[]> RESPONSE_OPTION_SET = new Property.BasicProperty<>(
            QualifiedName.parse("0:ResponseOptionSet"),
            NodeId.parse("ns=0;i=21"),
            1,
            LocalizedText[].class
    );

    Property<Integer> DEFAULT_RESPONSE = new Property.BasicProperty<>(
            QualifiedName.parse("0:DefaultResponse"),
            NodeId.parse("ns=0;i=6"),
            -1,
            Integer.class
    );

    Property<Integer> OK_RESPONSE = new Property.BasicProperty<>(
            QualifiedName.parse("0:OkResponse"),
            NodeId.parse("ns=0;i=6"),
            -1,
            Integer.class
    );

    Property<Integer> CANCEL_RESPONSE = new Property.BasicProperty<>(
            QualifiedName.parse("0:CancelResponse"),
            NodeId.parse("ns=0;i=6"),
            -1,
            Integer.class
    );

    Property<Integer> LAST_RESPONSE = new Property.BasicProperty<>(
            QualifiedName.parse("0:LastResponse"),
            NodeId.parse("ns=0;i=6"),
            -1,
            Integer.class
    );

    LocalizedText getPrompt();

    PropertyType getPromptNode();

    void setPrompt(LocalizedText value);

    LocalizedText[] getResponseOptionSet();

    PropertyType getResponseOptionSetNode();

    void setResponseOptionSet(LocalizedText[] value);

    Integer getDefaultResponse();

    PropertyType getDefaultResponseNode();

    void setDefaultResponse(Integer value);

    Integer getOkResponse();

    PropertyType getOkResponseNode();

    void setOkResponse(Integer value);

    Integer getCancelResponse();

    PropertyType getCancelResponseNode();

    void setCancelResponse(Integer value);

    Integer getLastResponse();

    PropertyType getLastResponseNode();

    void setLastResponse(Integer value);

    LocalizedText getEnabledState();

    TwoStateVariableType getEnabledStateNode();

    void setEnabledState(LocalizedText value);

    LocalizedText getDialogState();

    TwoStateVariableType getDialogStateNode();

    void setDialogState(LocalizedText value);
}
