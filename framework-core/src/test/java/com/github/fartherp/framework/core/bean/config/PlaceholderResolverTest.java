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
