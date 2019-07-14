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
package com.github.fartherp.framework.core.bean.config;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashSet;
import java.util.Set;

/**
 * 字符串占位符解析工作类.<br>
 * 例如: String testStr = "abc${ab}" 如果ab="ddd",则解析后结果为 abcddd<br>
 * <pre>
 * example:
 *      Map&lt;String, String&gt; placeholderVals = new HashMap&lt;&gt;(5);
 *      placeholderVals.put("key1", "china");
 *      placeholderVals.put("key2", "3");
 *      placeholderVals.put("key3", "beijin");
 *
 *      String testStr = "hello ${key1}";
 *
 *      PlaceholderResolver resolver = new PlaceholderResolver(placeholderVals::get);
 *      resolver.setPlaceholderPrefix("${");
 *      resolver.setPlaceholderSuffix("}");
 *      assertEquals(resolver.doParse(testStr), "hello china");
 *
 *      testStr = "hello ${key${key2}}";
 *      assertEquals(resolver.doParse(testStr), "hello beijin");
 * </pre>
 * <p/>
 * @author CK
 * @date 2016/8/29
 */
public class PlaceholderResolver {
    /**
     * Logger for this class
     */
    private static final Log LOGGER = LogFactory.getLog(PlaceholderResolver.class);

    /**
     * Default placeholder prefix: "${"
     */
    public static final String DEFAULT_PLACEHOLDER_PREFIX = "${";

    /**
     * Default placeholder suffix: "}"
     */
    public static final String DEFAULT_PLACEHOLDER_SUFFIX = "}";

    /**
     * placeholder prefix
     */
    private String placeholderPrefix = DEFAULT_PLACEHOLDER_PREFIX;

    /**
     * placeholder suffix
     */
    private String placeholderSuffix = DEFAULT_PLACEHOLDER_SUFFIX;

    /**
     * {@link PlaceholderResolved} instance.
     */
    private PlaceholderResolved resolvedInterceptor;

    /**
     * all parsed placeholders will store here
     */
    private Set<String> visitedPlaceholders = new HashSet<>(50);

    /**
     * Constructor method
     *
     * @param resolvedInterceptor {@link PlaceholderResolved} can not be null.
     */
    public PlaceholderResolver(PlaceholderResolved resolvedInterceptor) {
        if (resolvedInterceptor == null) {
            throw new IllegalArgumentException("property 'resolvedInterceptor' is null");
        }
        this.resolvedInterceptor = resolvedInterceptor;
    }

    /**
     * Do parser placeholder action.
     *
     * @param strVal target string to parser
     * @return target string after placeholder parse
     */
    public String doParse(String strVal) {
        if (strVal == null) {
            return null;
        }
        return parseStringValue(strVal, visitedPlaceholders);
    }

    /**
     * test if target string contains placeholderPrefix
     *
     * @param strVal target string to test
     * @return true if string contains placeholderPrefix
     */
    public boolean hasPlaceHolder(String strVal) {
        if (StringUtils.isBlank(strVal)) {
            return false;
        }
        int startIndex = strVal.indexOf(this.placeholderPrefix);
        return startIndex != -1;
    }

    /**
     * Parse the given String value recursively, to be able to resolve nested
     * placeholders (when resolved property values in turn contain placeholders
     * again).
     *
     * @param strVal              the String value to parse
     * @param visitedPlaceholders the placeholders that have already been visited
     *                            during the current resolution attempt (used to detect circular references
     *                            between placeholders). Only non-null if we're parsing a nested placeholder.
     */
    protected String parseStringValue(String strVal, Set<String> visitedPlaceholders) {
        StringBuilder buf = new StringBuilder(strVal);
        int startIndex = strVal.indexOf(this.placeholderPrefix);
        while (startIndex != -1) {
            int endIndex = findPlaceholderEndIndex(buf, startIndex);
            if (endIndex != -1) {
                String placeholder = buf.substring(startIndex + this.placeholderPrefix.length(), endIndex);
                if (!visitedPlaceholders.add(placeholder)) {
                    throw new RuntimeException(
                            "Circular placeholder reference '" + placeholder + "' in property definitions");
                }
                // Recursive invocation, parsing placeholders contained in the
                // placeholder key.
                placeholder = parseStringValue(placeholder, visitedPlaceholders);
                // Now obtain the value for the fully resolved key...
                String propVal = resolvedInterceptor.doResolved(placeholder);

                if (propVal != null) {
                    // Recursive invocation, parsing placeholders contained in the
                    // previously resolved placeholder value.
                    propVal = parseStringValue(propVal, visitedPlaceholders);
                    buf.replace(startIndex, endIndex + this.placeholderSuffix.length(), propVal);
                    if (LOGGER.isDebugEnabled()) {
                        LOGGER.debug("Resolved placeholder '" + placeholder + "'");
                    }
                    startIndex = buf.indexOf(this.placeholderPrefix, startIndex + propVal.length());
                } else {
                    startIndex = buf.indexOf(this.placeholderPrefix, endIndex + this.placeholderSuffix.length());
                }
                visitedPlaceholders.remove(placeholder);
            } else {
                startIndex = -1;
            }
        }

        return buf.toString();
    }

    /**
     * To find placeholder position from index.
     *
     * @param buf        target string
     * @param startIndex start index
     * @return -1 if not found or return position index.
     */
    private int findPlaceholderEndIndex(CharSequence buf, int startIndex) {
        int index = startIndex + this.placeholderPrefix.length();
        int withinNestedPlaceholder = 0;
        while (index < buf.length()) {
            if (substringMatch(buf, index, this.placeholderSuffix)) {
                if (withinNestedPlaceholder > 0) {
                    withinNestedPlaceholder--;
                    index = index + this.placeholderSuffix.length();
                } else {
                    return index;
                }
            } else if (substringMatch(buf, index, this.placeholderPrefix)) {
                withinNestedPlaceholder++;
                index = index + this.placeholderPrefix.length();
            } else {
                index++;
            }
        }
        return -1;
    }

    /**
     * Test whether the given string matches the given substring
     * at the given index.
     *
     * @param str       the original string (or StringBuffer)
     * @param index     the index in the original string to start matching against
     * @param substring the substring to match at the given index
     */
    public static boolean substringMatch(CharSequence str, int index, CharSequence substring) {
        for (int j = 0; j < substring.length(); j++) {
            int i = index + j;
            if (i >= str.length() || str.charAt(i) != substring.charAt(j)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param placeholderPrefix the placeholderPrefix to set
     */
    public void setPlaceholderPrefix(String placeholderPrefix) {
        this.placeholderPrefix = placeholderPrefix;
    }

    /**
     * @param placeholderSuffix the placeholderSuffix to set
     */
    public void setPlaceholderSuffix(String placeholderSuffix) {
        this.placeholderSuffix = placeholderSuffix;
    }
}
