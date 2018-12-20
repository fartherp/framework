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
