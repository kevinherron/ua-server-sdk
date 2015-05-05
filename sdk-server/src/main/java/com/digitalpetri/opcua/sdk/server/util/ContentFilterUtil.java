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

package com.digitalpetri.opcua.sdk.server.util;

import java.util.Arrays;

import com.digitalpetri.opcua.stack.core.StatusCodes;
import com.digitalpetri.opcua.stack.core.UaException;
import com.digitalpetri.opcua.stack.core.types.builtin.ExtensionObject;
import com.digitalpetri.opcua.stack.core.types.enumerated.FilterOperator;
import com.digitalpetri.opcua.stack.core.types.structured.AttributeOperand;
import com.digitalpetri.opcua.stack.core.types.structured.ContentFilter;
import com.digitalpetri.opcua.stack.core.types.structured.ContentFilterElement;
import com.digitalpetri.opcua.stack.core.types.structured.FilterOperand;
import com.digitalpetri.opcua.stack.core.types.structured.SimpleAttributeOperand;

public class ContentFilterUtil {

    /*
     * Operands: SimpleAttributeOperand
     * Operators: Equals, IsNull, GreaterThan, LessThan, GreaterThanOrEqual, LessThanOrEqual, Like, Not, Between,
     * InList, And, Or, Cast, BitwiseAnd, BitwiseOr and OfType.
     */

    public void apply(ContentFilter filter) throws UaException {
        ContentFilterElement[] elements = filter.getElements();

        for (int i = 0; i < elements.length; i++) {
            ContentFilterElement element = elements[i];

            FilterOperator operator = element.getFilterOperator();
            SimpleAttributeOperand[] operands = extract(element.getFilterOperands());

            throw new UaException(StatusCodes.Bad_FilterOperandInvalid);
        }
    }

    private SimpleAttributeOperand[] extract(ExtensionObject[] operandXos) {
        return Arrays.stream(operandXos)
                .map(xo -> (SimpleAttributeOperand) xo.getObject())
                .toArray(SimpleAttributeOperand[]::new);
    }

    private boolean apply(FilterOperator operator, FilterOperand[] operands) {
        if (operator == FilterOperator.Equals) {
            SimpleAttributeOperand op0 = (SimpleAttributeOperand) operands[0];
            AttributeOperand op1 = (AttributeOperand) operands[1];

            op0.getAttributeId();
            op0.getBrowsePath();
            op0.getIndexRange();
            op0.getTypeDefinitionId();
        }

        return false;
    }

    private class EqualsOperator {

        public void apply(FilterOperand[] operands) throws UaException {

        }

    }

}
