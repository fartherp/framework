/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.single;

import org.testng.annotations.Test;

public class BASE64Test {
    String str = "12345678";

    @Test
    public void testEncryptBASE64() throws Exception {
        String encrypt = BASE64.encryptBASE64(str);
        System.out.println(encrypt);
    }

    @Test
    public void testDecryptBASE64() throws Exception {
        String encrypt = BASE64.encryptBASE64(str);
        String decrypt = BASE64.decryptBASE64(encrypt);
        System.out.println(decrypt);
    }
}