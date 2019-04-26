/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;

import java.util.List;
import java.util.function.BiConsumer;

/**
 * Excel写接口
 * Author: CK
 * Date: 2016/5/29
 */
public interface ExcelWrite {

    /**
     * 写数据
     */
    void write();

    /**
     * 具体操作
     * @param deal 操作回调
     * @return ExcelWrite
     */
    <T> ExcelWrite deal(String[] title, WriteDeal<T> deal, List<T> list);

    /**
     * 具体操作
     * @param deal 操作回调
     * @return ExcelWrite
     */
    <T> ExcelWrite deal(WriteDeal<T> deal, List<T> list);

    /**
     * 设置大数据模式（SXSSFWorkbook）
     * @param largeDataMode true/false
     */
    ExcelWrite setLargeDataMode(boolean largeDataMode);

    /**
     * 设置头的样式风格
     * @param headStyle 头样式风格
     * @return ExcelWrite
     */
    ExcelWrite setHeadStyle(BiConsumer<CellStyle, Font> headStyle);

    /**
     * 设置包体的样式风格
     * @param bodyStyle 包体样式风格
     * @return ExcelWrite
     */
    ExcelWrite setBodyStyle(BiConsumer<CellStyle, Font> bodyStyle);
}
