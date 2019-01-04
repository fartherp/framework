/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import org.testng.Assert;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class PBETest {
    public static void main(String[] args) throws InvalidKeySpecException, NoSuchAlgorithmException {
        String key = "efg";
        String data = "root";
        byte[] salt = PBE.initSalt();
        salt = Base64.getDecoder().decode("sgf8lG50gls=");
        byte[] encryption = PBE.encrypt(data.getBytes(), key.getBytes(), salt);
        Assert.assertEquals("p3ydxNVXnB4=", Base64.getEncoder().encodeToString(encryption));
        Assert.assertEquals(data, new String(PBE.decrypt(encryption, key.getBytes(), salt)));
    }
}
