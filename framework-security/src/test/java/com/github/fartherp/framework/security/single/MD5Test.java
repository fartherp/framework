/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.single;

import org.testng.annotations.Test;

import static org.testng.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class MD5Test {

    @Test
    public void testDigest() {
        byte[] data = "aaa".getBytes();
        String result = MD5.digest(data);
        assertEquals(result, "47bce5c74f589f4867dbd57e9ca9f808");
    }
}
