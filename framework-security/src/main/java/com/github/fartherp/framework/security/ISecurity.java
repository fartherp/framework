/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security;

/**
 * 对称加密解密接口
 *
 * @author CK
 * @see IEncrypt 继承于此接口
 * @see IDecrypt 继承于此接口
 */
public interface ISecurity extends IEncrypt, IDecrypt {
    /**
     * 默认编码集UTF-8
     */
    public static final String UTF_CHAR_ENCODING = "UTF-8";
    /**
     * 编码集ISO-8859-1
     */
    public static final String ISO_CHAR_ENCODING = "ISO-8859-1";
    /**
     * 算法RSA
     */
    public static final String RSA_ALGORITHM = "RSA";
    /**
     * 算法DSA
     */
    public static final String DSA_ALGORITHM = "DSA";
    /**
     * 算法MD5withRSA
     */
    public static final String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 算法AES
     */
    public static final String AES_ALGORITHM = "AES";
    /**
     * 算法PBE
     * PBEWithMD5AndDES
     * PBEWithMD5AndTripleDES
     * PBEWithSHA1AndDESede
     * PBEWithSHA1AndRC2_40
     */
    public static final String PBE_ALGORITHM = "PBEWITHMD5andDES";
    /**
     * 算法3DES
     */
    public static final String THREE_DES_ALGORITHM = "DESede"; // 3des加解密算法工具类;
    /**
     * 算法DESede/ECB/Nopadding
     */
    public static final String THREE_DES_ECB_ALGORITHM = "DESede/ECB/Nopadding";
    /**
     * 算法DES-ECB
     */
    public static final String DES_ECB_ALGORITHM = "DES";// 默认的DES/ECB/PKCS5Padding
    /**
     * 算法DES-CBC
     */
    public static final String DES_CBC_ALGORITHM = "DES/CBC/PKCS5Padding";
    /**
     * 算法MD5
     */
    public static final String MD5_ALGORITHM = "MD5";
    /**
     * 算法SHA
     */
    public static final String SHA_ALGORITHM = "SHA";
    /**
     * 算法HMAC
     */
    public static final String HMAC_ALGORITHM = "HmacMD5";
}
