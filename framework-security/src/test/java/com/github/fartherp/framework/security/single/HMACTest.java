/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.single;

import base64.Base64Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HMACTest {

    @Test
    public void testEncrypt() throws Exception {
        byte[] key = HMAC.initMacKey();
        key = Base64Utils.decode("aDoeS0jpEa7R6YssPU7gZvf95RYH4slqbQgr2gpijhviXyOa16xxOAYmlg0VqBKTE0QPYB26wySLruNJNsbO3A==");
        byte[] data = "aaaa".getBytes();
        byte[] encryptData = HMAC.encrypt(data, key);
        String result = Base64Utils.encode(encryptData);
        Assert.assertEquals("omXf1QfFYGlZ+SshA2twjw==", result);
    }
}