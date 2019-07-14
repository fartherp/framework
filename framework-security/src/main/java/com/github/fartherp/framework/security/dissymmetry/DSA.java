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

import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

import static com.github.fartherp.framework.security.ISecurity.DSA_ALGORITHM;

/**
 * DSA.
 * Digital Signature Algorithm，数字签名
 * @author CK.
 * @date 2015/4/11.
 */
public class DSA {

    /**
     * <p>创建公钥&私钥</p>
     *
     * @return the key
     */
    public static DisSymmetryKey initKey() {
        return DisSymmetry.initKey(DSA_ALGORITHM);
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
            KeyFactory keyFactory = DisSymmetry.getKeyFactory(DSA_ALGORITHM);
            Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
            PKCS8EncodedKeySpec pkcs8KeySpec = new PKCS8EncodedKeySpec(privateKey);
            PrivateKey privateK = keyFactory.generatePrivate(pkcs8KeySpec);
            signature.initSign(privateK);
            signature.update(data);
            return signature.sign();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + DSA_ALGORITHM + "]获取签名算法错误", e);
        } catch (Exception e) {
            throw new RuntimeException("[" + DSA_ALGORITHM + "]私钥签名错误", e);
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
            KeyFactory keyFactory = DisSymmetry.getKeyFactory(DSA_ALGORITHM);
            Signature signature = Signature.getInstance(keyFactory.getAlgorithm());
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey);
            PublicKey publicK = keyFactory.generatePublic(keySpec);
            signature.initVerify(publicK);
            signature.update(data);
            return signature.verify(sign);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + DSA_ALGORITHM + "]获取签名算法错误", e);
        } catch (Exception e) {
            throw new RuntimeException("[" + DSA_ALGORITHM + "]公钥验签错误", e);
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
        return DisSymmetry.encryptByPublicKey(DSA_ALGORITHM, data, publicKey);
    }

    /**
     * <P>私钥解密</p>
     *
     * @param data    密文信息
     * @param privateKey 私钥
     * @return byte[]
     */
    public static byte[] decryptByPrivateKey(byte[] data, byte[] privateKey) {
        return DisSymmetry.decryptByPrivateKey(DSA_ALGORITHM, data, privateKey);
    }
}
