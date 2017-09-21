/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.testng.annotations.Test;

public class Num62Test {

    @Test
    public void testLongToN62() throws Exception {
        System.out.println(Num62.longToN62(Long.MAX_VALUE));
    }
}