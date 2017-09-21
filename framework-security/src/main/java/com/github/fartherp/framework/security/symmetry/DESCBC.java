/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;

import static com.github.fartherp.framework.security.ISecurity.DES_CBC_ALGORITHM;
import static com.github.fartherp.framework.security.ISecurity.DES_ECB_ALGORITHM;

/**
 * DES-CBC加密解密实现
 * <p>DES加密算法对密钥有严格要求,密钥必须是8字节,此算法需要初始向量,初始向量必须的8字节,数据没有硬性要求</p>
 * @author CK
 */
public class DESCBC extends SymmetrySecurity {
    public DESCBC(byte[] desIv, byte[] key) {
        this.desIv = desIv;
        this.key = key;
    }

    public void validation(byte[] data, byte[] key) {
        if (key.length != 8) {
            throw new RuntimeException("Invalid DESCBC key length (must be 8 bytes)");
        }
        if (this.desIv == null || this.desIv.length != 8) {
            throw new RuntimeException("Invalid DESCBC iv length (must be 8 bytes)");
        }
    }

    public Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(DES_CBC_ALGORITHM);
    }

    /**
     * SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ConfigureEncryptAndDecrypt.DES_ECB_ALGORITHM);
     * DESKeySpec deSedeKeySpec = new DESKeySpec(key);
     * secretKeyFactory.generateSecret(deSedeKeySpec);
     * @param key the key array
     * @return
     * @throws NoSuchAlgorithmException
     * @throws InvalidKeyException
     * @throws InvalidKeySpecException
     */
    public Key generateRandomKey(byte [] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        return new SecretKeySpec(key, DES_ECB_ALGORITHM);
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return new IvParameterSpec(this.desIv);
    }
}
