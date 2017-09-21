/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.code;

import org.testng.annotations.Test;

public class CoderTest {

    @Test
    public void testASCIIToEBCDIC() throws Exception {
        byte []c  = new byte[]{1,2,3,4};
        byte a[] = Coder.ASCIIToEBCDIC(c);
        for (byte b : a) {
            System.out.println(b);
        }
    }
}