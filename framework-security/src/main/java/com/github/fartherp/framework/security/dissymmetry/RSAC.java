/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.dissymmetry;

import com.github.fartherp.framework.security.ISecurity;

import javax.crypto.Cipher;
import java.math.BigInteger;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.spec.RSAPrivateKeySpec;
import java.security.spec.RSAPublicKeySpec;

import static com.github.fartherp.framework.security.ISecurity.RSA_ALGORITHM;

/**
 * RSA与C语言通用加密
 *
 * @author: CK
 * @date: 2018/1/29
 */
public class RSAC {

    public static byte[] encryptByPublicKey(byte[] data, String ns, String es) {
        return encryptByPublicKey(data, ns, es, ISecurity.RSA_ECB_ALGORITHM);
    }

    /**
     * <p>公钥加密</p>
     * @param data 明文信息
     * @param ns 模数
     * @param es 公钥
     * @param cipherS RSA/ECB/OAEPWithSHA1AndMGF1Padding  RSA/ECB/nopadding RSA/ECB/PKCS1Padding
     *                RSA/None/PKCS1Padding RSA/None/NoPadding  RSA/ECB/OAEPPADDING
     * @return 加密byte数组
     */
    public static byte[] encryptByPublicKey(byte[] data, String ns, String es, String cipherS) {
        try {
            BigInteger n = new BigInteger(ns, 16);
            BigInteger e = new BigInteger(es, 16);
            KeyFactory keyFactory = DisSymmetry.getKeyFactory(RSA_ALGORITHM);
            RSAPublicKeySpec keySpec = new RSAPublicKeySpec(n, e);
            Key publicK = keyFactory.generatePublic(keySpec);
            Cipher cipher = Cipher.getInstance(cipherS);
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            return cipher.doFinal(data);
        }  catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + RSA_ALGORITHM + "]获取密钥工厂错误", e);
        } catch (Exception e) {
            throw new RuntimeException("[" + RSA_ALGORITHM + "]公钥加密错误", e);
        }
    }

    public static byte[] decryptByPrivateKey(byte[] data, String ns, String ds) {
        return decryptByPrivateKey(data, ns, ds, ISecurity.RSA_ECB_ALGORITHM);
    }

    /**
     * <P>私钥解密</p>
     * @param data 密文信息
     * @param ns 模数
     * @param ds 私钥
     * @param cipherS RSA/ECB/OAEPWithSHA1AndMGF1Padding  RSA/ECB/nopadding RSA/ECB/PKCS1Padding
     *                RSA/None/PKCS1Padding RSA/None/NoPadding  RSA/ECB/OAEPPADDING
     * @return 解密byte数组
     */
    public static byte[] decryptByPrivateKey(byte[] data, String ns, String ds, String cipherS) {
        try {
            BigInteger n = new BigInteger(ns, 16);
            BigInteger d = new BigInteger(ds, 16);
            KeyFactory keyFactory = DisSymmetry.getKeyFactory(RSA_ALGORITHM);
            RSAPrivateKeySpec prvKeySpec = new RSAPrivateKeySpec(n, d);
            Key privateK = keyFactory.generatePrivate(prvKeySpec);
            Cipher cipher = Cipher.getInstance(cipherS);
            cipher.init(Cipher.DECRYPT_MODE, privateK);
            return cipher.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + RSA_ALGORITHM + "]获取密钥工厂错误", e);
        } catch (Exception e) {
            throw new RuntimeException("[" + RSA_ALGORITHM + "]私钥解密错误", e);
        }
    }
}
