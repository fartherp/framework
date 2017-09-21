/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.xml;

import com.github.fartherp.framework.common.util.OutputUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 页面XML元素
 * Author: CK
 * Date: 2015/8/15
 */
public class WebXmlElement implements Element {
    /** 属性列表：parameterType="java.lang.Long" */
    private List<Attribute> attributes;

    /** 元素列表 */
    private List<Element> elements;

    /** 每个节点名称：input/a */
    private String name;

    /** 是否直接结尾 */
    private boolean end;

    public WebXmlElement(String name) {
        this(name, true);
    }

    public WebXmlElement(String name, boolean end) {
        super();
        attributes = new ArrayList<Attribute>();
        elements = new ArrayList<Element>();
        this.name = name;
        this.end = end;
    }

    public String getFormattedContent(int indentLevel) {
        StringBuilder sb = new StringBuilder();

        // 生成名称对应标签
        OutputUtils.xmlIndent(sb, indentLevel);
        sb.append('<');
        sb.append(this.name);

        // 放入标签属性
        for (Attribute att : this.attributes) {
            // 属性间,一个空格
            sb.append(' ');
            sb.append(att.getFormattedContent(0));
        }

        if (this.elements.size() > 0) {
            sb.append(">");
            for (Element element : this.elements) {
                sb.append(element.getFormattedContent(indentLevel + 1));
            }
            // 生成名称对应的结束标签
            OutputUtils.xmlIndent(sb, indentLevel);
            sb.append("</");
            sb.append(this.name);
            sb.append('>');
        } else {
            // 不包括,直接结尾
            if (end) {
                sb.append(" />");
            } else {
                sb.append('>');
                sb.append("</");
                sb.append(this.name);
                sb.append('>');
            }
        }

        return sb.toString();
    }

    public void addAttribute(Attribute attribute) {
        if (null != attribute) {
            attributes.add(attribute);
        }
    }

    public void addAttributeAll(List<Attribute> attribute) {
        if (null != attribute) {
            attributes.addAll(attribute);
        }
    }

    public void addElement(Element element) {
        if (null != element) {
            elements.add(element);
        }
    }

    public void addElementAll(List<Element> element) {
        if (null != element) {
            elements.addAll(element);
        }
    }
}
