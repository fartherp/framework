/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.dissymmetry;

import org.testng.annotations.Test;

import static org.testng.Assert.assertTrue;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class DSATest {

	@Test
    public void testSignAndVerify() {
        String inputStr = "abc";

        // 构建密钥
        DisSymmetryKey disSymmetryKey = DSA.initKey();

        // 获得密钥
        byte[] publicKey = disSymmetryKey.getPublicKey();
        byte[] privateKey = disSymmetryKey.getPrivateKey();

        // 产生签名
        byte[] sign = DSA.sign(inputStr.getBytes(), privateKey);

        // 验证签名
        boolean status = DSA.verify(inputStr.getBytes(), publicKey, sign);
		assertTrue(status);
    }
}
