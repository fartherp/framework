/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.Assert;
import org.testng.annotations.Test;

public class PrimitiveJavaTypeTest {

    @Test
    public void testCovertValue() throws Exception {
        Object obj = PrimitiveJavaType.covertValue(Integer.class, "11");
        Assert.assertEquals(obj, 11);
    }

    @Test
    public void testGetByFullyQualifiedName() {
        PrimitiveJavaType primitiveJavaType = PrimitiveJavaType.getByFullyQualifiedName("java.lang.Boolean");
        Assert.assertNotNull(primitiveJavaType);
    }

    @Test
    public void testGetByShortName() {
        PrimitiveJavaType primitiveJavaType = PrimitiveJavaType.getByShortName("boolean");
        Assert.assertNotNull(primitiveJavaType);
    }
}