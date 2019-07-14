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
package com.github.fartherp.framework.security.single;

import com.github.fartherp.framework.security.ISecurity;

import javax.crypto.KeyGenerator;
import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

/**
 * <pre>
 * HMAC(Hash Message Authentication Code，散列消息鉴别码).基于密钥的Hash算法的认证协议.
 * 消息鉴别码实现鉴别的原理是，用公开函数和密钥产生一个固定长度的值作为认证标识，用这个标识鉴别消息的完整性。
 * 使用一个密钥生成一个固定大小的小数据块，即MAC，并将其加入到消息中，然后传输。
 * 接收方利用与发送方共享的密钥进行鉴别认证等
 * </pre>
 * @author CK
 * @date 2015/4/13
 */
public class HMAC {

    /**
     * 加密
     * @param data 加密数据
     * @param key BASE64加密后的密钥
     * @return 加密数组
     */
    public static byte[] encrypt(byte[] data, byte[] key) {
        try {
            SecretKey secretKey = new SecretKeySpec(key, ISecurity.HMAC_ALGORITHM);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("无此算法错误", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("无效密钥错误", e);
        }
    }

    /**
     * 初始化HMAC密钥
     *
     * @return BASE64加密后的密钥
     */
    public static byte[] initMacKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ISecurity.HMAC_ALGORITHM);
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("获取自增密钥错误", e);
        }
    }
}
