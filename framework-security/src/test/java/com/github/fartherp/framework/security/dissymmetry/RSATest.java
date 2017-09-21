/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.dissymmetry;

import com.github.fartherp.framework.security.TSecurity;

import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class RSATest {
    public static void main(String[] args) {
        TSecurity tSecurity = new RSA();
        String inputStr = "abc";

        // 构建密钥
        Map<String, Object> keyMap = tSecurity.initKey();

        // 获得密钥
        String publicKey = tSecurity.getPublicKey();
        String privateKey = tSecurity.getPrivateKey();

        System.out.println("公钥:" + publicKey);
        System.out.println("私钥:" + privateKey);

        // 产生签名
        String sign = tSecurity.sign(inputStr, privateKey);
        System.out.println("签名:" + sign);

        // 验证签名
        boolean status = tSecurity.verify(inputStr, publicKey, sign);
        System.out.println("状态:" + status);
        String str = tSecurity.encryptByPublicKey(inputStr, publicKey);
        System.out.println("加密数据:" + str);
        System.out.println("解密数据:" +tSecurity.decryptByPrivateKey(str, privateKey));
    }
}
