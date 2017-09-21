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
 * MD5加密(Message Digest algorithm 5，信息摘要算法)
 * 广泛用于加密和解密技术，常用于文件校验.
 * </pre>
 *
 * @author CK
 */
public class MD5 {
    /**
     * MD5加密
     *
     * @param bytes 需要加密字符串
     * @return 加密后的字符串
     */
    public static String digest(byte[] bytes) {
        try {
            MessageDigest md = MessageDigest.getInstance(ISecurity.MD5_ALGORITHM);
            BigInteger bigInteger = new BigInteger(md.digest(bytes));
            return bigInteger.toString(16);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此[" + ISecurity.MD5_ALGORITHM + "]算法", e);
        }
    }
}
