/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import org.testng.annotations.Test;

import java.util.Base64;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class PBETest {

	@Test
	public void testInitSalt() {
		assertNotNull(PBE.initSalt());
	}

	@Test
    public void testEncryptAndDecrypt() {
        String key = "efg";
        String data = "root";
        byte[] salt = Base64.getDecoder().decode("sgf8lG50gls=");
        byte[] encryption = PBE.encrypt(data.getBytes(), key.getBytes(), salt);
        assertEquals(Base64.getEncoder().encodeToString(encryption), "p3ydxNVXnB4=");
        assertEquals(new String(PBE.decrypt(encryption, key.getBytes(), salt)), data);
    }
}
