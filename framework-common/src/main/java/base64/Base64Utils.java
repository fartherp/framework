/*
 * Copyright (C) 2015 CK, Inc. All Rights Reserved.
 */

package base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * <p>BASE64</p>
 * <p>javabase64-1.3.1.jar</p>
 */
public class Base64Utils {
    /**
     * 文件读取缓存大小
     */
    private static final int CACHE_SIZE = 1024;

    /**
     * <p>字符串转换成二进制数据</p>
     *
     * @param base64
     * @return
     * @throws Exception
     */
    public static byte[] decode(String base64) throws Exception {
        return Base64.decode(base64.getBytes());
    }

    /**
     * <p>二进制数据转换成字符串</p>
     *
     * @param bytes
     * @return
     * @throws Exception
     */
    public static String encode(byte[] bytes) throws Exception {
        return new String(Base64.encode(bytes));
    }

    /**
     * <p>文件转换成字符串</p>
     * <p>big file can RAM overflow</p>
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static String encodeFile(String filePath) throws Exception {
        byte[] bytes = fileToByte(filePath);
        return encode(bytes);
    }

    /**
     * <p>字符串转换成文件</p>
     *
     * @param filePath
     * @param base64
     * @throws Exception
     */
    public static void decodeToFile(String filePath, String base64) throws Exception {
        byte[] bytes = decode(base64);
        byteArrayToFile(bytes, filePath);
    }

    /**
     * <p>文件转换二进制数据</p>
     *
     * @param filePath
     * @return
     * @throws Exception
     */
    public static byte[] fileToByte(String filePath) throws Exception {
        byte[] data = new byte[0];
        File file = new File(filePath);
        if (file.exists()) {
            try {
                FileInputStream in = new FileInputStream(file);
                ByteArrayOutputStream out = new ByteArrayOutputStream(2048);
                byte[] cache = new byte[CACHE_SIZE];
                int nRead = 0;
                while ((nRead = in.read(cache)) != -1) {
                    out.write(cache, 0, nRead);
                    out.flush();
                }
                out.close();
                in.close();
                data = out.toByteArray();
            } catch (Exception e) {
                throw new Exception("文件转换二进制数据", e);
            }
        }
        return data;
    }

    /**
     * <p>二进制数据写文件</p>
     *
     * @param bytes
     * @param filePath
     */
    public static void byteArrayToFile(byte[] bytes, String filePath) throws Exception {
        InputStream in = new ByteArrayInputStream(bytes);
        File destFile = new File(filePath);
        if (!destFile.getParentFile().exists()) {
            destFile.getParentFile().mkdirs();
        }
        try {
            destFile.createNewFile();
            OutputStream out = new FileOutputStream(destFile);
            byte[] cache = new byte[CACHE_SIZE];
            int nRead = 0;
            while ((nRead = in.read(cache)) != -1) {
                out.write(cache, 0, nRead);
                out.flush();
            }
            out.close();
            in.close();
        } catch (Exception e) {
            throw new Exception("二进制数据写文件", e);
        }
    }
}
