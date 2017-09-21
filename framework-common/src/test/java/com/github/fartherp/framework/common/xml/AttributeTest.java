/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.xml;

import org.testng.annotations.Test;

public class AttributeTest {

    @Test
    public void testGetFormattedContent() throws Exception {
        Attribute attribute = new Attribute("id", "add");
        System.out.println(attribute.getFormattedContent(0));
    }
}