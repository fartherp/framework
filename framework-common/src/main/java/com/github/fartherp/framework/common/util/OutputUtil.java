/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.common.util;

/**
 * Created by IntelliJ IDEA.
 * @author CK
 * @date 2015/8/15
 */
public class OutputUtil {

    /** 行分割符 */
    private static final String LINE_SEPARATOR;

    static {
        String ls = System.getProperty("line.separator");
        if (ls == null) {
            ls = "\n";
        }
        LINE_SEPARATOR = ls;
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
        sb.append(LINE_SEPARATOR);
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
