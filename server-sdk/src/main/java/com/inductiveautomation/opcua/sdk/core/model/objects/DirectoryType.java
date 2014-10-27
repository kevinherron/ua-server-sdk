package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface DirectoryType extends FolderType {

    FolderType getApplications();


    void atomicSet(Runnable runnable);

}
