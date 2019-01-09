/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.Constant;
import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.OutputStream;
import java.util.List;
import java.util.Map;

/**
 * Excel写接口
 * Author: CK
 * Date: 2016/5/29
 */
public interface ExcelWrite<T> {

    /**
     * 当前excel文件类型
     * <pre>
     * {@link Constant#OFFICE_EXCEL_2003_POSTFIX}
     * {@link Constant#OFFICE_EXCEL_2003_POSTFIX}
     * </pre>
     *
     * @return 文件类型
     */
    String getType();

    /**
     * 标题
     * @return 标题
     */
    String[] getTitle();

    /**
     * 文件名
     * @return 文件名
     */
    String getFileName();

    /**
     * 输出流
     * @return 输出流
     */
    OutputStream getOutputStream();

    /**
     *
     * @return
     */
    WriteDeal<T> getDeal();

    /**
     * 当前处理的Workbook
     * @return Workbook
     */
    Workbook getWb();

    int getTotal();

    void setTotal(int total);

    int getCurrentSheetNumber();

    void setCurrentSheetNumber(int currentSheetNumber);

    Sheet getCurrentSheet();

    void setCurrentSheet(Sheet currentSheet);

    int getCurrentRow();

    void setCurrentRow(int currentRow);

    /**
     * 创建工作薄
     */
    void createWb();

    /**
     * 添加实际数据
     * @param list 实际数据
     * @return ExcelWrite
     */
    ExcelWrite<T> list(List<T> list);

    /**
     * 写数据
     */
    void write();

    /**
     * 创建输出流
     * @return ExcelWrite
     */
    ExcelWrite<T> createOutputStream();

    /**
     * 具体操作
     * @param deal 操作回调
     * @return ExcelWrite
     */
    ExcelWrite<T> deal(WriteDeal<T> deal);

    /**
     * 设置大数据模式（SXSSFWorkbook）
     * @param largeDataMode true/false
     */
    ExcelWrite<T> setLargeDataMode(boolean largeDataMode);

    /**
     * 大数据模式
     * @return true/false
     */
    boolean getLargeDataMode();

    /**
     * 额外数据
     * @param params map
     * @return ExcelWrite
     */
    ExcelWrite<T> additional(Map<String, Object> params);
}
