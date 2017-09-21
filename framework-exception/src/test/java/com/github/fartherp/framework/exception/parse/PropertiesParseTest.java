/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.exception.parse;

import com.github.fartherp.framework.exception.BaseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Enumeration;
import java.util.Properties;

public class PropertiesParseTest {
    @Test
    public void testGetInstance() throws Exception {
        Properties properties = PropertiesParse.getProperties(BaseException.COMMON_EXCEPTION_MESSAGE);
        Enumeration enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String s = enumeration.nextElement().toString();
            Throwable t = new IllegalArgumentException();
            if (s.equals(t.getClass().getCanonicalName())) {
                Assert.assertEquals("非法参数", properties.get(s));
                break;
            }
        }
    }

    @Test
    public void testGetProperty() throws Exception {
        Properties properties = PropertiesParse.getProperties(BaseException.COMMON_EXCEPTION_MESSAGE);
        String IOException = properties.get("java.io.IOException").toString();
        Assert.assertEquals(IOException, "IO系统出错");
    }

    @Test
    public void testGetProperties() throws Exception {
        Properties properties = PropertiesParse.getProperties(BaseException.MYSQL_DATABASE);
        Assert.assertNotNull(properties);
    }
}