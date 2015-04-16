package com.digitalpetri.opcua.sdk.core.model.objects;

import com.digitalpetri.opcua.stack.core.types.enumerated.NamingRuleType;

public interface ModellingRuleType extends BaseObjectType {

    NamingRuleType getNamingRule();

    void setNamingRule(NamingRuleType namingRule);

}
