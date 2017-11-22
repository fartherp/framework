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
 * DES-ECB加密解密实现
 * <p>DES加密算法对密钥有严格要求,密钥必须是8字节,数据没有硬性要求</p>
 * <pre>
 *     CBC加密：
 *          密钥为8字节长，DK。DATA 按8字节截取 D1, D2, D3... Dn，Dn长度对8取余，补位。
 *          如：Dn长度1，补位8-1，0x070x070x070x070x070x070x07
 *              Dn长度3，补位8-3，0x050x050x050x050x05
 *              余数0，补位8-0，0x080x080x080x080x080x080x080x08
 *     加密方法：
 *       DES(D1, DK, TMP2);
 *       DES(TMP2, DK, TMP3);
 *       ...
 *       DES(TMPN, DK, DEST);
 *      Example:
 *          密钥: 1111111111111111
 *          DATA: 2222222222222222 补位: 0808080808080808 ALL_DATA: 22222222222222220808080808080808
 *          DEST: 950973182317F80BB95374BA8DDFF8C2
 * </pre>
 * Author: CK.
 * Date: 2015/4/11.
 */
public class DES extends SymmetrySecurity {

    public DES(byte[] key) {
        this.key = key;
    }

    public void validation(byte[] data, byte[] key) {
        if (key.length != 8) {
            throw new RuntimeException("Invalid DES key length (must be 8 bytes)");
        }
    }

    public Cipher getCipher() throws NoSuchPaddingException, NoSuchAlgorithmException {
        return Cipher.getInstance(DES_ECB_ALGORITHM);
    }

    /**
     * <pre>
     * SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ConfigureEncryptAndDecrypt.DES_ECB_ALGORITHM);
     * DESKeySpec deSedeKeySpec = new DESKeySpec(key);
     * secretKeyFactory.generateSecret(deSedeKeySpec);
     * </pre>
     */
    public Key generateRandomKey(byte[] key) throws NoSuchAlgorithmException, InvalidKeyException, InvalidKeySpecException {
        return new SecretKeySpec(key, DES_ECB_ALGORITHM);
    }

    public AlgorithmParameterSpec getAlgorithmParameterSpec() {
        return null;
    }
}
