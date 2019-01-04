/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Base64;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class AESTest {
    @Test
    public void encrypt() {
        String data = "root1";
        byte[] key = "1111111111111111".getBytes();
        byte[] encryption = AES.encrypt(data.getBytes(), key);
        Assert.assertEquals("8/mudtZ/bQOhcV/K6JFrug==", Base64.getEncoder().encodeToString(encryption));
        String decryptData = new String(AES.decrypt(encryption, key));
        Assert.assertEquals(data, decryptData);
    }
}
