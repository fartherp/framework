/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.common.util.FileUtilies;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 响应流
 *
 * @author: CK
 * @date: 2017/11/25
 */
public class HttpServletResponseExcelWrite<T> extends AbstractExcelWrite<T> {

    private HttpServletResponse response;

    public <T> HttpServletResponseExcelWrite(String[] title, String fileName, HttpServletRequest request, HttpServletResponse response) {
        super(title, fileName);
        this.setResponse(request, response);
        this.createOutputStream();
    }

    public ExcelWrite<T> createOutputStream() {
        try {
            this.outputStream = this.response.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return this;
    }

    public ExcelWrite<T> setResponse(HttpServletRequest request, HttpServletResponse response) {
        this.response = response;
        response.reset();
        response.setContentType("application/msexcel;charset=GBK");
        String filename = FileUtilies.getFileName(this.fileName, request);
        response.setHeader("content-disposition", "attachment; filename=" + filename);
        return this;
    }
}
