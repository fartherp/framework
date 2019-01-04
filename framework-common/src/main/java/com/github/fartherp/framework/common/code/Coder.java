/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.code;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * E码-->A码转换
 * Author: CK
 * Date: 2016/2/14
 */
public class Coder {
    /**
     * 字节的ASCII->EBCDIC转换函数
     */
    public static int ASCIIToEBCDIC(int ascii) {
        return ASCII.AToE[ascii & 0xff] & 0xff;
    }

    /**
     * 字节的EBCDIC->ASCII转换函数
     */
    public static int EBCDICToASCII(int ebcdic) {
        return EBCDIC.EToA[ebcdic & 0xff] & 0xff;
    }

    /**
     * 字节流的ASCII->EBCDIC转换函数
     */
    public static byte[] ASCIIToEBCDIC(byte[] ascii) {
        byte[] tobytes = new byte[ascii.length];
        for (int i = 0; i < ascii.length; i++) {
            tobytes[i] = (byte) ASCIIToEBCDIC(ascii[i]);
        }
        return tobytes;
    }

    /**
     * 字节流的EBCDIC->ASCII转换函数
     */
    public static byte[] EBCDICToASCII(byte[] ebcdic) {
        byte[] tobytes = new byte[ebcdic.length];
        for (int i = 0; i < ebcdic.length; i++) {
            tobytes[i] = (byte) EBCDICToASCII(ebcdic[i]);
        }
        return tobytes;
    }

    /**
     * 字符串的ASCII->EBCDIC转换函数
     */
    public static String ASCIIToEBCDIC(String ascii) {
        return new String(ASCIIToEBCDIC(ascii.getBytes()));
    }

    /**
     * 字符串的EBCDIC->ASCII转换函数
     */
    public static String EBCDICToASCII(String ebcdic) {
        return new String(EBCDICToASCII(ebcdic.getBytes()));
    }

    /**
     * 文件的ASCII->EBCDIC转换函数
     */
    public static void ASCIIToEBCDIC(String fromFile, String toFile) {
        try {
            FileInputStream in = new FileInputStream(new File(fromFile));
            FileOutputStream out = new FileOutputStream(new File(toFile));
            int tempint, i = 0;
            byte[] tempbytes = new byte[in.available()];
            while ((tempint = in.read()) != -1) {
                tempbytes[i++] = (byte) tempint;
            }
            out.write(ASCIIToEBCDIC(tempbytes));
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 文件的EBCDIC->ASCII转换函数
     */
    public static void EBCDICToASCII(String fromFile, String toFile) {
        try {
            FileInputStream in = new FileInputStream(new File(fromFile));
            FileOutputStream out = new FileOutputStream(new File(toFile));
            int temp, i = 0;
            byte[] bytes = new byte[in.available()];
            while ((temp = in.read()) != -1) {
                bytes[i++] = (byte) temp;
            }
            out.write(EBCDICToASCII(bytes));
            in.close();
            out.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
