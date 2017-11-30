/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.dissymmetry;

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.github.fartherp.framework.security.ISecurity.RSA_ALGORITHM;
import static com.github.fartherp.framework.security.ISecurity.SIGNATURE_ALGORITHM;

/**
 * <p>RSA公钥&私钥</p>
 *
 * @author CK
 */
public class RSA {

    /**
     * <p>创建公钥&私钥</p>
     *
     * @return the key
     */
    public static DisSymmetryKey initKey() {
        return DisSymmetry.initKey(RSA_ALGORITHM);
    }

    /**
     * <p>私钥签名</p>
     *
     * @param data    密文信息
     * @param privateKey 私钥
     * @return 私钥签名
     */
    public static byte[] sign(byte[] data, byte[] privateKey) {
        try {
            KeyFactory keyFactory = DisSymmetry.getKeyFactory(RSA_ALGORITHM);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            signature.initSign(privateK);
            signature.update(data);
            return signature.sign();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + RSA_ALGORITHM + "]获取签名算法错误", e);
        } catch (Exception e) {
            throw new RuntimeException("[" + RSA_ALGORITHM + "]私钥签名错误", e);
        }
    }

    /**
     * <p>公钥验签</p>
     *
     * @param data   密文
     * @param publicKey 公钥
     * @param sign      签名
     * @return true:验证成功，false:验证失败
     */
    public static boolean verify(byte[] data, byte[] publicKey, byte[] sign) {
        try {
            KeyFactory keyFactory = DisSymmetry.getKeyFactory(RSA_ALGORITHM);
            Signature signature = Signature.getInstance(SIGNATURE_ALGORITHM);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
            PublicKey publicK = keyFactory.generatePublic(keySpec);
            signature.initVerify(publicK);
            signature.update(data);
            return signature.verify(sign);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + RSA_ALGORITHM + "]获取签名算法错误", e);
        } catch (Exception e) {
            throw new RuntimeException("[" + RSA_ALGORITHM + "]公钥验签错误", e);
        }
    }

    /**
     * <p>公钥加密</p>
     *
     * @param data   明文信息
     * @param publicKey 公钥
     * @return byte[]
     */
    public static byte[] encryptByPublicKey(byte[] data, byte[] publicKey) {
        return DisSymmetry.encryptByPublicKey(RSA_ALGORITHM, data, publicKey);
    }

    /**
     * <P>私钥解密</p>
     *
     * @param data    密文信息
     * @param privateKey 私钥
     * @return byte[]
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] privateKey) {
        return DisSymmetry.decryptByPrivateKey(RSA_ALGORITHM, data, privateKey);
    }
}
