/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

public class Num62Test {

    @Test
    public void testLongToN62() throws Exception {
		assertEquals(Num62.longToN62(Long.MAX_VALUE), "AzL8n0Y58m7");
    }
}
