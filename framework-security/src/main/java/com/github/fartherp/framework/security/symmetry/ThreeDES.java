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
 * <pre>
 *     双倍长加密：
 *          双倍长3DES，密钥为16字节长，按左右，分别LK（密钥的左边8字节），RK（密钥的右边8字节）。
 *     加密方法：
 *       DES(DATA, LK, TMP1);
 *       UDES(TMP1, RK, TMP2);
 *       DES(TMP2, LK, DEST);
 *      Example:
 *          密钥: 9BED98891580C3B245FE9EC58BFA8D2A LK: 9BED98891580C3B2 RK: 45FE9EC58BFA8D2A
 *          DATA: F4F3E7B3566F6622098750B491EA8D5C DEST: FE7B6C8A73167964798EBAC2BA4899AA
 * </pre>
 * <pre>
 *     三倍长加密：
 *          三倍长3DES，密钥长度的为24节长。可以分为LK（密钥的左边8字节）,CK（密钥的中间8字节）,RK（密钥的左边8字节）。
 *     加密方法：
 *       DES(DATA, LK, TMP1);
 *       UDES(TMP1, CK, TMP2);
 *       DES(TMP2, RK, DEST);
 *      Example:
 *          密钥: 9BED98891580C3B245FE9EC58BFA8D2A2DC5A7FEAB967E40 LK: 9BED98891580C3B2 CK: 45FE9EC58BFA8D2A RK:
 *          DATA: F4F3E7B3566F6622098750B491EA8D5C DEST: 738533847602379CDC0F3B7EF880C356
 * </pre>
 *
 * @author CK
 */
public class ThreeDES extends SymmetrySecurity {
    public ThreeDES (byte[] key) {
        if (key.length == 16) {
            // 双倍长
            byte[] keys = new byte[24];
            System.arraycopy(key, 0, keys,0, 16);
            System.arraycopy(key, 0, keys,16, 8);
            this.key = keys;
        } else if (key.length == 24) {
            // 三倍长
            this.key = key;
        } else {
            throw new RuntimeException("Invalid 3DES key length (must be 16 or 24 bytes)");
        }
    }

    /**
     * 验证ThreeES数据有效性及密钥有效性
     * @param data 加密数据(ThreeES数据必须是24位)
     * @param key 密钥(ThreeES密钥必须是24位)
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
     * <pre>
     * SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ConfigureEncryptAndDecrypt.THREE_DES_ALGORITHM);
     * DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(key);
     * secretKeyFactory.generateSecret(deSedeKeySpec);
     * </pre>
     */
    public Key generateRandomKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        return new SecretKeySpec(key, THREE_DES_ALGORITHM);
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return null;
    }
}
