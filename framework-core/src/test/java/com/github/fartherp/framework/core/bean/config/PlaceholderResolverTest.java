/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.core.bean.config;

import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

import static org.testng.Assert.assertEquals;

public class PlaceholderResolverTest {

    @Test
    public void testDoParse() {
        final Map<String, String> placeholderVals = new HashMap<String, String>(5);
        placeholderVals.put("key1", "china");
        placeholderVals.put("key2", "3");
        placeholderVals.put("key3", "beijin");
        String testStr = "hello ${key1}";
        PlaceholderResolver resolver = new PlaceholderResolver(placeholderVals::get);
        resolver.setPlaceholderPrefix("${");
        resolver.setPlaceholderSuffix("}");

        assertEquals(resolver.doParse(testStr), "hello china");
        testStr = "hello ${key${key2}}";
        assertEquals(resolver.doParse(testStr), "hello beijin");
    }
}
