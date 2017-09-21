/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import static com.github.fartherp.framework.security.ISecurity.PBE_ALGORITHM;

/**
 * <pre>
 * Password-based encryption（基于密码加密）.
 * 其特点在于口令由用户自己掌管，不借助任何物理媒体；采用随机数（这里我们叫做盐）杂凑多重加密等方法保证数据的安全性
 * </pre>
 * Author: CK
 * Date: 2015/4/13
 */
public class PBE extends SymmetrySecurity {

    public PBE(byte[] key) {
        this.key = key;
        salt = initSalt();
    }

    private byte[] salt;

    public void validation(byte[] data, byte[] key) {

    }

    public Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(PBE_ALGORITHM);
    }

    public Key generateRandomKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException, UnsupportedEncodingException {
        String keyString = new String(key);
        PBEKeySpec keySpec = new PBEKeySpec(keyString.toCharArray());
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBE_ALGORITHM);
        return keyFactory.generateSecret(keySpec);
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return new PBEParameterSpec(this.salt, 100);
    }

    public byte[] initSalt() {
        byte[] salt = new byte[8];
        Random random = new Random();
        random.nextBytes(salt);
        return salt;
    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }
}
