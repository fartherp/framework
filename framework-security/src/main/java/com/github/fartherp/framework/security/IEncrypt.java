/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security;

/**
 * 对称加密接口
 *
 * @author CK
 */
public interface IEncrypt {
    /**
     * 加密
     *
     * @param data 加密前内容[数组]
     * @return 加密后内容[数组]
     * @throws Exception the Exception
     */
    public byte[] encrypt(byte[] data);
}
