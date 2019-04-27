/*
 * Copyright (c) 2019. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.Constant;
import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 新生成Excel模板
 * Author: CK
 * Date: 2019/4/24
 */
public class CreateNewExcelWrite extends AbstractExcelWrite {

    public CreateNewExcelWrite(String fileName, OutputStreamDelegate delegate) {
        super(fileName, delegate);
    }

    @Override
    public void createWb() {
        if (this.largeDataMode) {
            if (StringUtils.endsWith(fileName, Constant.OFFICE_EXCEL_2003_POSTFIX)) {
                this.type = Constant.OFFICE_EXCEL_2003_POSTFIX;
            } else if (StringUtils.endsWith(fileName, Constant.OFFICE_EXCEL_2010_POSTFIX)) {
                this.type = Constant.OFFICE_EXCEL_2010_POSTFIX;
            } else {
                throw new IllegalArgumentException("不是Excel文件");
            }
            wb = new SXSSFWorkbook(rowAccessWindowSize);
        } else {
            // 创建Excel文档
            if (StringUtils.endsWith(fileName, Constant.OFFICE_EXCEL_2003_POSTFIX)) {
                this.wb = new HSSFWorkbook();
                this.type = Constant.OFFICE_EXCEL_2003_POSTFIX;
            } else if (StringUtils.endsWith(fileName, Constant.OFFICE_EXCEL_2010_POSTFIX)) {
                this.wb = new XSSFWorkbook();
                this.type = Constant.OFFICE_EXCEL_2010_POSTFIX;
            } else {
                throw new IllegalArgumentException("不是Excel文件");
            }
        }
    }

    @Override
    public <T> Sheet createSheet(SheetWrapper currentSheetWrapper, String[] title, WriteDeal<T> deal) {
        // 标题多算一行数据
        currentSheetWrapper.increaseTotal();
        String sheetName = deal.name();
        // 第一个sheet(数量相等), 第二个及以后(超过最大行)
        // sheet 对应一个工作页
        Sheet currentSheet;
        if (sheetName == null) {
            currentSheet = wb.createSheet("sheet" + currentSheetNumber++);
        } else {
            StringBuilder sb = new StringBuilder();
            sb.append(sheetName);
            currentSheet = wb.getSheet(sb.toString());
            while (currentSheet != null) {
                // 自定义名称重名sheet，重新命名
                sb.setLength(0);
                sb.append(sheetName);
                sb.append(currentSheetNumber++);
                currentSheet = wb.getSheet(sb.toString());
            }
            currentSheet = wb.createSheet(sb.toString());
        }
        Row dataRow = currentSheet.createRow(currentSheetWrapper.increaseCurrentRow());
        // 设置标题数据
        for (int i = 0; i < title.length; i++) {
            Cell cell = dataRow.createCell(i);
            cell.setCellValue(title[i]);
            if(this.headStyle != null){
                cell.setCellStyle(this.headStyle);
            }
        }
        return currentSheet;
    }
}
