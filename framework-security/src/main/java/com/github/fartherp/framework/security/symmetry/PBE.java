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
package com.github.fartherp.framework.security.symmetry;

import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.util.Random;

import static com.github.fartherp.framework.security.ISecurity.PBE_ALGORITHM;

/**
 * <pre>
 * Password-based encryption（基于密码加密）.
 * 其特点在于口令由用户自己掌管，不借助任何物理媒体；采用随机数（这里我们叫做盐）杂凑多重加密等方法保证数据的安全性
 * </pre>
 * @author CK
 * @date 2015/4/13
 */
public class PBE {

    /**
     * 加密
     * @param data 加密原数据
     * @param key 密钥
     * @return 加密数据
     */
    public static byte[] encrypt(byte[] data, byte[] key, byte[] salt) {
        AlgorithmParameterSpec algorithmParameterSpec = getAlgorithmParameterSpec(salt);
        Key keySpec = generateRandomKey(key);
        return Symmetry.encrypt(PBE_ALGORITHM, keySpec, data, algorithmParameterSpec);
    }

    /**
     * 解密
     * @param data 加密数据
     * @param key 密钥
     * @return 加密原数据
     */
    public static byte[] decrypt(byte[] data, byte[] key, byte[] salt) {
        AlgorithmParameterSpec algorithmParameterSpec = getAlgorithmParameterSpec(salt);
        Key keySpec = generateRandomKey(key);
        return Symmetry.decrypt(PBE_ALGORITHM, keySpec, data, algorithmParameterSpec);
    }

    /**
     * 生成密钥
     * @param key 密钥
     * @return key
     */
    public static Key generateRandomKey(byte[] key) {
        try {
            String keyString = new String(key);
            PBEKeySpec keySpec = new PBEKeySpec(keyString.toCharArray());
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(PBE_ALGORITHM);
            return keyFactory.generateSecret(keySpec);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + PBE_ALGORITHM + "]无此算法", e);
        } catch (InvalidKeySpecException e) {
            throw new RuntimeException("[" + PBE_ALGORITHM + "]无效密钥错误", e);
        }
    }

    /**
     * 生成向量
     * @param salt 向量
     * @return key
     */
    public static AlgorithmParameterSpec getAlgorithmParameterSpec(byte[] salt) {
        return new PBEParameterSpec(salt, 100);
    }

    /**
     * 生成向量
     * @return 向量
     */
    public static byte[] initSalt() {
        byte[] salt = new byte[8];
        Random random = new Random();
        random.nextBytes(salt);
        return salt;
    }
}
