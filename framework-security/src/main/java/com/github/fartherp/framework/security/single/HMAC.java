/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.single;

import com.github.fartherp.framework.common.util.ISOUtil;
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
 * Author: CK
 * Date: 2015/4/13
 */
public class HMAC {

    public HMAC() {
        initMacKey();
    }

    private String key;

    public byte[] encrypt(byte [] data) {
        try {
            SecretKey secretKey = new SecretKeySpec(ISOUtil.hex2byte(this.key), ISecurity.HMAC_ALGORITHM);
            Mac mac = Mac.getInstance(secretKey.getAlgorithm());
            mac.init(secretKey);
            return mac.doFinal(data);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]无此算法错误", e);
        } catch (InvalidKeyException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]无效密钥错误", e);
        }
    }

    /**
     * 初始化HMAC密钥
     *
     * @return HMAC密钥
     */
    public String initMacKey() {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(ISecurity.HMAC_ALGORITHM);
            SecretKey secretKey = keyGenerator.generateKey();
            this.key = ISOUtil.hexString(secretKey.getEncoded());
            return this.key;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("[" + this.getClass().getName() + "]获取自增密钥错误", e);
        }
    }

    public String getKey() {
        return key;
    }
}
