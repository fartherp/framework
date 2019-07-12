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
     * 设置大数据缓存记录数
     * @param rowAccessWindowSize 缓存记录数
     * @return ExcelWrite
     */
    ExcelWrite setRowAccessWindowSize(int rowAccessWindowSize);

    /**
     * 设置头的样式风格
     * @param headStyleConsumer 头样式风格
     * @return ExcelWrite
     */
    ExcelWrite setHeadStyle(BiConsumer<CellStyle, Font> headStyleConsumer);

    /**
     * 设置包体的样式风格
     * @param bodyStyleConsumer 包体样式风格
     * @return ExcelWrite
     */
    ExcelWrite setBodyStyle(BiConsumer<CellStyle, Font> bodyStyleConsumer);
}
