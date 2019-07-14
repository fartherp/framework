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

import javax.crypto.spec.IvParameterSpec;
import java.security.Key;
import java.security.spec.AlgorithmParameterSpec;

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
 *          DATA: F4F3E7B3566F6622098750B491EA8D5C61 补位: 07070707070707
 *          ALL_DATA: F4F3E7B3566F6622098750B491EA8D5C6107070707070707
 *
 *          DEST: 5416B1A5537436105B6E2CAC0F87986F2968AC2DBFB464D0
 * </pre>
 * @author CK
 */
public class DESCBC {

    /**
     * 加密
     * @param data 加密原数据
     * @param key 密钥
     * @param iv 初始向量
     * @return 加密数据
     */
    public static byte[] encrypt(byte[] data, byte[] key, byte[] iv) {
        validation(data, key, iv);
        AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(iv);
        Key secretKeySpec = Symmetry.generateRandomKey(key, DES_ECB_ALGORITHM);
        return Symmetry.encrypt(DES_CBC_ALGORITHM, secretKeySpec, data, algorithmParameterSpec);
    }

    /**
     * 解密
     * @param data 加密数据
     * @param key 密钥
     * @param iv 初始向量
     * @return 加密原数据
     */
    public static byte[] decrypt(byte[] data, byte[] key, byte[] iv) {
        validation(data, key, iv);
        AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec(iv);
        Key secretKeySpec = Symmetry.generateRandomKey(key, DES_ECB_ALGORITHM);
        return Symmetry.decrypt(DES_CBC_ALGORITHM, secretKeySpec, data, algorithmParameterSpec);
    }

    /**
     * 验证DESCBC数据有效性及密钥有效性
     * @param data 加密数据
     * @param key 密钥(DESCBC密钥必须是8位)
     * @param iv 初始向量(必须是8位)
     */
    public static void validation(byte[] data, byte[] key, byte[] iv) {
        if (key.length != 8) {
            throw new RuntimeException("Invalid DESCBC key length (must be 8 bytes)");
        }
        if (iv == null || iv.length != 8) {
            throw new RuntimeException("Invalid DESCBC iv length (must be 8 bytes)");
        }
    }
}
