/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import base64.Base64Utils;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class DESTest {
    @Test
    public void testDES() {
        byte[] key = "11111111".getBytes();
        String data = "2222222222222222";
        byte[] encryption = DES.encrypt(data.getBytes(), key);
        Assert.assertEquals("+ScU6A6DLtz5JxToDoMu3K1qiLT6N4M9", Base64Utils.encode(encryption));
        Assert.assertEquals(data, new String(DES.decrypt(encryption, key)));
    }
}
