/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.single;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class SHATest {
    public static void main(String[] args) {
        String s = SHA.digest("aaa".getBytes());
        System.out.println(s);
    }
}
