/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.security.symmetry;

import com.github.fartherp.framework.common.util.ISOUtil;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

/**
 * Created by IntelliJ IDEA .
 * Auth: CK
 * Date: 2016/6/4
 */
public class Test {

    public static void main(String[] args) throws Exception {
        String a = doubleDesString("000000000000A43428481AE64383964A", "2016060411245960");
        System.out.println(a);
    }

    /**
     * @param encryptString 需要加密的明文
     * @param encryptKey    秘钥
     * @return 加密后的密文
     * @throws Exception
     */
    public static byte[] encryptDES(byte[] encryptString, byte[] encryptKey)
            throws Exception {
        // 实例化IvParameterSpec对象，使用指定的初始化向量
        // 实例化SecretKeySpec类，根据字节数组来构造SecretKey
        SecretKeySpec key = new SecretKeySpec(encryptKey, "DES/ECB/NoPadding");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        // 用秘钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key);
        // 执行加密操作
        byte[] encryptedData = cipher.doFinal(encryptString);

        return encryptedData;//Base64.encodeToString(encryptedData, Base64.DEFAULT);
    }

    public static byte[] encryptDESCBC(byte[] encryptString, byte[] encryptKey)
            throws Exception {
        // 实例化IvParameterSpec对象，使用指定的初始化向量
        // 实例化SecretKeySpec类，根据字节数组来构造SecretKey
        SecretKeySpec key = new SecretKeySpec(encryptKey, "DES/CBC/PKCS5Padding");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        AlgorithmParameterSpec algorithmParameterSpec = new IvParameterSpec("00000000".getBytes());
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES/CBC/PKCS5Padding");
//        DESKeySpec keySpec = new DESKeySpec(encryptKey);
//        Key key = keyFactory.generateSecret(keySpec);
        // 用秘钥初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, key, algorithmParameterSpec);
        // 执行加密操作
        byte[] encryptedData = cipher.doFinal(encryptString);

        return encryptedData;//Base64.encodeToString(encryptedData, Base64.DEFAULT);
    }

    /**
     * *
     *
     * @param decrypString 密文
     * @param decryptKey   解密密钥
     * @return
     * @throws Exception
     */
    public static byte[] decryptDES(byte[] decrypString, byte[] decryptKey)
            throws Exception {

        byte[] byteMi = decrypString;//Base64.decode(decrypString, Base64.DEFAULT);
        // 实例化IvParameterSpec对象，使用指定的初始化向量
        // 实例化SecretKeySpec类，根据字节数组来构造SecretKey
//        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(ConfigureEncryptAndDecrypt.DES_ECB_ALGORITHM);// 获得密钥工厂
//        DESKeySpec keySpec = new DESKeySpec(decryptKey);
//        Key key = keyFactory.generateSecret(keySpec);
        SecretKeySpec key = new SecretKeySpec(decryptKey, "DES/ECB/NoPadding");
        // 创建密码器
        Cipher cipher = Cipher.getInstance("DES/ECB/NoPadding");
        // 用秘钥初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, key);
        // 执行解密操作
        byte[] decryptedData = cipher.doFinal(byteMi);

        return decryptedData;//new String(decryptedData);
    }
    public static String doubleDesString(String keyString, String src) throws Exception {
        //用左边8字节的密钥对输入数据进行CBC加密，取密文的最后8字节，记为O1
        byte[] temp1 = encryptDESCBC(src.getBytes(), hex2byte(keyString.substring(0, 16)));
        //用右边8字节的密钥对O1进行ECB解密得到O2
        byte[] trimmedArray = new byte[8];
        System.arraycopy(temp1, 16, trimmedArray, 0, 8);
        byte[] temp2 = decryptDES(trimmedArray, hex2byte(keyString.substring(16, 32)));
        //再次使用密钥的前16字节，对第二次的计算结果TMP2，进行加密，得到加密的结果DEST。DEST就为最终的结果
        byte[] dest = encryptDES(temp2, hex2byte(keyString.substring(0, 16)));
        return ISOUtil.hexString(dest);
    }

    public static byte[] hex2byte (String s) {
        if (s.length() % 2 == 0) {
            return hex2byte (s.getBytes(), 0, s.length() >> 1);
        } else {
            // Padding left zero to make it even size #Bug raised by tommy
            return hex2byte("0"+s);
        }
    }

    public static byte[] hex2byte (byte[] b, int offset, int len) {
        byte[] d = new byte[len];
        for (int i=0; i<len*2; i++) {
            int shift = i%2 == 1 ? 0 : 4;
            d[i>>1] |= Character.digit((char) b[offset+i], 16) << shift;
        }
        return d;
    }
}
