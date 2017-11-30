/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.single;

import org.testng.Assert;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class SHATest {
    public static void main(String[] args) {
        byte[] data = "aaa".getBytes();
        String result = SHA.digest(data);
        Assert.assertEquals("7e240de74fb1ed08fa08d38063f6a6a91462a815", result);
    }
}
