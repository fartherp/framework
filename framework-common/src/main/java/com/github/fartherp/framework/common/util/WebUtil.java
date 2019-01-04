/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import org.apache.commons.lang3.StringUtils;

import javax.mail.internet.MimeUtility;
import java.io.UnsupportedEncodingException;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/1/6
 */
public class WebUtil {
    /**
     * Guess content type.
     *
     * @param fileName the file name
     * @return the string
     */
    public static String guessContentType(String fileName) {
        return URLConnection.getFileNameMap().getContentTypeFor(fileName);
    }

    /**
     * 下载文件名简单编码.
     *
     * @param userAgent the user agent
     * @param fileName  文件名
     * @return the string
     */
    public static String encodeContentDisposition(String userAgent, String fileName) {
         try {
             String lowUserAgent = userAgent.toLowerCase();
             if (lowUserAgent.contains("msie") || lowUserAgent.contains("trident")) {
                 return "attachment;filename=" + StringUtils.replace(URLEncoder.encode(fileName, StandardCharsets.UTF_8.name()), "+", "%20");
             } else if (lowUserAgent.contains("opera")) {
                 // Opera浏览器只能采用filename*
                 return "attachment;filename*=UTF-8''" + fileName;
             } else if (lowUserAgent.contains("safari") || lowUserAgent.contains("applewebkit") || lowUserAgent.contains("mozilla")) {
                 // Safari浏览器，只能采用ISO编码的中文输出
                 // Chrome浏览器，只能采用MimeUtility编码或ISO编码的中文输出
                 // FireFox浏览器，可以使用MimeUtility或filename*或ISO编码的中文输出
                 return "attachment;filename=" + new String(fileName.getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
             } else {
                 // XXX "inline;filename=", 被要求改回来的.
                 return "attachment;filename=" + MimeUtility.encodeWord(fileName);
             }
         } catch (UnsupportedEncodingException e) {
             String charset = MimeUtility.getDefaultJavaCharset();
             throw new RuntimeException("default java charset [" + charset + "]", e);
         }
    }
}
