package com.digitalpetri.opcua.sdk.server.namespaces.loader;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.Reference;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.model.UaDataTypeNode;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

public class UaDataTypeLoader {

    private final UaNamespace namespace;

    public UaDataTypeLoader(UaNamespace namespace) {
        this.namespace = namespace;
    }

    private void buildNode0() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=1"), new QualifiedName(0, "Boolean"), new LocalizedText("en", "Boolean"), Optional.of(new LocalizedText("en", "Describes a value that is either TRUE or FALSE.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=1"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode1() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=2"), new QualifiedName(0, "SByte"), new LocalizedText("en", "SByte"), Optional.of(new LocalizedText("en", "Describes a value that is an integer between -128 and 127.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=2"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=27"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode2() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=3"), new QualifiedName(0, "Byte"), new LocalizedText("en", "Byte"), Optional.of(new LocalizedText("en", "Describes a value that is an integer between 0 and 255.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=3"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=28"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode3() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=4"), new QualifiedName(0, "Int16"), new LocalizedText("en", "Int16"), Optional.of(new LocalizedText("en", "Describes a value that is an integer between \u221232,768 and 32,767.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=4"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=27"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode4() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=5"), new QualifiedName(0, "UInt16"), new LocalizedText("en", "UInt16"), Optional.of(new LocalizedText("en", "Describes a value that is an integer between 0 and 65535.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=5"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=28"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode5() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=6"), new QualifiedName(0, "Int32"), new LocalizedText("en", "Int32"), Optional.of(new LocalizedText("en", "Describes a value that is an integer between \u22122,147,483,648  and 2,147,483,647.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=6"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=27"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode6() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=7"), new QualifiedName(0, "UInt32"), new LocalizedText("en", "UInt32"), Optional.of(new LocalizedText("en", "Describes a value that is an integer between 0 and 4,294,967,295.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=7"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=28"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=7"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=288"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=7"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=289"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode7() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=8"), new QualifiedName(0, "Int64"), new LocalizedText("en", "Int64"), Optional.of(new LocalizedText("en", "Describes a value that is an integer between \u22129,223,372,036,854,775,808 and 9,223,372,036,854,775,807.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=8"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=27"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode8() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=9"), new QualifiedName(0, "UInt64"), new LocalizedText("en", "UInt64"), Optional.of(new LocalizedText("en", "Describes a value that is an integer between 0 and 18,446,744,073,709,551,615.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=9"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=28"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=9"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=11737"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode9() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=10"), new QualifiedName(0, "Float"), new LocalizedText("en", "Float"), Optional.of(new LocalizedText("en", "Describes a value that is an IEEE 754-1985 single precision floating point number.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=10"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=26"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode10() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=11"), new QualifiedName(0, "Double"), new LocalizedText("en", "Double"), Optional.of(new LocalizedText("en", "Describes a value that is an IEEE 754-1985 double precision floating point number.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=11"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=26"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=11"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=290"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode11() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=12"), new QualifiedName(0, "String"), new LocalizedText("en", "String"), Optional.of(new LocalizedText("en", "Describes a value that is a sequence of printable Unicode characters.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=12"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=12"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=295"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=12"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=291"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=12"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=292"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode12() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=13"), new QualifiedName(0, "DateTime"), new LocalizedText("en", "DateTime"), Optional.of(new LocalizedText("en", "Describes a value that is a Gregorian calender date and time.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=13"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=13"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=294"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=13"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=293"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode13() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=14"), new QualifiedName(0, "Guid"), new LocalizedText("en", "Guid"), Optional.of(new LocalizedText("en", "Describes a value that is a 128-bit globally unique identifier.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=14"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode14() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=15"), new QualifiedName(0, "ByteString"), new LocalizedText("en", "ByteString"), Optional.of(new LocalizedText("en", "Describes a value that is a sequence of bytes.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=15"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=15"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=30"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=15"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=311"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=15"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=521"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode15() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=16"), new QualifiedName(0, "XmlElement"), new LocalizedText("en", "XmlElement"), Optional.of(new LocalizedText("en", "Describes a value that is an XML element.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=16"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode16() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=17"), new QualifiedName(0, "NodeId"), new LocalizedText("en", "NodeId"), Optional.of(new LocalizedText("en", "Describes a value that is an identifier for a node within a Server address space.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=17"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=17"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=388"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode17() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=18"), new QualifiedName(0, "ExpandedNodeId"), new LocalizedText("en", "ExpandedNodeId"), Optional.of(new LocalizedText("en", "Describes a value that is an absolute identifier for a node.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=18"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode18() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=19"), new QualifiedName(0, "StatusCode"), new LocalizedText("en", "StatusCode"), Optional.of(new LocalizedText("en", "Describes a value that is a code representing the outcome of an operation by a Server.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=19"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode19() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=20"), new QualifiedName(0, "QualifiedName"), new LocalizedText("en", "QualifiedName"), Optional.of(new LocalizedText("en", "Describes a value that is a name qualified by a namespace.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=20"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode20() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=21"), new QualifiedName(0, "LocalizedText"), new LocalizedText("en", "LocalizedText"), Optional.of(new LocalizedText("en", "Describes a value that is human readable Unicode text with a locale identifier.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=21"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode21() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=22"), new QualifiedName(0, "Structure"), new LocalizedText("en", "Structure"), Optional.of(new LocalizedText("en", "Describes a value that is any type of structure that can be described with a data encoding.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true);
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12252"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12328"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=296"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=7594"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=8912"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=308"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12189"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=304"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=312"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=432"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=344"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=316"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=376"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=379"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=382"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=385"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=331"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=335"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=341"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=583"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=586"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=589"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=659"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=719"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=948"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=920"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=399"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=402"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=405"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=338"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=853"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=11943"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=11944"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=856"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=859"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=862"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=865"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=868"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=871"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=299"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=874"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=877"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=897"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=884"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=887"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12171"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12172"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12079"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12080"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=894"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=22"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=891"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode22() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=23"), new QualifiedName(0, "DataValue"), new LocalizedText("en", "DataValue"), Optional.of(new LocalizedText("en", "Describes a value that is a structure containing a value, a status code and timestamps.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=23"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode23() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=24"), new QualifiedName(0, "BaseDataType"), new LocalizedText("en", "BaseDataType"), Optional.of(new LocalizedText("en", "Describes a value that can have any valid DataType.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true);
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=26"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=1"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=13"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=14"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=15"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=16"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=17"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=18"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=19"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=20"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=21"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=23"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=25"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=24"), NodeId.parse("i=35"), ExpandedNodeId.parse("svr=0;i=90"), NodeClass.Object, false));
        this.namespace.addNode(node);
    }

    private void buildNode24() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=25"), new QualifiedName(0, "DiagnosticInfo"), new LocalizedText("en", "DiagnosticInfo"), Optional.of(new LocalizedText("en", "Describes a value that is a structure containing diagnostics associated with a StatusCode.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=25"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode25() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=26"), new QualifiedName(0, "Number"), new LocalizedText("en", "Number"), Optional.of(new LocalizedText("en", "Describes a value that can have any numeric DataType.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true);
        node.addReference(new Reference(NodeId.parse("i=26"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=26"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=27"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=26"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=10"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=26"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=11"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode26() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=27"), new QualifiedName(0, "Integer"), new LocalizedText("en", "Integer"), Optional.of(new LocalizedText("en", "Describes a value that can have any integer DataType.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true);
        node.addReference(new Reference(NodeId.parse("i=27"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=26"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=27"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=28"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=27"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=2"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=27"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=4"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=27"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=6"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=27"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=8"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode27() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=28"), new QualifiedName(0, "UInteger"), new LocalizedText("en", "UInteger"), Optional.of(new LocalizedText("en", "Describes a value that can have any unsigned integer DataType.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true);
        node.addReference(new Reference(NodeId.parse("i=28"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=27"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=28"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=3"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=28"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=5"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=28"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=7"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=28"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=9"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode28() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=29"), new QualifiedName(0, "Enumeration"), new LocalizedText("en", "Enumeration"), Optional.of(new LocalizedText("en", "Describes a value that is an enumerated DataType.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true);
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=24"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=120"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=11939"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=256"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=257"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=307"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=302"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=303"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=315"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=348"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=347"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=334"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=576"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=11234"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=11293"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=398"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=851"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=852"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12077"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=29"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=890"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode29() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=30"), new QualifiedName(0, "Image"), new LocalizedText("en", "Image"), Optional.of(new LocalizedText("en", "Describes a value that is an image encoded as a string of bytes.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=30"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=15"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=30"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=2000"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=30"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=2001"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=30"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=2002"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=30"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=2003"), NodeClass.DataType, true));
        this.namespace.addNode(node);
    }

    private void buildNode30() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=12328"), new QualifiedName(0, "TrustListDataType"), new LocalizedText("en", "TrustListDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=12328"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=12328"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12494"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12328"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12498"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode31() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=120"), new QualifiedName(0, "NamingRuleType"), new LocalizedText("en", "NamingRuleType"), Optional.of(new LocalizedText("en", "Describes a value that specifies the significance of the BrowseName for an instance declaration.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=120"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12169"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=120"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=120"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12169"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode32() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=256"), new QualifiedName(0, "IdType"), new LocalizedText("en", "IdType"), Optional.of(new LocalizedText("en", "The type of identifier used in a node id.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=256"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7591"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=256"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=256"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7591"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode33() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=257"), new QualifiedName(0, "NodeClass"), new LocalizedText("en", "NodeClass"), Optional.of(new LocalizedText("en", "A mask specifying the class of the node.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=257"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11878"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=257"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=257"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11878"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode34() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=288"), new QualifiedName(0, "IntegerId"), new LocalizedText("en", "IntegerId"), Optional.of(new LocalizedText("en", "A numeric identifier for an object.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=288"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=7"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode35() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=289"), new QualifiedName(0, "Counter"), new LocalizedText("en", "Counter"), Optional.of(new LocalizedText("en", "A monotonically increasing value.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=289"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=7"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode36() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=290"), new QualifiedName(0, "Duration"), new LocalizedText("en", "Duration"), Optional.of(new LocalizedText("en", "A period of time measured in seconds.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=290"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=11"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode37() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=291"), new QualifiedName(0, "NumericRange"), new LocalizedText("en", "NumericRange"), Optional.of(new LocalizedText("en", "Specifies a range of array indexes.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=291"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode38() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=292"), new QualifiedName(0, "Time"), new LocalizedText("en", "Time"), Optional.of(new LocalizedText("en", "A time value specified as HH:MM:SS.SSS.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=292"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode39() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=293"), new QualifiedName(0, "Date"), new LocalizedText("en", "Date"), Optional.of(new LocalizedText("en", "A date value.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=293"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=13"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode40() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=294"), new QualifiedName(0, "UtcTime"), new LocalizedText("en", "UtcTime"), Optional.of(new LocalizedText("en", "A date/time value specified in Universal Coordinated Time (UTC).")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=294"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=13"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode41() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=295"), new QualifiedName(0, "LocaleId"), new LocalizedText("en", "LocaleId"), Optional.of(new LocalizedText("en", "An identifier for a user locale.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=295"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode42() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=296"), new QualifiedName(0, "Argument"), new LocalizedText("en", "Argument"), Optional.of(new LocalizedText("en", "An argument for a method.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=296"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=296"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=297"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=296"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=298"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode43() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=299"), new QualifiedName(0, "StatusResult"), new LocalizedText("en", "StatusResult"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=299"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=299"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=300"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=299"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=301"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode44() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=302"), new QualifiedName(0, "MessageSecurityMode"), new LocalizedText("en", "MessageSecurityMode"), Optional.of(new LocalizedText("en", "The type of security to use on a message.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=302"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7595"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=302"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=302"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7595"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode45() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=303"), new QualifiedName(0, "UserTokenType"), new LocalizedText("en", "UserTokenType"), Optional.of(new LocalizedText("en", "The possible user token types.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=303"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7596"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=303"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=303"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7596"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode46() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=304"), new QualifiedName(0, "UserTokenPolicy"), new LocalizedText("en", "UserTokenPolicy"), Optional.of(new LocalizedText("en", "Describes a user token that can be used with a server.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=304"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=304"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=305"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=304"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=306"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode47() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=307"), new QualifiedName(0, "ApplicationType"), new LocalizedText("en", "ApplicationType"), Optional.of(new LocalizedText("en", "The types of applications.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=307"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7597"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=307"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=307"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7597"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode48() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=308"), new QualifiedName(0, "ApplicationDescription"), new LocalizedText("en", "ApplicationDescription"), Optional.of(new LocalizedText("en", "Describes an application and how to find it.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=308"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=308"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=309"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=308"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=310"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode49() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=311"), new QualifiedName(0, "ApplicationInstanceCertificate"), new LocalizedText("en", "ApplicationInstanceCertificate"), Optional.of(new LocalizedText("en", "A certificate for an instance of an application.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=311"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=15"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode50() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=312"), new QualifiedName(0, "EndpointDescription"), new LocalizedText("en", "EndpointDescription"), Optional.of(new LocalizedText("en", "The description of a endpoint that can be used to access a server.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=312"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=312"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=313"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=312"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=314"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode51() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=315"), new QualifiedName(0, "SecurityTokenRequestType"), new LocalizedText("en", "SecurityTokenRequestType"), Optional.of(new LocalizedText("en", "Indicates whether a token if being created or renewed.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=315"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7598"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=315"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=315"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7598"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode52() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=316"), new QualifiedName(0, "UserIdentityToken"), new LocalizedText("en", "UserIdentityToken"), Optional.of(new LocalizedText("en", "A base type for a user identity token.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=316"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=316"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=319"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=316"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=322"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=316"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=325"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=316"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=938"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=316"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=317"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=316"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=318"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode53() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=319"), new QualifiedName(0, "AnonymousIdentityToken"), new LocalizedText("en", "AnonymousIdentityToken"), Optional.of(new LocalizedText("en", "A token representing an anonymous user.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=319"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=316"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=319"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=320"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=319"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=321"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode54() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=322"), new QualifiedName(0, "UserNameIdentityToken"), new LocalizedText("en", "UserNameIdentityToken"), Optional.of(new LocalizedText("en", "A token representing a user identified by a user name and password.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=322"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=316"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=322"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=323"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=322"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=324"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode55() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=325"), new QualifiedName(0, "X509IdentityToken"), new LocalizedText("en", "X509IdentityToken"), Optional.of(new LocalizedText("en", "A token representing a user identified by an X509 certificate.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=325"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=316"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=325"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=326"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=325"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=327"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode56() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=331"), new QualifiedName(0, "EndpointConfiguration"), new LocalizedText("en", "EndpointConfiguration"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=331"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=331"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=332"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=331"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=333"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode57() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=334"), new QualifiedName(0, "ComplianceLevel"), new LocalizedText("en", "ComplianceLevel"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=334"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7599"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=334"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=334"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7599"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode58() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=335"), new QualifiedName(0, "SupportedProfile"), new LocalizedText("en", "SupportedProfile"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=335"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=335"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=336"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=335"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=337"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode59() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=338"), new QualifiedName(0, "BuildInfo"), new LocalizedText("en", "BuildInfo"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=338"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=338"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=339"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=338"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=340"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode60() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=341"), new QualifiedName(0, "SoftwareCertificate"), new LocalizedText("en", "SoftwareCertificate"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=341"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=341"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=342"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=341"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=343"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode61() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=344"), new QualifiedName(0, "SignedSoftwareCertificate"), new LocalizedText("en", "SignedSoftwareCertificate"), Optional.of(new LocalizedText("en", "A software certificate with a digital signature.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=344"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=344"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=345"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=344"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=346"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode62() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=347"), new QualifiedName(0, "AttributeWriteMask"), new LocalizedText("en", "AttributeWriteMask"), Optional.of(new LocalizedText("en", "Define bits used to indicate which attributes are writeable.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=347"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11882"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=347"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=347"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11882"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode63() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=348"), new QualifiedName(0, "NodeAttributesMask"), new LocalizedText("en", "NodeAttributesMask"), Optional.of(new LocalizedText("en", "The bits used to specify default attributes for a new node.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=348"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11881"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=348"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=348"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11881"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode64() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=376"), new QualifiedName(0, "AddNodesItem"), new LocalizedText("en", "AddNodesItem"), Optional.of(new LocalizedText("en", "A request to add a node to the server address space.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=376"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=376"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=377"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=376"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=378"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode65() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=379"), new QualifiedName(0, "AddReferencesItem"), new LocalizedText("en", "AddReferencesItem"), Optional.of(new LocalizedText("en", "A request to add a reference to the server address space.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=379"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=379"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=380"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=379"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=381"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode66() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=382"), new QualifiedName(0, "DeleteNodesItem"), new LocalizedText("en", "DeleteNodesItem"), Optional.of(new LocalizedText("en", "A request to delete a node to the server address space.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=382"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=382"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=383"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=382"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=384"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode67() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=385"), new QualifiedName(0, "DeleteReferencesItem"), new LocalizedText("en", "DeleteReferencesItem"), Optional.of(new LocalizedText("en", "A request to delete a node from the server address space.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=385"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=385"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=386"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=385"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=387"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode68() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=388"), new QualifiedName(0, "SessionAuthenticationToken"), new LocalizedText("en", "SessionAuthenticationToken"), Optional.of(new LocalizedText("en", "A unique identifier for a session used to authenticate requests.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=388"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=17"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode69() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=398"), new QualifiedName(0, "EnumeratedTestType"), new LocalizedText("en", "EnumeratedTestType"), Optional.of(new LocalizedText("en", "A simple enumerated type used for testing.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=398"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11886"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=398"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=398"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11886"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode70() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=399"), new QualifiedName(0, "ScalarTestType"), new LocalizedText("en", "ScalarTestType"), Optional.of(new LocalizedText("en", "A complex type containing all possible scalar types used for testing.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=399"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=399"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=400"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=399"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=401"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode71() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=402"), new QualifiedName(0, "ArrayTestType"), new LocalizedText("en", "ArrayTestType"), Optional.of(new LocalizedText("en", "A complex type containing all possible array types used for testing.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=402"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=402"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=403"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=402"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=404"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode72() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=405"), new QualifiedName(0, "CompositeTestType"), new LocalizedText("en", "CompositeTestType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=405"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=405"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=406"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=405"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=407"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode73() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=432"), new QualifiedName(0, "RegisteredServer"), new LocalizedText("en", "RegisteredServer"), Optional.of(new LocalizedText("en", "The information required to register a server with a discovery server.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=432"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=432"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=12192"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=432"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=433"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=432"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=434"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode74() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=521"), new QualifiedName(0, "ContinuationPoint"), new LocalizedText("en", "ContinuationPoint"), Optional.of(new LocalizedText("en", "An identifier for a suspended query or browse operation.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=521"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=15"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode75() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=576"), new QualifiedName(0, "FilterOperator"), new LocalizedText("en", "FilterOperator"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=576"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7605"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=576"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=576"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7605"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode76() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=583"), new QualifiedName(0, "ContentFilterElement"), new LocalizedText("en", "ContentFilterElement"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=583"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=583"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=584"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=583"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=585"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode77() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=586"), new QualifiedName(0, "ContentFilter"), new LocalizedText("en", "ContentFilter"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=586"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=586"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=587"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=586"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=588"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode78() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=589"), new QualifiedName(0, "FilterOperand"), new LocalizedText("en", "FilterOperand"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=589"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=589"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=592"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=589"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=595"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=589"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=598"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=589"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=601"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=589"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=590"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=589"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=591"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode79() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=592"), new QualifiedName(0, "ElementOperand"), new LocalizedText("en", "ElementOperand"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=592"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=589"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=592"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=593"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=592"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=594"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode80() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=595"), new QualifiedName(0, "LiteralOperand"), new LocalizedText("en", "LiteralOperand"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=595"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=589"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=595"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=596"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=595"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=597"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode81() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=598"), new QualifiedName(0, "AttributeOperand"), new LocalizedText("en", "AttributeOperand"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=598"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=589"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=598"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=599"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=598"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=600"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode82() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=601"), new QualifiedName(0, "SimpleAttributeOperand"), new LocalizedText("en", "SimpleAttributeOperand"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=601"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=589"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=601"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=602"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=601"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=603"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode83() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=659"), new QualifiedName(0, "HistoryEvent"), new LocalizedText("en", "HistoryEvent"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=659"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=659"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=660"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=659"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=661"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode84() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=719"), new QualifiedName(0, "MonitoringFilter"), new LocalizedText("en", "MonitoringFilter"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=719"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=719"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=725"), NodeClass.DataType, true));
        node.addReference(new Reference(NodeId.parse("i=719"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=720"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=719"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=721"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode85() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=8912"), new QualifiedName(0, "TimeZoneDataType"), new LocalizedText("en", "TimeZoneDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=8912"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=8912"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=8913"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=8912"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=8917"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode86() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=725"), new QualifiedName(0, "EventFilter"), new LocalizedText("en", "EventFilter"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=725"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=719"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=725"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=726"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=725"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=727"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode87() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=851"), new QualifiedName(0, "RedundancySupport"), new LocalizedText("en", "RedundancySupport"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=851"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7611"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=851"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=851"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7611"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode88() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=852"), new QualifiedName(0, "ServerState"), new LocalizedText("en", "ServerState"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=852"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7612"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=852"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=852"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7612"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode89() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=853"), new QualifiedName(0, "RedundantServerDataType"), new LocalizedText("en", "RedundantServerDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=853"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=853"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=854"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=853"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=855"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode90() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=856"), new QualifiedName(0, "SamplingIntervalDiagnosticsDataType"), new LocalizedText("en", "SamplingIntervalDiagnosticsDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=856"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=856"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=857"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=856"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=858"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode91() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=859"), new QualifiedName(0, "ServerDiagnosticsSummaryDataType"), new LocalizedText("en", "ServerDiagnosticsSummaryDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=859"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=859"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=860"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=859"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=861"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode92() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=862"), new QualifiedName(0, "ServerStatusDataType"), new LocalizedText("en", "ServerStatusDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=862"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=862"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=863"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=862"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=864"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode93() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=865"), new QualifiedName(0, "SessionDiagnosticsDataType"), new LocalizedText("en", "SessionDiagnosticsDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=865"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=865"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=866"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=865"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=867"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode94() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=868"), new QualifiedName(0, "SessionSecurityDiagnosticsDataType"), new LocalizedText("en", "SessionSecurityDiagnosticsDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=868"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=868"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=869"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=868"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=870"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode95() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=871"), new QualifiedName(0, "ServiceCounterDataType"), new LocalizedText("en", "ServiceCounterDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=871"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=871"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=872"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=871"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=873"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode96() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=874"), new QualifiedName(0, "SubscriptionDiagnosticsDataType"), new LocalizedText("en", "SubscriptionDiagnosticsDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=874"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=874"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=875"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=874"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=876"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode97() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=877"), new QualifiedName(0, "ModelChangeStructureDataType"), new LocalizedText("en", "ModelChangeStructureDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=877"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=877"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=878"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=877"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=879"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode98() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=884"), new QualifiedName(0, "Range"), new LocalizedText("en", "Range"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=884"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=884"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=885"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=884"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=886"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode99() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=887"), new QualifiedName(0, "EUInformation"), new LocalizedText("en", "EUInformation"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=887"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=887"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=888"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=887"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=889"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode100() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=890"), new QualifiedName(0, "ExceptionDeviationFormat"), new LocalizedText("en", "ExceptionDeviationFormat"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=890"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7614"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=890"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=890"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=7614"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode101() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=891"), new QualifiedName(0, "Annotation"), new LocalizedText("en", "Annotation"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=891"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=891"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=892"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=891"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=893"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode102() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=894"), new QualifiedName(0, "ProgramDiagnosticDataType"), new LocalizedText("en", "ProgramDiagnosticDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=894"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=894"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=895"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=894"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=896"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode103() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=897"), new QualifiedName(0, "SemanticChangeStructureDataType"), new LocalizedText("en", "SemanticChangeStructureDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=897"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=897"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=898"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=897"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=899"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode104() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=920"), new QualifiedName(0, "HistoryEventFieldList"), new LocalizedText("en", "HistoryEventFieldList"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=920"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=920"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=921"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=920"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=922"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode105() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=938"), new QualifiedName(0, "IssuedIdentityToken"), new LocalizedText("en", "IssuedIdentityToken"), Optional.of(new LocalizedText("en", "A token representing a user identified by a WS-Security XML token.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=938"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=316"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=938"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=939"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=938"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=940"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode106() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=948"), new QualifiedName(0, "AggregateConfiguration"), new LocalizedText("en", "AggregateConfiguration"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=948"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=948"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=949"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=948"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=950"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode107() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=2000"), new QualifiedName(0, "ImageBMP"), new LocalizedText("en", "ImageBMP"), Optional.of(new LocalizedText("en", "An image encoded in BMP format.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=2000"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=30"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode108() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=2001"), new QualifiedName(0, "ImageGIF"), new LocalizedText("en", "ImageGIF"), Optional.of(new LocalizedText("en", "An image encoded in GIF format.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=2001"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=30"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode109() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=2002"), new QualifiedName(0, "ImageJPG"), new LocalizedText("en", "ImageJPG"), Optional.of(new LocalizedText("en", "An image encoded in JPEG format.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=2002"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=30"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode110() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=2003"), new QualifiedName(0, "ImagePNG"), new LocalizedText("en", "ImagePNG"), Optional.of(new LocalizedText("en", "An image encoded in PNG format.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=2003"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=30"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode111() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=11234"), new QualifiedName(0, "HistoryUpdateType"), new LocalizedText("en", "HistoryUpdateType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=11234"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11884"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11234"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=11234"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11884"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode112() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=11293"), new QualifiedName(0, "PerformUpdateType"), new LocalizedText("en", "PerformUpdateType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=11293"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11885"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11293"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=11293"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11885"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode113() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=7594"), new QualifiedName(0, "EnumValueType"), new LocalizedText("en", "EnumValueType"), Optional.of(new LocalizedText("en", "A mapping between a value of an enumerated type and a name and description.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=7594"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=7594"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=7616"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=7594"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=8251"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode114() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=11737"), new QualifiedName(0, "BitFieldMaskDataType"), new LocalizedText("en", "BitFieldMaskDataType"), Optional.of(new LocalizedText("en", "A mask of 32 bits that can be updated individually by using the top 32 bits as a mask.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=11737"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=9"), NodeClass.DataType, false));
        this.namespace.addNode(node);
    }

    private void buildNode115() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=11939"), new QualifiedName(0, "OpenFileMode"), new LocalizedText("en", "OpenFileMode"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=11939"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11940"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11939"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=11939"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11940"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode116() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=11943"), new QualifiedName(0, "EndpointUrlListDataType"), new LocalizedText("en", "EndpointUrlListDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=11943"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=11943"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=11949"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11943"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=11957"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode117() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=11944"), new QualifiedName(0, "NetworkGroupDataType"), new LocalizedText("en", "NetworkGroupDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=11944"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=11944"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=11950"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11944"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=11958"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode118() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=12077"), new QualifiedName(0, "AxisScaleEnumeration"), new LocalizedText("en", "AxisScaleEnumeration"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=12077"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12078"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12077"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=29"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=12077"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12078"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode119() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=12079"), new QualifiedName(0, "AxisInformation"), new LocalizedText("en", "AxisInformation"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=12079"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=12079"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12081"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12079"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12089"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode120() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=12080"), new QualifiedName(0, "XVType"), new LocalizedText("en", "XVType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=12080"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=12080"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12082"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12080"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12090"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode121() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=12171"), new QualifiedName(0, "ComplexNumberType"), new LocalizedText("en", "ComplexNumberType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=12171"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=12171"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12173"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12171"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12181"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode122() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=12172"), new QualifiedName(0, "DoubleComplexNumberType"), new LocalizedText("en", "DoubleComplexNumberType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=12172"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=12172"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12174"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12172"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12182"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode123() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=12189"), new QualifiedName(0, "ServerOnNetwork"), new LocalizedText("en", "ServerOnNetwork"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=12189"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=12189"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12195"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12189"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12207"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode124() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=12192"), new QualifiedName(0, "RegisteredServer2"), new LocalizedText("en", "RegisteredServer2"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=12192"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=432"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=12192"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12198"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12192"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12210"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    private void buildNode125() {
        UaDataTypeNode node = new UaDataTypeNode(this.namespace, NodeId.parse("i=12252"), new QualifiedName(0, "ApplicationRecordDataType"), new LocalizedText("en", "ApplicationRecordDataType"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), false);
        node.addReference(new Reference(NodeId.parse("i=12252"), NodeId.parse("i=45"), ExpandedNodeId.parse("svr=0;i=22"), NodeClass.DataType, false));
        node.addReference(new Reference(NodeId.parse("i=12252"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12253"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12252"), NodeId.parse("i=38"), ExpandedNodeId.parse("svr=0;i=12257"), NodeClass.Object, true));
        this.namespace.addNode(node);
    }

    public void buildNodes() {
        buildNode0();
        buildNode1();
        buildNode2();
        buildNode3();
        buildNode4();
        buildNode5();
        buildNode6();
        buildNode7();
        buildNode8();
        buildNode9();
        buildNode10();
        buildNode11();
        buildNode12();
        buildNode13();
        buildNode14();
        buildNode15();
        buildNode16();
        buildNode17();
        buildNode18();
        buildNode19();
        buildNode20();
        buildNode21();
        buildNode22();
        buildNode23();
        buildNode24();
        buildNode25();
        buildNode26();
        buildNode27();
        buildNode28();
        buildNode29();
        buildNode30();
        buildNode31();
        buildNode32();
        buildNode33();
        buildNode34();
        buildNode35();
        buildNode36();
        buildNode37();
        buildNode38();
        buildNode39();
        buildNode40();
        buildNode41();
        buildNode42();
        buildNode43();
        buildNode44();
        buildNode45();
        buildNode46();
        buildNode47();
        buildNode48();
        buildNode49();
        buildNode50();
        buildNode51();
        buildNode52();
        buildNode53();
        buildNode54();
        buildNode55();
        buildNode56();
        buildNode57();
        buildNode58();
        buildNode59();
        buildNode60();
        buildNode61();
        buildNode62();
        buildNode63();
        buildNode64();
        buildNode65();
        buildNode66();
        buildNode67();
        buildNode68();
        buildNode69();
        buildNode70();
        buildNode71();
        buildNode72();
        buildNode73();
        buildNode74();
        buildNode75();
        buildNode76();
        buildNode77();
        buildNode78();
        buildNode79();
        buildNode80();
        buildNode81();
        buildNode82();
        buildNode83();
        buildNode84();
        buildNode85();
        buildNode86();
        buildNode87();
        buildNode88();
        buildNode89();
        buildNode90();
        buildNode91();
        buildNode92();
        buildNode93();
        buildNode94();
        buildNode95();
        buildNode96();
        buildNode97();
        buildNode98();
        buildNode99();
        buildNode100();
        buildNode101();
        buildNode102();
        buildNode103();
        buildNode104();
        buildNode105();
        buildNode106();
        buildNode107();
        buildNode108();
        buildNode109();
        buildNode110();
        buildNode111();
        buildNode112();
        buildNode113();
        buildNode114();
        buildNode115();
        buildNode116();
        buildNode117();
        buildNode118();
        buildNode119();
        buildNode120();
        buildNode121();
        buildNode122();
        buildNode123();
        buildNode124();
        buildNode125();
    }

}
