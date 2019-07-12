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

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class SystemPropertyUtilTest {

    @BeforeMethod
    public void clearSystemPropertyBeforeEach() {
        System.clearProperty("key");
    }

    @Test(expectedExceptions = NullPointerException.class)
    public void testGetWithKeyNull() {
        SystemPropertyUtil.get(null, null);
    }

    @Test(expectedExceptions = IllegalArgumentException.class)
    public void testGetWithKeyEmpty() {
        SystemPropertyUtil.get("", null);
    }

    @Test
    public void testGetDefaultValueWithPropertyNull() {
        assertEquals("default", SystemPropertyUtil.get("key", "default"));
    }

    @Test
    public void testGetPropertyValue() {
        System.setProperty("key", "value");
        assertEquals("value", SystemPropertyUtil.get("key"));
    }

    @Test
    public void getIntDefaultValueWithPropertyNull() {
        assertEquals(1, SystemPropertyUtil.getInt("key", 1));
    }

    @Test
    public void getIntWithPropertValueIsInt() {
        System.setProperty("key", "123");
        assertEquals(123, SystemPropertyUtil.getInt("key", 1));
    }

    @Test
    public void getIntDefaultValueWithPropertValueIsNotInt() {
        System.setProperty("key", "NotInt");
        assertEquals(1, SystemPropertyUtil.getInt("key", 1));
    }
}
