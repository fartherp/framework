/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.dissymmetry;

import base64.Base64Utils;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class DSATest {
    public static void main(String[] args) {
        String inputStr = "abc";

        // 构建密钥
        DisSymmetryKey disSymmetryKey = DSA.initKey();

        // 获得密钥
        byte[] publicKey = disSymmetryKey.getPublicKey();
        byte[] privateKey = disSymmetryKey.getPrivateKey();

        System.out.println("公钥:" + Base64Utils.encode(publicKey));
        System.out.println("私钥:" + Base64Utils.encode(privateKey));

        // 产生签名
        byte[] sign = DSA.sign(inputStr.getBytes(), privateKey);
        System.out.println("签名:" + Base64Utils.encode(sign));

        // 验证签名
        boolean status = DSA.verify(inputStr.getBytes(), publicKey, sign);
        System.out.println("状态:" + status);
    }
}
