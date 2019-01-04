/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.pdf.write;

import com.github.fartherp.framework.common.util.FileUtilies;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static com.github.fartherp.framework.poi.Constant.PDF_CONTENT_TYPE;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/12/20
 */
public class HttpServletResponsePdfWrite extends AbstractPdfWrite {
    private HttpServletResponse response;

    public HttpServletResponsePdfWrite(String fileName, HttpServletRequest request, HttpServletResponse response) {
        super(fileName);
        this.setResponse(request, response);
    }

    public PdfWrite createOutputStream() {
        try {
            this.outputStream = this.response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("响应流异常", e);
        }
        return this;
    }

    public PdfWrite setResponse(HttpServletRequest request, HttpServletResponse response) {
        this.response = response;
        response.reset();
        response.setContentType(PDF_CONTENT_TYPE);
        String filename = FileUtilies.getFileName(this.fileName, request);
        response.setHeader("content-disposition", "attachment; filename=" + filename);
        return this;
    }
}
