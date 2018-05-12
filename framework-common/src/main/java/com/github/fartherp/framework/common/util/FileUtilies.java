/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import com.github.fartherp.framework.common.constant.Constant;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.StringTokenizer;

/**
 * 文件工具类
 * Author: CK.
 * Date: 2015/6/6.
 */
public class FileUtilies {

    /**
     * 生成文件
     *
     * @param targetPackage 目标包
     * @param fileName      Mapper文件名
     * @return 文件
     */
    public static File getDirectory(String targetPackage, String fileName, String dir) {
        File project = new File(dir);
        if (!project.isDirectory()) {
            throw new RuntimeException("[" + project + "] is not directory ");
        }

        StringBuilder sb = new StringBuilder();
        StringTokenizer st = new StringTokenizer(targetPackage, ".");
        while (st.hasMoreTokens()) {
            sb.append(st.nextToken());
            sb.append(File.separatorChar);
        }

        File directory = new File(project, sb.toString());
        if (!directory.isDirectory()) {
            if (!directory.mkdirs()) {
                throw new RuntimeException("[" + directory + "] file create fail");
            }
        }

        return new File(directory, fileName);
    }

    /**
     * Writes, or overwrites, the contents of the specified file.
     *
     * @param file         the file
     * @param content      the content
     */
    public static void writeFile(File file, String content) {
        writeFile(file, content, "UTF-8");
    }

    /**
     * Writes, or overwrites, the contents of the specified file.
     *
     * @param file         the file
     * @param content      the content
     * @param fileEncoding the file encoding
     */
    public static void writeFile(File file, String content, String fileEncoding) {
        BufferedWriter bw = null;
        try {
            FileOutputStream fos = new FileOutputStream(file, false);
            OutputStreamWriter osw;
            if (fileEncoding == null) {
                osw = new OutputStreamWriter(fos);
            } else {
                osw = new OutputStreamWriter(fos, fileEncoding);
            }

            bw = new BufferedWriter(osw);
            bw.write(content);
        } catch (IOException e) {
            // ignore
        } finally {
            if (bw != null) {
                try {
                    bw.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }
    }

    public static String getFileName(String filename, HttpServletRequest request) {
        try {
            String fileName = URLEncoder.encode(filename, Constant.UTF_8);
            // 根据浏览器代理获取浏览器内核类型
            UserAgent userAgent = UserAgentUtil.getUserAgent(request.getHeader("USER-AGENT"));
            String browserType = userAgent == null ? "" : userAgent.getBrowserType();
            if ("Firefox".equals(browserType)) {
                // 针对火狐浏览器处理方式不一样了,解决Firefox下载文件名编码问题
                fileName = new String(filename.getBytes(Constant.UTF_8), Constant.ISO_8859_1);
            }
            return fileName;
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("charset no support UTF-8", e);
        }
    }
}
