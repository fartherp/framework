/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.common.util;

import com.opencsv.CSVWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
        response.setContentType("application/octet-stream;charset=GBK");
        String fileName = FileUtilies.getFileName(filename, request);
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName + ".csv");

        try (CSVWriter writer = new CSVWriter(response.getWriter())) {
            if (title != null && title.length > 0) {
                writer.writeNext(title);
            }
            if (bodyList != null && bodyList.size() > 0) {
                writer.writeAll(bodyList);
            }
        } catch (IOException e) {
            throw new RuntimeException("write csv file of response fail ", e);
        }
    }

    /**
     * The csv file wrote through user-defined
     * @param filename the file name
     * @param title the title content of the csv file
     * @param bodyList this body content of the csv file
     */
    public static void writeCsvFile(String filename, String[] title, List<String[]> bodyList) {
        try (CSVWriter writer = new CSVWriter(new OutputStreamWriter(new FileOutputStream(new File(filename + ".csv")), "GBK"))) {
            if (title != null && title.length > 0) {
                writer.writeNext(title);
            }
            if (bodyList != null && bodyList.size() > 0) {
                writer.writeAll(bodyList);
            }
        } catch (IOException e) {
            throw new RuntimeException("write csv file fail ", e);
        }
    }

    /**
     * create csv String
     * @param outputList the output content list
     * @return the new {@code String}
     */
    public static String createCsvFile(List<List<Object>> outputList) {
        StringBuilder sb = new StringBuilder();
        // enter symbol
        char returnChar = '\r';
        // newline symbol
        char lineChar = '\n';
        for (List<Object> unitList : outputList) {
            if (unitList != null && !unitList.isEmpty()) {
                sb.setLength(0);
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
            }
        }
        return sb.toString();
    }

}
