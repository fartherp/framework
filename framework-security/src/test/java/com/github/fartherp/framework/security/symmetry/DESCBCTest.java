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
public class DESCBCTest {
    public static void main(String[] args) {
        ISecurity desSecurity = new DESCBC("12345678".getBytes(),"admin@va".getBytes());
        byte[] encryption = desSecurity.encrypt("asdf".getBytes());
        System.out.println(new String(encryption));
        System.out.println(new String(desSecurity.decrypt(encryption)));
    }
}
