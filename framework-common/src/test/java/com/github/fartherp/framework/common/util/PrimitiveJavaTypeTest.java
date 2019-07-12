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

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class PrimitiveJavaTypeTest {

    @Test
    public void testCovertValue() throws Exception {
        Object obj = PrimitiveJavaType.covertValue(Integer.class, "11");
        assertEquals(obj, 11);
    }

    @Test
    public void testGetByFullyQualifiedName() {
        PrimitiveJavaType primitiveJavaType = PrimitiveJavaType.getByFullyQualifiedName("java.lang.Boolean");
        assertNotNull(primitiveJavaType);
    }

    @Test
    public void testGetByShortName() {
        PrimitiveJavaType primitiveJavaType = PrimitiveJavaType.getByShortName("boolean");
        assertNotNull(primitiveJavaType);
    }
}
