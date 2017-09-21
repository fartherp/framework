/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.bean.config;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class PlaceholderResolverTest {

    @Test
    public void testDoParse() throws Exception {
        final Map<String, String> placeholderVals = new HashMap<String, String>(5);
        placeholderVals.put("key1", "china");
        placeholderVals.put("key2", "3");
        placeholderVals.put("key3", "beijin");
        String testStr = "hello ${key1}";
        PlaceholderResolver resolver = new PlaceholderResolver(new PlaceholderResolved() {
            public String doResolved(String placeholder) {
                System.out.println("find placeholder:" + placeholder);
                return placeholderVals.get(placeholder);
            }
        });
        resolver.setPlaceholderPrefix("${");
        resolver.setPlaceholderSuffix("}");

        System.out.println(resolver.doParse(testStr));
        testStr = "hello ${key${key2}}";
        System.out.println(resolver.doParse(testStr));
    }
}