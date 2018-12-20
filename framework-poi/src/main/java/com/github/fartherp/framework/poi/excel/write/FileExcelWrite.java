/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

/**
 * 文件流
 *
 * @author: CK
 * @date: 2017/11/25
 */
public class FileExcelWrite<T> extends AbstractExcelWrite<T> {
    public FileExcelWrite(String[] title, String fileName) {
        super(title, fileName);
    }

    public ExcelWrite<T> createOutputStream() {
        try {
            this.outputStream = new FileOutputStream(this.fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件不存在", e);
        }
        return this;
    }
}
