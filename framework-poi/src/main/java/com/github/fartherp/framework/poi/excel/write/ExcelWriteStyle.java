/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.util.CellRangeAddress;

import static com.github.fartherp.framework.poi.excel.ExcelUtils.setRegionStyle;
import static com.github.fartherp.framework.poi.excel.ExcelUtils.setRichTextString;
import static com.github.fartherp.framework.poi.excel.ExcelUtils.setStyleColorStrong;
import static com.github.fartherp.framework.poi.excel.ExcelUtils.setStyleLightColorStrong;
import static com.github.fartherp.framework.poi.excel.ExcelUtils.setStyleNoColor;

/**
 * 自定义风格输出
 * Author: CK
 * Date: 2016/5/29
 */
public class ExcelWriteStyle<T> extends AbstractExcelWrite<T> {

    private ExcelWrite<T> excelWrite;

    /**
     * 头
     */
    private String head;

    /**
     * 条件
     */
    private String condition;

    public ExcelWriteStyle(ExcelWrite<T> excelWrite) {
        super(excelWrite.getTitle(), excelWrite.getFileName(), excelWrite.getList());
        this.excelWrite = excelWrite;
        this.outputStream = this.excelWrite.getOutputStream();
    }

    public void deal(WriteDeal<T> deal) {
        // 总条数
        int size = this.list.size();
        // 最大行数
        int maxRows = deal.setMaxRows();
        // 创建几个sheet
        int sheetNum = size / maxRows + 1;

        for (int j = 1; j <= sheetNum; j++) {
            // sheet 对应一个工作页
            Sheet sheet = this.wb.createSheet("sheet" + j);
            int offset = 0;

            CellRangeAddress region0 = new CellRangeAddress(offset, offset, (short) 0, (short) this.title.length - 1);
            sheet.addMergedRegion(region0);

            Row row0 = sheet.createRow(offset);
            row0.setHeight(deal.setHeight());
            offset++;

            Cell cell0 = row0.createCell(0);
            cell0.setCellValue(setRichTextString(this.type, head));

            CellStyle lightColorStrong = setStyleLightColorStrong(wb);
            setRegionStyle(sheet, region0, lightColorStrong);

            CellRangeAddress region1 = new CellRangeAddress(offset, offset, (short) 0, (short) this.title.length - 1);
            sheet.addMergedRegion(region1);

            Row row1 = sheet.createRow(offset);
            row1.setHeight(deal.setHeight());
            offset++;

            Cell cell1 = row1.createCell(0);
            cell1.setCellValue(setRichTextString(this.type, this.condition));

            setRegionStyle(sheet, region1, lightColorStrong);

            Row titleRow = sheet.createRow(offset);
            titleRow.setHeight(deal.setHeight());
            offset++;

            CellStyle colorStrong = setStyleColorStrong(this.wb);
            int[] columnWidth = deal.setColumnWidth(this.title);
            for (int i = 0; i < this.title.length; i++) {
                Cell cell = titleRow.createCell(i);
                cell.setCellValue(setRichTextString(this.type, this.title[i]));
                cell.setCellStyle(colorStrong);
                sheet.setColumnWidth(i, columnWidth[i]);
            }

            CellStyle noColor = setStyleNoColor(this.wb);

            offset = 3;
            // 除去标题，条件，头
            int maxRow = maxRows - offset;
            // 开始位置
            int start = (j - 1) * maxRow;
            // 结束位置
            int end = j * maxRow > size ? size : j * maxRow;
            // 每个sheet的row重新计算
            int row = offset;
            for (int i = start; i < end; i++) {
                // 创建一行
                Row dataRow = sheet.createRow(row);
                dataRow.setHeight(deal.setHeight());
                row++;
                // 得到要插入的每一条记录
                String[] data = deal.dealBean(this.list.get(i));
                for (int k = 0; k < data.length; k++) {
                    // 在一行内循环
                    Cell cell = dataRow.createCell(k);
                    cell.setCellValue(setRichTextString(this.type, data[k]));
                    cell.setCellStyle(noColor);
                }
            }
        }
    }

    public ExcelWrite<T> createOutputStream() {
        throw new IllegalArgumentException("no outputStream");
    }

    public ExcelWriteStyle<T> head(String head) {
        this.head = head;
        return this;
    }

    public ExcelWriteStyle<T> condition(String condition) {
        this.condition = condition;
        return this;
    }
}
