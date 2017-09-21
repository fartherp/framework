/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import com.github.fartherp.framework.common.util.ISOUtil;
import com.github.fartherp.framework.security.ISecurity;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class AESTest {
    @Test
    public void encrypt() {
        ISecurity aes = new AES("1111111111111111".getBytes());
        String data = "root1";
        byte[] encryption = aes.encrypt(data.getBytes());
        System.out.println(new String(encryption));
        System.out.println(new String(aes.decrypt(encryption)));
    }

    @Test
    public void encrypt1() {
        ISecurity aes = new AES("2016080901161445".getBytes());
        byte [] data = "000000000000A43428481AE64383964A1".getBytes();
        byte [] encrypt = aes.encrypt(data);
        System.out.println(ISOUtil.hexString(encrypt));
        byte [] decrypt = aes.decrypt(encrypt);
        System.out.println(new String(decrypt));
    }
}
