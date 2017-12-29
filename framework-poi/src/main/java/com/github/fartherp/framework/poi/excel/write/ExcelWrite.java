/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;
import java.util.List;

/**
 * Excel写接口
 * Author: CK
 * Date: 2016/5/29
 */
public interface ExcelWrite<T> {

    String getType();

    String[] getTitle();

    String getFileName();

    OutputStream getOutputStream();

    WriteDeal<T> getDeal();

    Workbook getWb();

    int getTotal();

    void setTotal(int total);

    int getCurrentSheetNumber();

    void setCurrentSheetNumber(int currentSheetNumber);

    Sheet getCurrentSheet();

    void setCurrentSheet(Sheet currentSheet);

    int getCurrentRow();

    void setCurrentRow(int currentRow);

    void createWb();

    ExcelWrite<T> list(List<T> list);

    void write();

    ExcelWrite<T> createOutputStream();

    ExcelWrite<T> deal(WriteDeal<T> deal);
}
