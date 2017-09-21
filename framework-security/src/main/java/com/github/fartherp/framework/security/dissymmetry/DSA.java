/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.dissymmetry;

import com.github.fartherp.framework.security.ISecurity;
import com.github.fartherp.framework.security.single.BASE64;

import java.security.KeyFactory;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.Signature;

/**
 * DSA.
 * Digital Signature Algorithm，数字签名
 * Author: CK.
 * Date: 2015/4/11.
 */
public class DSA extends DisSymmetrySecurity {

    public KeyPairGenerator getKeyPairGenerator() throws NoSuchAlgorithmException {
        return KeyPairGenerator.getInstance(ISecurity.DSA_ALGORITHM);
    }

    public KeyFactory getKeyFactory() throws NoSuchAlgorithmException {
        return KeyFactory.getInstance(ISecurity.DSA_ALGORITHM);
    }

    public Signature getSignature(KeyFactory keyFactory) throws NoSuchAlgorithmException {
        return Signature.getInstance(keyFactory.getAlgorithm());
    }

    private static String encryptBASE64(byte[] data) {
        return BASE64.encryptBASE64(data);
    }

    private byte[] decryptBASE64(String data) {
        return BASE64.decryptBASE64B(data);
    }
}
