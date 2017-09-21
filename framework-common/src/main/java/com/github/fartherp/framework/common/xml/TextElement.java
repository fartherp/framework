/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.xml;

/**
 * 文本元素
 * Author: CK
 * Date: 2015/8/15
 */
public class TextElement implements Element {

    private String content;

    public TextElement(String content) {
        super();
        this.content = content;
    }

    public String getFormattedContent(int indentLevel) {
        return content;
    }
}
