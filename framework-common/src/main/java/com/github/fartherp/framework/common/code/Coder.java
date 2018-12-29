/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.code;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.UnsupportedEncodingException;

/**
 * E码-->A码转换
 * Author: CK
 * Date: 2016/2/14
 */
public class Coder {
    public static final String CHARSET_GBK = "GBK";
    public static final String INT = "0123456789";

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

    /**
     * 转码（ISO8859-1 -> GBK） 若已是GBK编码则不再进行转码
     *
     * @param s
     * @return
     * @throws Exception
     */
    public static String transCode(String s) throws Exception {
        if (StringUtils.isBlank(s)) {
            return null;
        }
        try {
            byte[] b = s.getBytes("ISO-8859-1");
            if (s.indexOf("?") > -1) {
                String s_temp = StringUtils.replace(s, "?", "");
                byte[] b_temp = s_temp.getBytes("ISO-8859-1");
                for (int i = 0; i < b_temp.length; i++) {
                    if (b_temp[i] == 63) {
                        return s;
                    }
                }
            } else {
                for (int i = 0; i < b.length; i++) {
                    if (b[i] == 63) {
                        return s;
                    }
                }
            }
            return new String(b, "GBK");
        } catch (Exception e) {
            throw e;
        }
    }

    /**
     * ISO convert GBK
     *
     * @param s 字符串
     * @return 字符串
     */
    public static String encodeStrFromISOToGBK(Object s) {
        if (s == null) {
            return "";
        }
        try {
            if (!"GBK".equalsIgnoreCase("GBK")) {
                s = new String(s.toString().trim().getBytes("8859_1"), "GBK");
            }
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return s.toString();
    }

    /**
     * GBK convert ISO
     *
     * @param s
     * @return
     */
    public static String encodeStrFromGBKToISO(Object s) {
        if (s == null) {
            return "";
        }
        try {
            if (CHARSET_GBK.equalsIgnoreCase("GBK")) {
                s = new String(s.toString().trim().getBytes("GBK"), "8859_1");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return s.toString();
    }

    public static Object transCharsetISO(Class targetType, Object obj) {
        if (!CHARSET_GBK.equalsIgnoreCase("GBK") || !targetType.equals(String.class)) {
            return obj;
        } else {
            return encodeStrFromISOToGBK(obj);
        }
    }

    public static Object transCharsetGBK(Class targetType, Object obj) {
        if (CHARSET_GBK.equalsIgnoreCase("GBK") || !targetType.equals(String.class)) {
            return obj;
        } else {
            return encodeStrFromISOToGBK(obj);
        }
    }

    public static String transCharsetISO(Object obj) {
        if (!CHARSET_GBK.equalsIgnoreCase("GBK")) {
            return obj.toString();
        } else {
            return encodeStrFromGBKToISO(obj);
        }
    }

    /**
     * GBK
     *
     * @param obj 对象
     * @return 字符串
     */
    public static String transCharsetGBK(Object obj) {
        if (CHARSET_GBK.equalsIgnoreCase("GBK")) {
            return obj.toString();
        } else {
            return transCharsetGBK(String.class, obj).toString();
        }
    }
}
