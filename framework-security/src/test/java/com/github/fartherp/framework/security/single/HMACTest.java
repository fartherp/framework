/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.single;

import org.testng.annotations.Test;

import java.util.Base64;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

public class HMACTest {

	@Test
	public void testInitMacKey() {
		assertNotNull(HMAC.initMacKey());
	}

    @Test
    public void testEncrypt() {
        byte[] key = Base64.getDecoder().decode("aDoeS0jpEa7R6YssPU7gZvf95RYH4slqbQgr2gpijhviXyOa16xxOAYmlg0VqBKTE0QPYB26wySLruNJNsbO3A==");
        byte[] data = "aaaa".getBytes();
        byte[] encryptData = HMAC.encrypt(data, key);
        String result = Base64.getEncoder().encodeToString(encryptData);
        assertEquals(result, "omXf1QfFYGlZ+SshA2twjw==");
    }
}
