/*
 * Copyright (c) 2019. CK. All rights reserved.
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
