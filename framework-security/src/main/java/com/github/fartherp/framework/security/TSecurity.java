/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security;

import java.util.Map;

/**
 * 非对称私钥、公钥实现接口.
 * Author: CK.
 * Date: 2015/4/11.
 */
public interface TSecurity {

    /**
     * 初始化公钥私钥
     * @return 公钥私钥
     * @throws Exception Exception
     */
    Map<String, Object> initKey();

    /**
     * 私钥签名
     * @param data 需要签名字符串
     * @param privateKey 私钥
     * @return 签名后字符串
     * @throws Exception the Exception
     */
    String sign(String data, String privateKey);

    /**
     * 验签
     * @param data 需要验证字符串
     * @param publicKey 公钥
     * @param sign 签名字符串
     * @return true:验签成功，false:验签失败
     * @throws Exception the Exception
     */
    boolean verify(String data, String publicKey, String sign);

    /**
     * 私钥解密
     * @param oriData 加密后数据
     * @param privateKey 私钥
     * @return 解密后字符串
     * @throws Exception the Exception
     */
    String decryptByPrivateKey(String oriData, String privateKey);

    /**
     * 公钥加密
     * @param oriData 加密前字符串
     * @param publicKey 公钥
     * @return 加密后字符串
     * @throws Exception the Exception
     */
    String encryptByPublicKey(String oriData, String publicKey);

    /**
     * 获取私钥
     * @return 私钥
     * @throws Exception the Exception
     */
    String getPrivateKey();

    /**
     * 获取公钥
     * @return 公钥
     * @throws Exception the Exception
     */
    String getPublicKey();
}
