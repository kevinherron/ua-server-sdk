package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface NamespacesType extends BaseObjectType {

    NamespaceMetadataType getNamespaceIdentifier();

    AddressSpaceFileType getAddressSpaceFile();

    void setNamespaceIdentifier(NamespaceMetadataType namespaceIdentifier);

    void setAddressSpaceFile(AddressSpaceFileType addressSpaceFile);

    void atomicSet(Runnable runnable);

}
