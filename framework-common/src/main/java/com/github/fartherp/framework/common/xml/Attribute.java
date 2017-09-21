/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.xml;

/**
 * 属性字符拼接
 * <pre>
 * <code>name="value"</code>
 * </pre>
 * Author: CK
 * Date: 2015/8/15
 */
public class Attribute implements Element {
    /**
     * 属性名
     */
    private String name;
    /**
     * 属性值
     */
    private String value;

    public Attribute(String name, String value) {
        super();
        this.name = name;
        this.value = value;
    }

    public String getFormattedContent(int indentLevel) {
        return name + "=\"" + value + '\"';
    }

    public String getName() {
        return name;
    }

    public String getValue() {
        return value;
    }
}
