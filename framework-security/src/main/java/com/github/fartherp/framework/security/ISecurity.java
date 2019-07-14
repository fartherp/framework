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
package com.github.fartherp.framework.security;

/**
 * 对称加密解密接口
 *
 * @author CK
 */
public interface ISecurity {
    /**
     * 默认编码集UTF-8
     */
	String UTF_CHAR_ENCODING = "UTF-8";
    /**
     * 编码集ISO-8859-1
     */
	String ISO_CHAR_ENCODING = "ISO-8859-1";
    /**
     * 算法RSA
     */
	String RSA_ALGORITHM = "RSA";
    /**
     * 算法DSA
     */
	String DSA_ALGORITHM = "DSA";
    /**
     * 算法MD5withRSA
     */
	String SIGNATURE_ALGORITHM = "MD5withRSA";
    /**
     * 算法AES
     */
	String AES_ALGORITHM = "AES";
    /**
     * 算法PBE
     * PBEWithMD5AndDES
     * PBEWithMD5AndTripleDES
     * PBEWithSHA1AndDESede
     * PBEWithSHA1AndRC2_40
     */
	String PBE_ALGORITHM = "PBEWITHMD5andDES";
    /**
     * 算法3DES
     */
	String THREE_DES_ALGORITHM = "DESede";
    /**
     * 算法DESede/ECB/Nopadding
     */
	String THREE_DES_ECB_ALGORITHM = "DESede/ECB/Nopadding";
    /**
     * 算法DES-ECB，默认的DES/ECB/PKCS5Padding
     */
	String DES_ECB_ALGORITHM = "DES";
    /**
     * 算法DES-CBC
     */
	String DES_CBC_ALGORITHM = "DES/CBC/PKCS5Padding";
    /**
     * 算法MD5
     */
	String MD5_ALGORITHM = "MD5";
    /**
     * 算法SHA
     */
	String SHA_ALGORITHM = "SHA";
    /**
     * 算法HMAC
     */
	String HMAC_ALGORITHM = "HmacMD5";
    /**
     * 初始向量（默认）0000000000000000(16进制)
     */
	byte[] DEFAULT_IV = new byte[8];
    /**
     * 算法RSA/ECB/Nopadding
     */
	String RSA_ECB_ALGORITHM = "RSA/ECB/Nopadding";
}
