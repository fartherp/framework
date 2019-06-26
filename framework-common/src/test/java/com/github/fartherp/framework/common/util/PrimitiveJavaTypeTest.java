/*
 * Copyright (c) 2017. CK. All rights reserved.
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
