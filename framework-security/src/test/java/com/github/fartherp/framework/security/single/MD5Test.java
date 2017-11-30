/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.single;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class MD5Test {

    @Test
    public void main() {
        byte[] data = "aaa".getBytes();
        String result = MD5.digest(data);
        Assert.assertEquals("47bce5c74f589f4867dbd57e9ca9f808", result);
    }
}
