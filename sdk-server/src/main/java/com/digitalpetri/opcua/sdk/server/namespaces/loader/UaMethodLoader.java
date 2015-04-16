package com.digitalpetri.opcua.sdk.server.namespaces.loader;

import java.util.Optional;

import com.digitalpetri.opcua.sdk.core.Reference;
import com.digitalpetri.opcua.sdk.server.api.UaNamespace;
import com.digitalpetri.opcua.sdk.server.model.UaMethodNode;
import com.digitalpetri.opcua.stack.core.types.builtin.ExpandedNodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.LocalizedText;
import com.digitalpetri.opcua.stack.core.types.builtin.NodeId;
import com.digitalpetri.opcua.stack.core.types.builtin.QualifiedName;
import com.digitalpetri.opcua.stack.core.types.builtin.unsigned.UInteger;
import com.digitalpetri.opcua.stack.core.types.enumerated.NodeClass;

public class UaMethodLoader {

    private final UaNamespace namespace;

    public UaMethodLoader(UaNamespace namespace) {
        this.namespace = namespace;
    }

    private void buildNode0() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12305"), new QualifiedName(0, "CloseAndUpdate"), new LocalizedText("en", "CloseAndUpdate"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12305"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12283"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12305"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12306"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12305"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=80"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12305"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12283"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12305"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12306"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode1() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12307"), new QualifiedName(0, "AddCertificate"), new LocalizedText("en", "AddCertificate"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12307"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12283"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12307"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12308"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12307"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=80"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12307"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12283"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12307"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12308"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode2() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12309"), new QualifiedName(0, "RemoveCertificate"), new LocalizedText("en", "RemoveCertificate"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12309"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12283"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12309"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12310"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12309"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=80"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12309"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12283"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12309"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12310"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode3() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12357"), new QualifiedName(0, "RequestCertificate"), new LocalizedText("en", "RequestCertificate"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12357"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12344"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12357"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12358"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12357"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12359"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12357"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12357"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12344"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12357"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12358"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12357"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12359"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode4() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12360"), new QualifiedName(0, "SignCertificate"), new LocalizedText("en", "SignCertificate"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12360"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12344"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12360"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12361"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12360"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12362"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12360"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12360"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12344"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12360"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12361"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12360"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12362"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode5() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12363"), new QualifiedName(0, "RenewCertificate"), new LocalizedText("en", "RenewCertificate"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12363"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12344"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12363"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12364"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12363"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12365"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12363"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12363"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12344"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12363"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12364"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12363"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12365"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode6() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12366"), new QualifiedName(0, "CheckRequestStatus"), new LocalizedText("en", "CheckRequestStatus"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12366"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12344"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12366"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12367"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12366"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12368"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12366"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12366"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12344"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12366"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12367"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12366"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12368"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode7() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12369"), new QualifiedName(0, "GetTrustLists"), new LocalizedText("en", "GetTrustLists"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12369"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12344"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12369"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12370"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12369"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12371"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12369"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12369"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12344"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12369"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12370"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12369"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12371"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode8() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12422"), new QualifiedName(0, "Open"), new LocalizedText("en", "Open"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12422"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12417"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12422"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12423"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12422"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12424"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12422"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12422"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12417"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12422"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12423"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12422"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12424"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode9() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12425"), new QualifiedName(0, "Close"), new LocalizedText("en", "Close"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12425"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12417"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12425"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12426"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12425"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12425"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12417"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12425"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12426"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode10() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12427"), new QualifiedName(0, "Read"), new LocalizedText("en", "Read"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12427"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12417"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12427"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12428"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12427"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12429"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12427"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12427"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12417"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12427"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12428"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12427"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12429"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode11() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12430"), new QualifiedName(0, "Write"), new LocalizedText("en", "Write"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12430"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12417"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12430"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12431"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12430"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12430"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12417"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12430"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12431"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode12() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12432"), new QualifiedName(0, "GetPosition"), new LocalizedText("en", "GetPosition"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12432"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12417"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12432"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12433"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12432"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12434"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12432"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12432"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12417"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12432"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12433"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12432"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12434"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode13() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12435"), new QualifiedName(0, "SetPosition"), new LocalizedText("en", "SetPosition"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12435"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12417"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12435"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12436"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12435"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12435"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12417"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12435"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12436"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode14() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12450"), new QualifiedName(0, "Open"), new LocalizedText("en", "Open"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12450"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12445"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12450"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12451"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12450"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12452"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12450"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12450"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12445"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12450"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12451"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12450"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12452"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode15() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12453"), new QualifiedName(0, "Close"), new LocalizedText("en", "Close"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12453"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12445"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12453"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12454"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12453"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12453"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12445"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12453"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12454"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode16() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12455"), new QualifiedName(0, "Read"), new LocalizedText("en", "Read"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12455"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12445"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12455"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12456"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12455"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12457"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12455"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12455"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12445"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12455"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12456"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12455"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12457"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode17() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12458"), new QualifiedName(0, "Write"), new LocalizedText("en", "Write"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12458"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12445"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12458"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12459"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12458"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12458"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12445"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12458"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12459"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode18() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12460"), new QualifiedName(0, "GetPosition"), new LocalizedText("en", "GetPosition"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12460"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12445"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12460"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12461"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12460"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12462"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12460"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12460"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12445"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12460"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12461"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12460"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12462"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode19() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12463"), new QualifiedName(0, "SetPosition"), new LocalizedText("en", "SetPosition"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12463"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12445"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12463"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12464"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12463"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12463"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12445"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=12463"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12464"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode20() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12473"), new QualifiedName(0, "UpdateCertificate"), new LocalizedText("en", "UpdateCertificate"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12473"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12411"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12473"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12474"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12473"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12475"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12473"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12473"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12411"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12473"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12474"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12473"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12475"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode21() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12476"), new QualifiedName(0, "Restart"), new LocalizedText("en", "Restart"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12476"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12411"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12476"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12476"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12411"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode22() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=8922"), new QualifiedName(0, "Lock"), new LocalizedText("en", "Lock"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=8922"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=8921"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=8922"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=8922"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=8921"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode23() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=8923"), new QualifiedName(0, "Unlock"), new LocalizedText("en", "Unlock"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=8923"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=8921"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=8923"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=8923"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=8921"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode24() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=8925"), new QualifiedName(0, "Lock"), new LocalizedText("en", "Lock"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=8925"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=8924"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=8925"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=8924"), NodeClass.Object, false));
        this.namespace.addNode(node);
    }

    private void buildNode25() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=8926"), new QualifiedName(0, "Unlock"), new LocalizedText("en", "Unlock"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=8926"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=8924"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=8926"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=8924"), NodeClass.Object, false));
        this.namespace.addNode(node);
    }

    private void buildNode26() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=9027"), new QualifiedName(0, "Enable"), new LocalizedText("en", "Enable"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=9027"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2782"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=9027"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=2803"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=9027"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=9027"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2782"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode27() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=9028"), new QualifiedName(0, "Disable"), new LocalizedText("en", "Disable"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=9028"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2782"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=9028"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=2803"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=9028"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=9028"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2782"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode28() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=9029"), new QualifiedName(0, "AddComment"), new LocalizedText("en", "AddComment"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=9029"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2782"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=9029"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=9030"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=9029"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=2829"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=9029"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=9029"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2782"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=9029"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=9030"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode29() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=9069"), new QualifiedName(0, "Respond"), new LocalizedText("en", "Respond"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=9069"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2830"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=9069"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=9070"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=9069"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=8927"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=9069"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=9069"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2830"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=9069"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=9070"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode30() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=9111"), new QualifiedName(0, "Acknowledge"), new LocalizedText("en", "Acknowledge"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=9111"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2881"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=9111"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=9112"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=9111"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=8944"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=9111"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=9111"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2881"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=9111"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=9112"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode31() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=9113"), new QualifiedName(0, "Confirm"), new LocalizedText("en", "Confirm"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=9113"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2881"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=9113"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=9114"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=9113"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=8961"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=9113"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=80"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=9113"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2881"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=9113"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=9114"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode32() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=9211"), new QualifiedName(0, "Unshelve"), new LocalizedText("en", "Unshelve"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=9211"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=9178"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=9211"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=11093"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=9211"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=9211"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=9178"), NodeClass.Object, false));
        this.namespace.addNode(node);
    }

    private void buildNode33() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=9212"), new QualifiedName(0, "OneShotShelve"), new LocalizedText("en", "OneShotShelve"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=9212"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=9178"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=9212"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=11093"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=9212"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=9212"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=9178"), NodeClass.Object, false));
        this.namespace.addNode(node);
    }

    private void buildNode34() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=9213"), new QualifiedName(0, "TimedShelve"), new LocalizedText("en", "TimedShelve"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=9213"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=9178"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=9213"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=9214"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=9213"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=11093"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=9213"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=9213"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=9178"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=9213"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=9214"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode35() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=2426"), new QualifiedName(0, "Start"), new LocalizedText("en", "Start"), Optional.of(new LocalizedText("en", "Causes the Program to transition from the Ready state to the Running state.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=2426"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2391"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=2426"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2410"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2426"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2410"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2426"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=2426"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2391"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode36() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=2427"), new QualifiedName(0, "Suspend"), new LocalizedText("en", "Suspend"), Optional.of(new LocalizedText("en", "Causes the Program to transition from the Running state to the Suspended state.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=2427"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2391"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=2427"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2416"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2427"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2416"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2427"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=2427"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2391"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode37() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=2428"), new QualifiedName(0, "Resume"), new LocalizedText("en", "Resume"), Optional.of(new LocalizedText("en", "Causes the Program to transition from the Suspended state to the Running state.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=2428"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2391"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=2428"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2418"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2428"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2418"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2428"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=2428"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2391"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode38() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=2429"), new QualifiedName(0, "Halt"), new LocalizedText("en", "Halt"), Optional.of(new LocalizedText("en", "Causes the Program to transition from the Ready, Running or Suspended state to the Halted state.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=2429"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2391"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=2429"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2412"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2429"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2420"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2429"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2424"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2429"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2412"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2429"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2420"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2429"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2424"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2429"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=2429"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2391"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode39() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=2430"), new QualifiedName(0, "Reset"), new LocalizedText("en", "Reset"), Optional.of(new LocalizedText("en", "Causes the Program to transition from the Halted state to the Ready state.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=2430"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2391"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=2430"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2408"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2430"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2408"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2430"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=2430"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2391"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode40() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=2947"), new QualifiedName(0, "Unshelve"), new LocalizedText("en", "Unshelve"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=2947"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2929"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=2947"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2940"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2947"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2943"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2947"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2940"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2947"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2943"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2947"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=11093"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=2947"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=2947"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2929"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode41() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=2948"), new QualifiedName(0, "OneShotShelve"), new LocalizedText("en", "OneShotShelve"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=2948"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2929"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=2948"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2936"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2948"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2942"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2948"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2936"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2948"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2942"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2948"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=11093"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=2948"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=2948"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2929"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode42() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=2949"), new QualifiedName(0, "TimedShelve"), new LocalizedText("en", "TimedShelve"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=2949"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2929"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=2949"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2935"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2949"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2945"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2949"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=2991"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=2949"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2935"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2949"), NodeId.parse("i=53"), ExpandedNodeId.parse("svr=0;i=2945"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=2949"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=11093"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=2949"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=2949"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2929"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=2949"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=2991"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode43() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11489"), new QualifiedName(0, "GetMonitoredItems"), new LocalizedText("en", "GetMonitoredItems"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11489"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2004"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11489"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11490"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11489"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11491"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11489"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=80"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11489"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2004"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11489"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11490"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11489"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11491"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode44() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11492"), new QualifiedName(0, "GetMonitoredItems"), new LocalizedText("en", "GetMonitoredItems"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11492"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2253"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11492"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11493"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11492"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11494"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11492"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2253"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11492"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11493"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11492"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11494"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode45() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11580"), new QualifiedName(0, "Open"), new LocalizedText("en", "Open"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11580"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11575"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11580"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11581"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11580"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11582"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11580"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11580"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11575"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11580"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11581"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11580"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11582"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode46() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11583"), new QualifiedName(0, "Close"), new LocalizedText("en", "Close"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11583"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11575"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11583"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11584"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11583"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11583"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11575"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11583"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11584"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode47() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11585"), new QualifiedName(0, "Read"), new LocalizedText("en", "Read"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11585"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11575"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11585"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11586"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11585"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11587"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11585"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11585"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11575"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11585"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11586"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11585"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11587"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode48() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11588"), new QualifiedName(0, "Write"), new LocalizedText("en", "Write"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11588"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11575"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11588"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11589"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11588"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11588"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11575"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11588"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11589"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode49() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11590"), new QualifiedName(0, "GetPosition"), new LocalizedText("en", "GetPosition"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11590"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11575"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11590"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11591"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11590"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11592"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11590"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11590"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11575"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11590"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11591"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11590"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11592"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode50() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11593"), new QualifiedName(0, "SetPosition"), new LocalizedText("en", "SetPosition"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11593"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11575"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11593"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11594"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11593"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11593"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11575"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11593"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11594"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode51() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11615"), new QualifiedName(0, "ExportNamespace"), new LocalizedText("en", "ExportNamespace"), Optional.of(new LocalizedText("en", "Updates the file by exporting the server namespace.")), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11615"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11595"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=11615"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=80"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11615"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11595"), NodeClass.ObjectType, false));
        this.namespace.addNode(node);
    }

    private void buildNode52() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11629"), new QualifiedName(0, "Open"), new LocalizedText("en", "Open"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11629"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11624"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11629"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11630"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11629"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11631"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11629"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11629"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11624"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11629"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11630"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11629"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11631"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode53() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11632"), new QualifiedName(0, "Close"), new LocalizedText("en", "Close"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11632"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11624"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11632"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11633"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11632"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11632"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11624"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11632"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11633"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode54() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11634"), new QualifiedName(0, "Read"), new LocalizedText("en", "Read"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11634"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11624"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11634"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11635"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11634"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11636"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11634"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11634"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11624"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11634"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11635"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11634"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11636"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode55() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11637"), new QualifiedName(0, "Write"), new LocalizedText("en", "Write"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11637"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11624"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11637"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11638"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11637"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11637"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11624"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11637"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11638"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode56() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11639"), new QualifiedName(0, "GetPosition"), new LocalizedText("en", "GetPosition"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11639"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11624"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11639"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11640"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11639"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11641"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11639"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11639"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11624"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11639"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11640"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11639"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11641"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode57() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11642"), new QualifiedName(0, "SetPosition"), new LocalizedText("en", "SetPosition"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11642"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11624"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11642"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11643"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11642"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11642"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11624"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11642"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11643"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode58() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11680"), new QualifiedName(0, "Open"), new LocalizedText("en", "Open"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11680"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11675"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11680"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11681"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11680"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11682"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11680"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11680"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11675"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11680"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11681"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11680"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11682"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode59() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11683"), new QualifiedName(0, "Close"), new LocalizedText("en", "Close"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11683"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11675"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11683"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11684"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11683"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11683"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11675"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11683"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11684"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode60() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11685"), new QualifiedName(0, "Read"), new LocalizedText("en", "Read"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11685"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11675"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11685"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11686"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11685"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11687"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11685"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11685"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11675"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11685"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11686"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11685"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11687"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode61() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11688"), new QualifiedName(0, "Write"), new LocalizedText("en", "Write"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11688"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11675"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11688"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11689"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11688"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11688"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11675"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11688"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11689"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode62() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11690"), new QualifiedName(0, "GetPosition"), new LocalizedText("en", "GetPosition"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11690"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11675"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11690"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11691"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11690"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11692"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11690"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11690"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11675"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11690"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11691"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11690"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11692"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode63() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=11693"), new QualifiedName(0, "SetPosition"), new LocalizedText("en", "SetPosition"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=11693"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11675"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11693"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11694"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=11693"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=11693"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=11675"), NodeClass.Object, false));
        node.addReference(new Reference(NodeId.parse("i=11693"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=11694"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode64() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=3875"), new QualifiedName(0, "ConditionRefresh"), new LocalizedText("en", "ConditionRefresh"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=3875"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2782"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=3875"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=3876"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=3875"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=2787"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=3875"), NodeId.parse("i=3065"), ExpandedNodeId.parse("svr=0;i=2788"), NodeClass.ObjectType, true));
        node.addReference(new Reference(NodeId.parse("i=3875"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=2782"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=3875"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=3876"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode65() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12233"), new QualifiedName(0, "FindApplication"), new LocalizedText("en", "FindApplication"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12233"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12231"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12233"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12234"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12233"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12235"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12233"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12233"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12231"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12233"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12234"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12233"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12235"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode66() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12236"), new QualifiedName(0, "RegisterApplication"), new LocalizedText("en", "RegisterApplication"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12236"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12231"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12236"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12237"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12236"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12238"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12236"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12236"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12231"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12236"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12237"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12236"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12238"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode67() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12239"), new QualifiedName(0, "UnregisterApplication"), new LocalizedText("en", "UnregisterApplication"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12239"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12231"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12239"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12240"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12239"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12239"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12231"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12239"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12240"), NodeClass.Variable, true));
        this.namespace.addNode(node);
    }

    private void buildNode68() {
        UaMethodNode node = new UaMethodNode(this.namespace, NodeId.parse("i=12241"), new QualifiedName(0, "QueryServers"), new LocalizedText("en", "QueryServers"), Optional.empty(), Optional.of(UInteger.valueOf(0L)), Optional.of(UInteger.valueOf(0L)), true, true);
        node.addReference(new Reference(NodeId.parse("i=12241"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12231"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12241"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12242"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12241"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12243"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12241"), NodeId.parse("i=37"), ExpandedNodeId.parse("svr=0;i=78"), NodeClass.Object, true));
        node.addReference(new Reference(NodeId.parse("i=12241"), NodeId.parse("i=47"), ExpandedNodeId.parse("svr=0;i=12231"), NodeClass.ObjectType, false));
        node.addReference(new Reference(NodeId.parse("i=12241"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12242"), NodeClass.Variable, true));
        node.addReference(new Reference(NodeId.parse("i=12241"), NodeId.parse("i=46"), ExpandedNodeId.parse("svr=0;i=12243"), NodeClass.Variable, true));
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
    }

}
