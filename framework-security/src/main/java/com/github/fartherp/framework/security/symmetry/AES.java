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

import java.security.Key;

import static com.github.fartherp.framework.security.ISecurity.AES_ALGORITHM;

/**
 * AES
 * <p>AES加密算法对密钥有严格要求,密钥必须是16字节,数据没有硬性要求</p>
 * @author CK
 */
public class AES {

    /**
     * 加密
     * @param data 加密原数据
     * @param key 密钥
     * @return 加密数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        validation(data, key);
        Key secretKeySpec = Symmetry.generateRandomKey(key, AES_ALGORITHM);
        return Symmetry.encrypt(AES_ALGORITHM, secretKeySpec, data);
    }

    /**
     * 解密
     * @param data 加密数据
     * @param key 密钥
     * @return 加密原数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        validation(data, key);
        Key secretKeySpec = Symmetry.generateRandomKey(key, AES_ALGORITHM);
        return Symmetry.decrypt(AES_ALGORITHM, secretKeySpec, data);
    }

    /**
     * 验证AES数据有效性及密钥有效性
     * @param data 加密数据
     * @param key 密钥(AES密钥必须是16位)
     */
    public static void validation(byte[] data, byte[] key) {
        if (key.length != 16) {
            throw new RuntimeException("Invalid AES key length (must be 16 bytes)");
        }
    }
}
