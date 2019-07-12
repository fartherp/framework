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
package com.github.fartherp.framework.exception.parse;

import com.github.fartherp.framework.exception.BaseException;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Enumeration;
import java.util.Properties;

import static org.testng.Assert.assertEquals;

public class PropertiesParseTest {

    @Test
    public void testGetInstance() {
        Properties properties = PropertiesParse.getProperties(BaseException.COMMON_EXCEPTION_MESSAGE);
        Enumeration enumeration = properties.propertyNames();
        while (enumeration.hasMoreElements()) {
            String s = enumeration.nextElement().toString();
            Throwable t = new IllegalArgumentException();
            if (s.equals(t.getClass().getCanonicalName())) {
                assertEquals("非法参数", properties.get(s));
                break;
            }
        }
    }

    @Test
    public void testGetProperty() {
        Properties properties = PropertiesParse.getProperties(BaseException.COMMON_EXCEPTION_MESSAGE);
        String IOException = properties.get("java.io.IOException").toString();
        assertEquals(IOException, "IO系统出错");
    }

    @Test
    public void testGetProperties() {
        Properties properties = PropertiesParse.getProperties(BaseException.MYSQL_DATABASE);
        Assert.assertNotNull(properties);
    }
}
