package com.inductiveautomation.opcua.sdk.server.model.objects;

import java.util.Optional;

import com.inductiveautomation.opcua.sdk.core.model.objects.AddressSpaceFileType;
import com.inductiveautomation.opcua.sdk.core.model.objects.NamespaceMetadataType;
import com.inductiveautomation.opcua.sdk.server.api.UaNodeManager;
import com.inductiveautomation.opcua.sdk.server.util.UaObjectType;
import com.inductiveautomation.opcua.stack.core.types.builtin.DataValue;
import com.inductiveautomation.opcua.stack.core.types.builtin.DateTime;
import com.inductiveautomation.opcua.stack.core.types.builtin.LocalizedText;
import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import com.inductiveautomation.opcua.stack.core.types.builtin.QualifiedName;
import com.inductiveautomation.opcua.stack.core.types.builtin.Variant;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UByte;
import com.inductiveautomation.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.inductiveautomation.opcua.stack.core.types.enumerated.IdType;


@UaObjectType(name = "NamespaceMetadataType")
public class NamespaceMetadataNode extends BaseObjectNode implements NamespaceMetadataType {

    public NamespaceMetadataNode(
            UaNodeManager nodeManager,
            NodeId nodeId,
            QualifiedName browseName,
            LocalizedText displayName,
            Optional<LocalizedText> description,
            Optional<UInteger> writeMask,
            Optional<UInteger> userWriteMask,
            UByte eventNotifier) {

        super(nodeManager, nodeId, browseName, displayName, description, writeMask, userWriteMask, eventNotifier);
    }

    public String getNamespaceUri() {
        Optional<String> namespaceUri = getProperty("NamespaceUri");

        return namespaceUri.orElse(null);
    }

    public String getNamespaceVersion() {
        Optional<String> namespaceVersion = getProperty("NamespaceVersion");

        return namespaceVersion.orElse(null);
    }

    public DateTime getNamespacePublicationDate() {
        Optional<DateTime> namespacePublicationDate = getProperty("NamespacePublicationDate");

        return namespacePublicationDate.orElse(null);
    }

    public Boolean getIsNamespaceSubset() {
        Optional<Boolean> isNamespaceSubset = getProperty("IsNamespaceSubset");

        return isNamespaceSubset.orElse(null);
    }

    public IdType[] getStaticNodeIdIdentifierTypes() {
        Optional<Integer[]> staticNodeIdIdentifierTypes = getProperty("StaticNodeIdIdentifierTypes");

        return staticNodeIdIdentifierTypes.map(values -> {
            IdType[] staticNodeIdentifierTypes = new IdType[values.length];
            for (int i = 0; i < values.length; i++) {
                staticNodeIdentifierTypes[i] = IdType.from(values[i]);
            }
            return staticNodeIdentifierTypes;
        }).orElse(null);
    }

    public String[] getStaticNumericNodeIdRange() {
        Optional<String[]> staticNumericNodeIdRange = getProperty("StaticNumericNodeIdRange");

        return staticNumericNodeIdRange.orElse(null);
    }

    public String[] getStaticStringNodeIdPattern() {
        Optional<String[]> staticStringNodeIdPattern = getProperty("StaticStringNodeIdPattern");

        return staticStringNodeIdPattern.orElse(null);
    }

    public AddressSpaceFileType getNamespaceFile() {
        Optional<com.inductiveautomation.opcua.sdk.core.nodes.ObjectNode> namespaceFile = getObjectComponent("NamespaceFile");

        return namespaceFile.map(node -> (AddressSpaceFileType) node).orElse(null);
    }

    public synchronized void setNamespaceUri(String namespaceUri) {
        getPropertyNode("NamespaceUri").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(namespaceUri)));
        });
    }

    public synchronized void setNamespaceVersion(String namespaceVersion) {
        getPropertyNode("NamespaceVersion").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(namespaceVersion)));
        });
    }

    public synchronized void setNamespacePublicationDate(DateTime namespacePublicationDate) {
        getPropertyNode("NamespacePublicationDate").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(namespacePublicationDate)));
        });
    }

    public synchronized void setIsNamespaceSubset(Boolean isNamespaceSubset) {
        getPropertyNode("IsNamespaceSubset").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(isNamespaceSubset)));
        });
    }

    public synchronized void setStaticNodeIdIdentifierTypes(IdType[] staticNodeIdIdentifierTypes) {
        getPropertyNode("StaticNodeIdIdentifierTypes").ifPresent(n -> {
            if (staticNodeIdIdentifierTypes != null) {
                Integer[] values = new Integer[staticNodeIdIdentifierTypes.length];
                for (int i = 0; i < values.length; i++) {
                    values[i] = staticNodeIdIdentifierTypes[i].getValue();
                }
                n.setValue(new DataValue(new Variant(values)));
            } else {
                n.setValue(new DataValue(new Variant(new Integer[0])));
            }
        });
    }

    public synchronized void setStaticNumericNodeIdRange(String[] staticNumericNodeIdRange) {
        getPropertyNode("StaticNumericNodeIdRange").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(staticNumericNodeIdRange)));
        });
    }

    public synchronized void setStaticStringNodeIdPattern(String[] staticStringNodeIdPattern) {
        getPropertyNode("StaticStringNodeIdPattern").ifPresent(n -> {
            n.setValue(new DataValue(new Variant(staticStringNodeIdPattern)));
        });
    }

    public synchronized void atomicSet(Runnable runnable) {
        runnable.run();
    }

}
