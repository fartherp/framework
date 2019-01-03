/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.pdf.write;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/12/19
 */
public abstract class AbstractPdfWrite implements PdfWrite {

    /**
     * 文件名
     */
    protected String fileName;

    /**
     * 输出流
     */
    protected OutputStream outputStream;

    protected ConverterProperties converterProperties;

    protected FontProvider fp;

    protected PdfWriteDeal<?> deal;

    public AbstractPdfWrite(String fileName) {
        this.fileName = fileName;
        this.converterProperties = new ConverterProperties();
        this.fp = new FontProvider();
        this.fp.addStandardPdfFonts();
        this.converterProperties.setFontProvider(fp);
    }

    public String getFileName() {
        return fileName;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public PdfWrite addFontPath(String path) {
        fp.addDirectory(path);
        return this;
    }

    public void write() {
        this.createOutputStream();

        try (PdfWriter writer = new PdfWriter(this.outputStream)) {
            PdfDocument pdfDoc = new PdfDocument(writer);

            Object o = this.deal.deal();

            convertToPdf(o, pdfDoc);
        } catch (IOException e) {
            throw new RuntimeException("PDF写入错误", e);
        }
    }

    public PdfWrite deal(PdfWriteDeal<?> deal) {
        this.deal = deal;
        return this;
    }

    /**
     * 目前只提供html转换，其他需要自己实现
     */
    protected <R> void convertToPdf(R r, PdfDocument pdfDoc) throws IOException {
        HtmlConverter.convertToPdf((String) r, pdfDoc, this.converterProperties);
    }
}
