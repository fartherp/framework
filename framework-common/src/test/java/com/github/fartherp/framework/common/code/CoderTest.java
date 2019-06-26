/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.code;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class CoderTest {

    @Test
    public void testASCIIToEBCDIC() {
        byte[]c  = new byte[]{1,2,3,4};
		byte[] a = Coder.ASCIIToEBCDIC(c);
		assertEquals(a[0], 1);
		assertEquals(a[1], 2);
		assertEquals(a[2], 3);
		assertEquals(a[3], 55);
    }
}
