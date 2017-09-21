/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

/**
 * ThreeDES 加密&解密
 *
 * @author CK
 */
public class ThreeDES extends SymmetrySecurity {
    public ThreeDES (byte[] key) {
        this.key = key;
    }

    /**
     * 验证AES数据有效性及密钥有效性
     * @param data 加密数据(AES数据必须是24位)
     * @param key 密钥(AES密钥必须是24位)
     */
    public void validation(byte[] data, byte[] key) {
        if (key.length != 24) {
            throw new RuntimeException("Invalid 3DES key length (must be 24 bytes)");
        }
        if (data.length == 0 || data.length % 8 != 0) {
            throw new RuntimeException("Invalid 3DES data length (must be multiple of 8 bytes)");
        }
    }

    public Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(THREE_DES_ECB_ALGORITHM);
    }

    /**
     * SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ConfigureEncryptAndDecrypt.THREE_DES_ALGORITHM);
     * DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key);
     * secretKeyFactory.generateSecret(deSedeKeySpec);
     * @param key the key array
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    public Key generateRandomKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        return new SecretKeySpec(key, THREE_DES_ALGORITHM);
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return null;
    }
}
