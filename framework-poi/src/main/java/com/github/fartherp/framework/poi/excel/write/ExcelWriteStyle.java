/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.Constant;
import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;

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

    private ExcelWrite excelWrite;

    /**
     * 头
     */
    private String head;

    /**
     * 条件
     */
    private String condition;

    public ExcelWriteStyle(ExcelWrite excelWrite) {
        super(excelWrite.getTitle(), excelWrite.getFileName(), excelWrite.getList());
        this.excelWrite = excelWrite;
    }

    /**
     * @param deal  实际处理结果
     */
    public void writeExcel(WriteDeal<T> deal) {
        // 创建Excel文档
        Workbook wb;
        if (StringUtils.endsWith(fileName, Constant.OFFICE_EXCEL_2003_POSTFIX)) {
            wb = new HSSFWorkbook();
            type = Constant.OFFICE_EXCEL_2003_POSTFIX;
        } else if (StringUtils.endsWith(fileName, Constant.OFFICE_EXCEL_2010_POSTFIX)) {
            wb = new XSSFWorkbook();
            type = Constant.OFFICE_EXCEL_2010_POSTFIX;
        } else {
            throw new IllegalArgumentException("不是Excel文件");
        }
        // sheet 对应一个工作页
        Sheet sheet = wb.createSheet("sheet1");
        int offset = 0;

        CellRangeAddress region0 = new CellRangeAddress(offset, offset, (short) 0, (short) title.length - 1);
        sheet.addMergedRegion(region0);

        Row row0 = sheet.createRow(offset);
        row0.setHeight(deal.setHeight());
        offset++;

        Cell cell0 = row0.createCell(0);
        cell0.setCellValue(setRichTextString(type, head));

        CellStyle lightColorStrong = setStyleLightColorStrong(wb);
        setRegionStyle(sheet, region0, lightColorStrong);

        CellRangeAddress region1 = new CellRangeAddress(offset, offset, (short) 0, (short) title.length - 1);
        sheet.addMergedRegion(region1);

        Row row1 = sheet.createRow(offset);
        row1.setHeight(deal.setHeight());
        offset++;

        Cell cell1 = row1.createCell(0);
        cell1.setCellValue(setRichTextString(type, condition));

        setRegionStyle(sheet, region1, lightColorStrong);

        Row titleRow = sheet.createRow(offset);
        titleRow.setHeight(deal.setHeight());
        offset++;

        CellStyle colorStrong = setStyleColorStrong(wb);
        int[] columnWidth = deal.setColumnWidth(title);
        for (int i = 0; i < title.length; i++) {
            Cell cell = titleRow.createCell(i);
            cell.setCellValue(setRichTextString(type, title[i]));
            cell.setCellStyle(colorStrong);
            sheet.setColumnWidth(i, columnWidth[i]);
        }

        CellStyle noColor = setStyleNoColor(wb);
        for (T t : list) {
            // 创建一行
            Row dataRow = sheet.createRow(offset);
            dataRow.setHeight(deal.setHeight());
            offset++;
            // 得到要插入的每一条记录
            String[] data = deal.dealBean(t);
            for (int j = 0; j < data.length; j++) {
                // 在一行内循环
                Cell cell = dataRow.createCell(j);
                cell.setCellValue(setRichTextString(type, data[j]));
                cell.setCellStyle(noColor);
            }
        }
        // 创建文件输出流，准备输出电子表格
        OutputStream outputStream = excelWrite.getOutputStream();
        try {
            wb.write(outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    outputStream = null;
                }
            }
        }
    }

    public ExcelWrite<T> createOutputStream() {
        throw new IllegalArgumentException("no outputStream");
    }

    public ExcelWriteStyle head(String head) {
        this.head = head;
        return this;
    }

    public ExcelWriteStyle condition(String condition) {
        this.condition = condition;
        return this;
    }
}
