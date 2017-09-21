/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import com.github.fartherp.framework.security.ISecurity;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class PBETest {
    public static void main(String[] args) {
        String key = "efg";
        ISecurity pbe = new PBE(key.getBytes());
        String plaintext = "root";
        byte[] encryption = pbe.encrypt(plaintext.getBytes());
        System.out.println(new String(encryption));
        System.out.println(new String(pbe.decrypt(encryption)));
    }
}
