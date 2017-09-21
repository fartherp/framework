/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.xml;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class TextElementTest {

    @Test
    public void testGetFormattedContent() throws Exception {
        WebXmlElement webXmlElement = new WebXmlElement("a");
        Attribute href = new Attribute("href", "javascript:void(0)");
        webXmlElement.addAttribute(href);
        Attribute id = new Attribute("id", "auth");
        webXmlElement.addAttribute(id);
        Element element = new TextElement("分配权限");
        webXmlElement.addElement(element);
        String str = webXmlElement.getFormattedContent(0);
        assertEquals("<a href=\"javascript:void(0)\" id=\"auth\">分配权限</a>", str);
    }
}