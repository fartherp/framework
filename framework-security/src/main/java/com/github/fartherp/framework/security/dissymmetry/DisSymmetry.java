/**
 *    Copyright (c) 2014-2019 CK.
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */
package com.github.fartherp.framework.security.dissymmetry;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2017/11/30
 */
public class DisSymmetry {
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

    private static final String SEED = "0f22507a10bbddd07d8a3082122966e3";

    /**
     * <p>创建公钥&私钥</p>
     *
     * @return the Map
     */
    public static DisSymmetryKey initKey(String algorithm) {
        DisSymmetryKey disSymmetryKey = new DisSymmetryKey();
        KeyPairGenerator keyPairGen = null;
        try {
            keyPairGen = getKeyPairGenerator(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + algorithm + "]获取自增密钥错误", e);
        }
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.setSeed(SEED.getBytes());
        keyPairGen.initialize(KEY_SIZE, secureRandom);
        KeyPair keyPair = keyPairGen.generateKeyPair();
        disSymmetryKey.setPublicKey(keyPair.getPublic().getEncoded());
        disSymmetryKey.setPrivateKey(keyPair.getPrivate().getEncoded());
        return disSymmetryKey;
    }

    public static KeyPairGenerator getKeyPairGenerator(String algorithm) throws NoSuchAlgorithmException {
        return KeyPairGenerator.getInstance(algorithm);
    }

    public static KeyFactory getKeyFactory(String algorithm) throws NoSuchAlgorithmException {
        return KeyFactory.getInstance(algorithm);
    }

    /**
     * <p>公钥加密</p>
     *
     * @param algorithm 加密算法
     * @param data   明文信息
     * @param publicKey 公钥
     * @return byte[]
     */
    public static byte[] encryptByPublicKey(String algorithm, byte[] data, byte[] publicKey) {
        ByteArrayOutputStream out = null;
        try {
            KeyFactory keyFactory = getKeyFactory(algorithm);
            X509EncodedKeySpec x509KeySpec = new X509EncodedKeySpec(publicKey);
            Key publicK = keyFactory.generatePublic(x509KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.ENCRYPT_MODE, publicK);
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
            return encryptedData;
        }  catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + algorithm + "]获取密钥工厂错误", e);
        } catch (Exception e) {
            throw new RuntimeException("[" + algorithm + "]公钥加密错误", e);
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
     * <P>私钥解密</p>
     *
     * @param algorithm 加密算法
     * @param data    密文信息
     * @param privateKey 私钥
     * @return byte[]
     */
    public static byte[] decryptByPrivateKey(String algorithm, byte[] data, byte[] privateKey) {
        ByteArrayOutputStream out = null;
        try {
            KeyFactory keyFactory = getKeyFactory(algorithm);
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
            Key privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(Cipher.DECRYPT_MODE, privateK);
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
            return decryptedData;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + algorithm + "]获取密钥工厂错误", e);
        } catch (Exception e) {
            throw new RuntimeException("[" + algorithm + "]私钥解密错误", e);
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
}
