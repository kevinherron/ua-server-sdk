package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.enumerated.NamingRuleType;

public interface ModellingRuleType extends BaseObjectType {

    NamingRuleType getNamingRule();

    void setNamingRule(NamingRuleType namingRule);

    void atomicSet(Runnable runnable);

}
