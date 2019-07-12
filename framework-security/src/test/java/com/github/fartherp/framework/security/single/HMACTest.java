/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
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
