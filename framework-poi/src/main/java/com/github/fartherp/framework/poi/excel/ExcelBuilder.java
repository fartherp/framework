/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel;

import com.github.fartherp.framework.poi.excel.write.ExcelWrite;
import com.github.fartherp.framework.poi.excel.write.FileExcelWrite;
import com.github.fartherp.framework.poi.excel.write.HttpServletResponseExcelWrite;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/11/25
 */
public class ExcelBuilder {

    @SuppressWarnings("all")
    public static <T> ExcelWrite<T> buildFile(String[] title, String fileName, List<T> list) {
        ExcelWrite<T> excelWrite = FileExcelWrite.getInstance(title, fileName, list);
        return excelWrite;
    }

    @SuppressWarnings("all")
    public static <T> ExcelWrite<T> buildHttpServletResponse(String[] title, String fileName, List<T> list, HttpServletResponse response) {
        ExcelWrite<T> excelWrite = HttpServletResponseExcelWrite.getInstance(title, fileName, list, response);
        return excelWrite;
    }
}
