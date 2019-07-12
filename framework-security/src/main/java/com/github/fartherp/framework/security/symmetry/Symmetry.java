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

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/11/30
 */
public class Symmetry {

    /**
     * 生成密钥
     * @param key 密钥
     * @param algorithm 算法
     * @return key
     */
    public static Key generateRandomKey(byte[] key, String algorithm) {
        return new SecretKeySpec(key, algorithm);
    }

    /**
     * 加密
     * @param algorithm 算法
     * @param key 加密密钥
     * @param data 需要加密数据[数组]
     * @return 加密数据[数组]
     */
    public static byte[] encrypt(String algorithm, Key key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            // 传入加密标识与加密密钥
            cipher.init(Cipher.ENCRYPT_MODE, key);
            // 加密数据
            return cipher.doFinal(data);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("[" + algorithm + "]无此填充", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + algorithm + "]无此算法", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("[" + algorithm + "]无效密钥错误", e);
        }catch (IllegalBlockSizeException e) {
            throw new RuntimeException("[" + algorithm + "] (data length error)", e);
        } catch (BadPaddingException e) {
            throw new RuntimeException("[" + algorithm + "]填充错误", e);
        }
    }

    /**
     * 加密
     * @param algorithm 算法
     * @param key 加密密钥
     * @param data 需要加密数据[数组]
     * @return 加密数据[数组]
     */
    public static byte[] encrypt(String algorithm, Key key, byte[] data, AlgorithmParameterSpec algorithmParameterSpec) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            // 传入加密标识与加密密钥
            cipher.init(Cipher.ENCRYPT_MODE, key, algorithmParameterSpec);
            // 加密数据
            return cipher.doFinal(data);
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("[" + algorithm + "]无此填充", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + algorithm + "]无此算法", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("[" + algorithm + "]无效密钥错误", e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("[" + algorithm + "]无效算法参数错误", e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("[" + algorithm + "] (data length error)", e);
        } catch (BadPaddingException e) {
            throw new RuntimeException("[" + algorithm + "]填充错误", e);
        }
    }

    /**
     * 解密
     * @param algorithm 算法
     * @param key 解密密钥
     * @param data 需要解密数据[数组]
     * @return 解密数据[数组]
     */
    public static byte[] decrypt(String algorithm, Key key, byte[] data) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            // 传入解密标识与解密密钥
            cipher.init(Cipher.DECRYPT_MODE, key);
            return cipher.doFinal(data);// 解密数据
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("[" + algorithm + "]无此填充", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + algorithm + "]无此算法", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("[" + algorithm + "]无效密钥错误", e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("[" + algorithm + "] (data length error)", e);
        } catch (BadPaddingException e) {
            throw new RuntimeException("[" + algorithm + "]填充错误", e);
        }
    }

    /**
     * 解密
     * @param data 需要解密数据[数组]
     * @param key 解密密钥
     * @return 解密数据[数组]
     */
    public static byte[] decrypt(String algorithm, Key key, byte[] data, AlgorithmParameterSpec algorithmParameterSpec) {
        try {
            Cipher cipher = Cipher.getInstance(algorithm);
            // 传入解密标识与解密密钥
            cipher.init(Cipher.DECRYPT_MODE, key, algorithmParameterSpec);
            return cipher.doFinal(data);// 解密数据
        } catch (NoSuchPaddingException e) {
            throw new RuntimeException("[" + algorithm + "]无此填充", e);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + algorithm + "]无此算法", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("[" + algorithm + "]无效密钥错误", e);
        } catch (InvalidAlgorithmParameterException e) {
            throw new RuntimeException("[" + algorithm + "]无效算法参数错误", e);
        } catch (IllegalBlockSizeException e) {
            throw new RuntimeException("[" + algorithm + "] (data length error)", e);
        } catch (BadPaddingException e) {
            throw new RuntimeException("[" + algorithm + "]填充错误", e);
        }
    }
}
