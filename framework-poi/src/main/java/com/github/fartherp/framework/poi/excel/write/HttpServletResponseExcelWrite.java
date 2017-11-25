/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * 响应流
 *
 * @author: CK
 * @date: 2017/11/25
 */
public class HttpServletResponseExcelWrite<T> extends AbstractExcelWrite<T> {

    private HttpServletResponse response;

    private HttpServletResponseExcelWrite(String[] title, String fileName, List<T> list, HttpServletResponse response) {
        super(title, fileName, list);
        this.setResponse(response);
    }

    public static <T> HttpServletResponseExcelWrite getInstance(String[] title, String fileName, List<T> list, HttpServletResponse response) {
        return new HttpServletResponseExcelWrite<T>(title, fileName, list, response);
    }

    public ExcelWrite<T> createOutputStream() {
        try {
            this.outputStream = this.response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ExcelWrite<T> setResponse(HttpServletResponse response) {
        this.response = response;
        response.reset();
        response.setContentType("application/msexcel;charset=GBK");
        String filename;
        try {
            filename = URLEncoder.encode(this.fileName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            try {
                filename = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
            } catch (UnsupportedEncodingException e1) {
                e1.printStackTrace();
                filename = fileName;
            }
        }
        response.setHeader("content-disposition", "attachment; filename=" + filename);
        return this;
    }
}
