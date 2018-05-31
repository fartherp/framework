/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/8/15
 */
public class OutputUtils {

    /** 行分割符 */
    private static final String lineSeparator;

    static {
        String ls = System.getProperty("line.separator");
        if (ls == null) {
            ls = "\n";
        }
        lineSeparator = ls;
    }

    /**
     * 对于前个级别的XML自增两个空格
     *
     * @param sb a StringBuilder to append to
     * @param indentLevel the required indent level
     */
    public static void xmlIndent(StringBuilder sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append("  ");
        }
    }

    /**
     * 对于前个级别的JAVA自增四个空格
     *
     * @param sb a StringBuilder to append to
     * @param indentLevel the required indent level
     */
    public static void javaIndent(StringBuilder sb, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            sb.append("    ");
        }
    }

    /**
     * 换行
     *
     * @param sb a StringBuilder to append to
     */
    public static void newLine(StringBuilder sb) {
        sb.append(lineSeparator);
    }

    /**
     * 替换XML字符
     *
     * @param xmlStr 字符串
     *
     * @return 字符串
     */
    public static String replaceXMLStr(String xmlStr) {
        return xmlStr == null ? null : xmlStr.replaceAll("<", "&lt;")
                .replaceAll(">", "&gt;")
                .replaceAll("&", "&amp;")
                .replaceAll("'", "&apos;")
                .replaceAll("\"", "&quot;");
    }
}
