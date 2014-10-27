package com.inductiveautomation.opcua.sdk.core.model.objects;

public interface DirectoryType extends FolderType {

    FolderType getApplications();

    void setApplications(FolderType applications);

    void atomicSet(Runnable runnable);

}
