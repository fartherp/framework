/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.single;

import com.github.fartherp.framework.security.ISecurity;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * SHA(Secure Hash Algorithm，安全散列算法)数字签名等密码学应用中重要的工具
 * 广泛地应用于电子商务等信息安全领域,但是SHA仍然是公认的安全加密算法，较之MD5更为安全
 * </pre>
 * @author CK
 */
public class SHA {
    /**
     * 对报文进行SHA签名
     *
     * @param bytes   - 待签名的字符串
     * @return - 签名结果，hex字符串
     */
    public static String digest(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance(ISecurity.SHA_ALGORITHM);
            BigInteger bigInteger = new BigInteger(md.digest(bytes));
            return bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此[" + ISecurity.SHA_ALGORITHM + "]算法", e);
        }
    }
}
