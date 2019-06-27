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
public class SHATest {

	@Test
    public void testDigest() {
        byte[] data = "aaa".getBytes();
        String result = SHA.digest(data);
        assertEquals(result, "7e240de74fb1ed08fa08d38063f6a6a91462a815");
    }
}
