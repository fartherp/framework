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
package com.github.fartherp.framework.poi.pdf.write;

import com.itextpdf.html2pdf.ConverterProperties;
import com.itextpdf.html2pdf.HtmlConverter;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.font.FontProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by IntelliJ IDEA.
 *
 * @author CK
 * @date 2018/12/19
 */
public abstract class AbstractPdfWrite implements PdfWrite {

	protected final Logger logger = LoggerFactory.getLogger(this.getClass());

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

    @Override
	public String getFileName() {
        return fileName;
    }

    @Override
	public OutputStream getOutputStream() {
        return outputStream;
    }

    @Override
	public PdfWrite addFontPath(String path) {
        fp.addDirectory(path);
        return this;
    }

    @Override
	public void write() {
        this.createOutputStream();

        try (PdfWriter writer = new PdfWriter(this.outputStream)) {
            PdfDocument pdfDoc = new PdfDocument(writer);

            Object o = this.deal.deal();

            convertToPdf(o, pdfDoc);
        } catch (IOException e) {
            throw new IllegalArgumentException("PDF写入错误", e);
        }
    }

    @Override
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
