/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.dissymmetry;

import base64.Base64Utils;
import com.github.fartherp.framework.security.ISecurity;
import com.github.fartherp.framework.security.TSecurity;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.HashMap;
import java.util.Map;

/**
 * 1：私钥签名 2：公钥验签 3：公钥加密 4：私钥解密
 * Author: CK
 * Date: 2015/4/10
 */
public abstract class DisSymmetrySecurity implements TSecurity {
    /**
     * 数据编码,默认UTF-8
     */
    private String dataCharEncoding = ISecurity.ISO_CHAR_ENCODING;
    /**
     * 公钥 Key
     */
    protected static final String PUBLIC_KEY = "PublicKey";
    /**
     * 私钥 Key
     */
    protected static final String PRIVATE_KEY = "PrivateKey";

    /**
     * KEY_SIZE
     */
    private static final int KEY_SIZE = 1024;

    /**
     * RSA 最大加密
     */
    private static final int MAX_ENCRYPT_BLOCK = 117;

    /**
     * RSA 最大解密
     */
    private static final int MAX_DECRYPT_BLOCK = 128;

    private String seed = "0f22507a10bbddd07d8a3082122966e3";

    private Map<String, Object> keyMap = new HashMap<String, Object>(2);

    /**
     * <p>创建公钥&私钥</p>
     *
     * @return the Map
     */
    public Map<String, Object> initKey() {
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = getKeyPairGenerator();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]获取自增密钥错误", e);
        }
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(seed.getBytes());
        keyPairGen.initialize(KEY_SIZE, secureRandom);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        this.keyMap.put(PUBLIC_KEY, keyPair.getPublic());
        this.keyMap.put(PRIVATE_KEY, keyPair.getPrivate());
        return this.keyMap;
    }

    /**
     * <p>私钥签名</p>
     *
     * @param oriData    密文信息
     * @param privateKey 私钥
     * @return 私钥签名的字符串
     */
    public String sign(String oriData, String privateKey) {
        KeyFactory keyFactory = null;
        try {
            keyFactory = getKeyFactory();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]获取密钥工厂错误", e);
        }
        Signature signature = null;
        try {
            signature = getSignature(keyFactory);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]获取签名算法错误", e);
        }
        try {
            byte[] keyBytes = Base64Utils.decode(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            signature.initSign(privateK);
            byte[] data = oriData.getBytes(getDataCharEncoding());
            signature.update(data);
            return Base64Utils.encode(signature.sign());
        } catch (Exception e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]私钥签名错误", e);
        }
    }

    /**
     * <p>公钥验签</p>
     *
     * @param oriData   密文
     * @param publicKey 公钥
     * @param sign      签名字符串
     * @return true:验证成功，false:验证失败
     */
    public boolean verify(String oriData, String publicKey, String sign) {
        KeyFactory keyFactory = null;
        try {
            keyFactory = getKeyFactory();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]获取密钥工厂错误", e);
        }
        Signature signature = null;
        try {
            signature = getSignature(keyFactory);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]获取签名算法错误", e);
        }
        try {
            byte[] keyBytes = Base64Utils.decode(publicKey);
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            PublicKey publicK = keyFactory.generatePublic(keySpec);
            signature.initVerify(publicK);
            byte[] data = oriData.getBytes(getDataCharEncoding());
            signature.update(data);
            return signature.verify(Base64Utils.decode(sign));
        } catch (Exception e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]公钥验签错误", e);
        }
    }

    /**
     * <P>私钥解密</p>
     *
     * @param oriData    密文信息
     * @param privateKey 私钥
     * @return the String
     */
    public String decryptByPrivateKey(String oriData, String privateKey) {
        KeyFactory keyFactory = null;
        try {
            keyFactory = getKeyFactory();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]获取密钥工厂错误", e);
        }
        ByteArrayOutputStream out = null;
        try {
            byte[] keyBytes = Base64Utils.decode(privateKey);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(keyBytes);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateK);
            byte[] data = oriData.getBytes(getDataCharEncoding());
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;

            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_DECRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_DECRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_DECRYPT_BLOCK;
            }
            byte[] decryptedData = out.toByteArray();
            return new String(decryptedData, getDataCharEncoding());
        } catch (Exception e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]私钥解密错误", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                out = null;
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>公钥加密</p>
     *
     * @param oriData   明文信息
     * @param publicKey 公钥
     * @return the String
     */
    public String encryptByPublicKey(String oriData, String publicKey) {
        KeyFactory keyFactory = null;
        try {
            keyFactory = getKeyFactory();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]获取密钥工厂错误", e);
        }
        ByteArrayOutputStream out = null;
        try {
            byte[] keyBytes = Base64Utils.decode(publicKey);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(keyBytes);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
            byte[] data = oriData.getBytes(getDataCharEncoding());
            int inputLen = data.length;
            out = new ByteArrayOutputStream();
            int offSet = 0;
            byte[] cache;
            int i = 0;

            while (inputLen - offSet > 0) {
                if (inputLen - offSet > MAX_ENCRYPT_BLOCK) {
                    cache = cipher.doFinal(data, offSet, MAX_ENCRYPT_BLOCK);
                } else {
                    cache = cipher.doFinal(data, offSet, inputLen - offSet);
                }
                out.write(cache, 0, cache.length);
                i++;
                offSet = i * MAX_ENCRYPT_BLOCK;
            }
            byte[] encryptedData = out.toByteArray();
            return new String(encryptedData, getDataCharEncoding());
        } catch (Exception e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]公钥加密错误", e);
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException e) {
                out = null;
                e.printStackTrace();
            }
        }
    }

    /**
     * <p>获取私钥</p>
     *
     * @return the String RSAPrivateKey or DSAPrivateKey
     */
    public String getPrivateKey() {
        Key key = (Key) this.keyMap.get(PRIVATE_KEY);
        try {
            return Base64Utils.encode(key.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Base64加密私钥错误", e);
        }
    }

    /**
     * <p>获取公钥</p>
     *
     * @return the String RSAPublicKey or DSAPublicKey
     */
    public String getPublicKey() {
        Key key = (Key) this.keyMap.get(PUBLIC_KEY);
        try {
            return Base64Utils.encode(key.getEncoded());
        } catch (Exception e) {
            throw new RuntimeException("Base64加密公钥错误", e);
        }
    }

    /**
     * The KeyPairGenerator.
     * @return the KeyPairGenerator
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public abstract KeyPairGenerator getKeyPairGenerator() throws NoSuchAlgorithmException;

    /**
     * The KeyFactory.
     * @return the KeyFactory
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public abstract KeyFactory getKeyFactory() throws NoSuchAlgorithmException;

    /**
     * The Signature.
     * @param keyFactory the KeyFactory
     * @return the Signature
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     */
    public abstract Signature getSignature(KeyFactory keyFactory) throws NoSuchAlgorithmException;

    /**
     * Gets data char encoding.
     *
     * @return the data char encoding
     */
    public String getDataCharEncoding() {
        return dataCharEncoding;
    }

    /**
     * Se data char encoding.
     *
     * @param dataCharEncoding the data char encoding
     */
    public void seDataCharEncoding(String dataCharEncoding) {
        this.dataCharEncoding = dataCharEncoding;
    }

    /**
     * Gets seed.
     *
     * @return the seed
     */
    public String getSeed() {
        return seed;
    }

    /**
     * Sets seed.
     *
     * @param seed the seed
     */
    public void setSeed(String seed) {
        this.seed = seed;
    }
}
