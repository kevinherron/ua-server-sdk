package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface NamespacesType extends BaseObjectType {

    NamespaceMetadataType getNamespaceIdentifier();

    AddressSpaceFileType getAddressSpaceFile();


    void atomicSet(Runnable runnable);

}
