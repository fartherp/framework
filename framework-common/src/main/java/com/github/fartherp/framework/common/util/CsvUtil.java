/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import au.com.bytecode.opencsv.CSVWriter;
import com.github.fartherp.framework.common.constant.Constant;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * the CSV tools that the response write the csv file of the page and write the csv file of the user-defined
 * Author: CK
 * Date: 2015/9/22
 */
public class CsvUtil {
    /**
     * The csv file is output through the response of the page
     * @param response the response of the page
     * @param request the request of the page
     * @param filename the file name of user-defined
     * @param title the title content of the csv file
     * @param bodyList the body content of the csv file
     */
    public static void writeCsvFile(HttpServletResponse response, HttpServletRequest request,
                                    String filename, String[] title, List<String[]> bodyList) {
        CSVWriter writer = null;
        try {
            response.setContentType("application/octet-stream;charset=GBK");
            String fileName = URLEncoder.encode(filename, Constant.UTF_8);
            // 根据浏览器代理获取浏览器内核类型
            UserAgent userAgent = UserAgentUtil.getUserAgent(request.getHeader("USER-AGENT"));
            String browserType = userAgent == null ? "" : userAgent.getBrowserType();
            if ("Firefox".equals(browserType)) {
                // 针对火狐浏览器处理方式不一样了,解决Firefox下载文件名编码问题
                fileName = new String(filename.getBytes(Constant.UTF_8), Constant.ISO_8859_1);
            }
            response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".csv");
            writer = new CSVWriter(response.getWriter());
            if (title != null && title.length > 0) {
                writer.writeNext(title);
            }
            if (bodyList != null && bodyList.size() > 0) {
                writer.writeAll(bodyList);
            }
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("charset no support UTF-8", e);
        } catch (IOException e) {
            throw new RuntimeException("write csv file of response fail ", e);
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * The csv file wrote through user-defined
     * @param filename the file name
     * @param title the title content of the csv file
     * @param bodyList this body content of the csv file
     */
    public static void writeCsvFile(String filename, String[] title, List<String[]> bodyList) {
        CSVWriter writer = null;
        OutputStreamWriter osw = null;
        try {
            osw = new OutputStreamWriter(new FileOutputStream(new File(filename + ".csv")), "GBK");
            writer = new CSVWriter(osw);
            if (title != null && title.length > 0) {
                writer.writeNext(title);
            }
            if (bodyList != null && bodyList.size() > 0) {
                writer.writeAll(bodyList);
            }
        } catch (IOException e) {
            throw new RuntimeException("write csv file fail ", e);
        } finally {
            try {
                if (osw != null) {
                    osw.close();
                }
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                // ignore
            }
        }
    }

    /**
     * create csv String
     * @param outputList the output content list
     * @return the new {@code String}
     */
    public static String createCsvFile(List<List<Object>> outputList) {
        StringBuilder os = new StringBuilder();
        // enter symbol
        char returnChar = 13;
        // newline symbol
        char lineChar = 10;
        for (List<Object> unitList : outputList) {
            if (unitList != null && !unitList.isEmpty()) {
                StringBuilder sb = new StringBuilder();
                for (Object unitOb : unitList) {
                    String unit = "";
                    if (unitOb != null) {
                        unit = unitOb.toString().replaceAll(",", "");
                    }
                    sb.append(unit);
                    sb.append(",");
                }
                sb.deleteCharAt(sb.length() - 1);
                sb.append(returnChar);
                sb.append(lineChar);
                os.append(sb);
            }
        }
        return os.toString();
    }

}
