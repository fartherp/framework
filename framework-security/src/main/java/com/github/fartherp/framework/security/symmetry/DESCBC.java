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
 * <pre>
 *     CBC加密：
 *          密钥为8字节长，DK。DATA 按8字节截取 D1, D2, D3... Dn，Dn长度对8取余，补位。
 *          如：Dn长度1，补位8-1，0x070x070x070x070x070x070x07
 *              Dn长度3，补位8-3，0x050x050x050x050x05
 *              余数0，补位8-0，0x080x080x080x080x080x080x080x08
 *     加密方法：
 *       XOR(D1, IV, TMP1);
 *       DES(TMP1, DK, TMP2);
 *       XOR(D2, TMP2, TMP3);
 *       DES(TMP3, DK, TMP4);
 *       XOR(D3, TMP4, TMP5);
 *       ...
 *       DES(TMPN, DK, DEST);
 *      Example:
 *          密钥: 9BED98891580C3B2
 *          DATA: F4F3E7B3566F6622098750B491EA8D5C61 补位: 07070707070707 ALL_DATA: F4F3E7B3566F6622098750B491EA8D5C6107070707070707
 *          DEST: 5416B1A5537436105B6E2CAC0F87986F2968AC2DBFB464D0
 * </pre>
 * @author CK
 */
public class DESCBC extends SymmetrySecurity {

    public DESCBC(byte[] key) {
        this(DEFAULT_IV, key);
    }

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
