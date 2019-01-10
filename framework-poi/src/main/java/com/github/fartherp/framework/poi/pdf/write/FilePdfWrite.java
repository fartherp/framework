/*
 * Copyright (c) 2018. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.pdf.write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2018/12/19
 */
public class FilePdfWrite extends AbstractPdfWrite {

    /**
     * <p>
     * Example code:
     * </p>
     * <pre>
     *  new FilePdfWrite("合同.pdf", request, response)
     *                 .addFontPath(getFontPath())//字体路径
     *                 .deal((PdfWriteDeal<String>) () -> "合同内容").write();
     * </pre>
     *
     * @param fileName the output file name
     *
     * @see <a href="https://github.com/fartherp/framework/blob/master/framework-poi/src/test/resources/d.html">
     *     file content</a>
     */
    public FilePdfWrite(String fileName) {
        super(fileName);
    }

    public PdfWrite createOutputStream() {
        try {
            this.outputStream = new FileOutputStream(this.fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件不存在", e);
        }
        return this;
    }
}
