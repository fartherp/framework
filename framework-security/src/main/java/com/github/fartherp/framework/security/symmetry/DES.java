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

import static com.github.fartherp.framework.security.ISecurity.DES_ECB_ALGORITHM;

/**
 * DES-ECB加密解密实现
 * <p>DES加密算法对密钥有严格要求,密钥必须是8字节,数据没有硬性要求</p>
 * <pre>
 *     CBC加密：
 *          密钥为8字节长，DK。DATA 按8字节截取 D1, D2, D3... Dn，Dn长度对8取余，补位。
 *          如：Dn长度1，补位8-1，0x070x070x070x070x070x070x07
 *              Dn长度3，补位8-3，0x050x050x050x050x05
 *              余数0，补位8-0，0x080x080x080x080x080x080x080x08
 *     加密方法：
 *       DES(D1, DK, TMP2);
 *       DES(TMP2, DK, TMP3);
 *       ...
 *       DES(TMPN, DK, DEST);
 *      Example:
 *          密钥: 1111111111111111
 *          DATA: 2222222222222222 补位: 0808080808080808 ALL_DATA: 22222222222222220808080808080808
 *          DEST: 950973182317F80BB95374BA8DDFF8C2
 * </pre>
 * 密钥生成
 * <pre>
 * SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(ConfigureEncryptAndDecrypt.DES_ECB_ALGORITHM);
 * DESKeySpec deSedeKeySpec = new DESKeySpec(key);
 * secretKeyFactory.generateSecret(deSedeKeySpec);
 * </pre>
 * @author CK.
 * @date 2015/4/11.
 */
public class DES {

    /**
     * 加密
     * @param data 加密原数据
     * @param key 密钥
     * @return 加密数据
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        validation(data, key);
        Key secretKeySpec = Symmetry.generateRandomKey(key, DES_ECB_ALGORITHM);
        return Symmetry.encrypt(DES_ECB_ALGORITHM, secretKeySpec, data);
    }

    /**
     * 解密
     * @param data 加密数据
     * @param key 密钥
     * @return 加密原数据
     */
    public static byte[] decrypt(byte[] data, byte[] key) {
        validation(data, key);
        Key secretKeySpec = Symmetry.generateRandomKey(key, DES_ECB_ALGORITHM);
        return Symmetry.decrypt(DES_ECB_ALGORITHM, secretKeySpec, data);
    }

    /**
     * 验证DES数据有效性及密钥有效性
     * @param data 加密数据
     * @param key 密钥(DES密钥必须是8位)
     */
    public static void validation(byte[] data, byte[] key) {
        if (key.length != 8) {
            throw new RuntimeException("Invalid DES key length (must be 8 bytes)");
        }
    }
}
