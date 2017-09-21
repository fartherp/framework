/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.single;

import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class MD5Test {

    @Test
    public void main() {
        String s = MD5.digest("aaa".getBytes());
        System.out.println(s);
    }
}
