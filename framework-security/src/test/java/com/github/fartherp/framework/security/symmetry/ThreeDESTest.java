/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import com.github.fartherp.framework.common.util.ISOUtil;
import com.github.fartherp.framework.security.ISecurity;
import org.testng.annotations.Test;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2015/4/13
 */
public class ThreeDESTest {
    @Test
    public void main() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 24; i++) {
            sb.append("a");
        }
        String key = sb.toString();
        System.out.println(key.length());
        ISecurity desSecurity = new ThreeDES(key.getBytes());
        byte[] encryption = desSecurity.encrypt("aaaaaaaa".getBytes());
        System.out.println(new String(encryption));
        System.out.println(new String(desSecurity.decrypt(encryption)));
    }

    /**
     * 3DES(双倍长)单侧
     */
    @Test
    public void encrypt() {

        // 加密数据
        byte[] data = ISOUtil.hex2byte("2016080901161445");
        // 加密密钥
        byte[] clearKeyBytes = ISOUtil.hex2byte("000000000000A43428481AE64383964A");
        // 3DES密钥长度必须是24字节,双倍长需要补充8字节
        byte[] key = ISOUtil.concat(clearKeyBytes, 0, getBytesLength(128), clearKeyBytes, 0, getBytesLength(64));
        System.out.println(ISOUtil.hexString(key));
        ISecurity desSecurity = new ThreeDES(key);
        byte [] encrypt = desSecurity.encrypt(data);
        System.out.println(ISOUtil.hexString(encrypt));
        byte [] decrypt = desSecurity.decrypt(data);
        System.out.println(ISOUtil.hexString(decrypt));
    }

    int getBytesLength(int keyLength) throws RuntimeException {
        int bytesLength = 0;
        switch (keyLength) {
            case 64:
                bytesLength = 8;
                break;
            case 128:
                bytesLength = 16;
                break;
            case 192:
                bytesLength = 24;
                break;
            default:
                throw new RuntimeException("Unsupported key length: " + keyLength + " bits");
        }
        return bytesLength;
    }
}
