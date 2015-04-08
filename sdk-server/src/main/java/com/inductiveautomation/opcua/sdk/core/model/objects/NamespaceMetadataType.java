package com.inductiveautomation.opcua.sdk.core.model.objects;

import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.enumerated.IdType;

public interface NamespaceMetadataType extends BaseObjectType {

    String getNamespaceUri();

    String getNamespaceVersion();

    DateTime getNamespacePublicationDate();

    Boolean getIsNamespaceSubset();

    IdType[] getStaticNodeIdIdentifierTypes();

    String[] getStaticNumericNodeIdRange();

    String[] getStaticStringNodeIdPattern();

    AddressSpaceFileType getNamespaceFile();

    void setNamespaceUri(String namespaceUri);

    void setNamespaceVersion(String namespaceVersion);

    void setNamespacePublicationDate(DateTime namespacePublicationDate);

    void setIsNamespaceSubset(Boolean isNamespaceSubset);

    void setStaticNodeIdIdentifierTypes(IdType[] staticNodeIdIdentifierTypes);

    void setStaticNumericNodeIdRange(String[] staticNumericNodeIdRange);

    void setStaticStringNodeIdPattern(String[] staticStringNodeIdPattern);

}
