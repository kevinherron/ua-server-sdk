package com.inductiveautomation.opcua.sdk.server.api.nodes.generators;

import java.beans.IntrospectionException;

import com.inductiveautomation.opcua.stack.core.types.builtin.NodeId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.Test;

public class UaObjectNodeGeneratorTest {

    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testGenerate() throws IntrospectionException {
        UaObjectNodeGenerator generator = new UaObjectNodeGenerator(
                s -> new NodeId(0, s)
        );

        UaObjectNodeGenerator.GeneratedNodes nodes = generator.generate(new TestBean());

        Assert.assertNotNull(nodes.getRoot());
        Assert.assertEquals(nodes.getChildren().size(), 2, "expected 2 generated nodes");
    }

    public static class TestBean {
        private final String foo = "foo";
        private final int bar = 42;

        public String getFoo() {
            return foo;
        }

        public int getBar() {
            return bar;
        }
    }

}
