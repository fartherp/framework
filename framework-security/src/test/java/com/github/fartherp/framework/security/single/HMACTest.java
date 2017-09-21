/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.single;

import com.github.fartherp.framework.common.util.ISOUtil;
import org.testng.annotations.Test;

public class HMACTest {

    @Test
    public void testEncrypt() throws Exception {
        HMAC hmac = new HMAC();
        String key = hmac.getKey();
        System.out.println(key);
        byte[] a = hmac.encrypt("aaaa".getBytes());
        String result = ISOUtil.hexString(a);
        System.out.println(result);
    }
}