/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.single;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Base64;

public class HMACTest {

    @Test
    public void testEncrypt() throws Exception {
        byte[] key = HMAC.initMacKey();
        key = Base64.getDecoder().decode("aDoeS0jpEa7R6YssPU7gZvf95RYH4slqbQgr2gpijhviXyOa16xxOAYmlg0VqBKTE0QPYB26wySLruNJNsbO3A==");
        byte[] data = "aaaa".getBytes();
        byte[] encryptData = HMAC.encrypt(data, key);
        String result = Base64.getEncoder().encodeToString(encryptData);
        Assert.assertEquals("omXf1QfFYGlZ+SshA2twjw==", result);
    }
}