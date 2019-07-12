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
package com.github.fartherp.framework.common.util;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
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

    public static String getFileName(String fileName, HttpServletRequest request) {
        try {
            // 根据浏览器代理获取浏览器内核类型
            UserAgent userAgent = UserAgentUtil.getUserAgent(request.getHeader("USER-AGENT"));
            if (userAgent != null && "Firefox".equals(userAgent.getBrowserType())) {
                // 针对火狐浏览器处理方式不一样了,解决Firefox下载文件名编码问题
                return new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
            } else {
                return URLEncoder.encode(fileName, StandardCharsets.UTF_8.name());
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("charset no support UTF-8", e);
        }
    }
}
