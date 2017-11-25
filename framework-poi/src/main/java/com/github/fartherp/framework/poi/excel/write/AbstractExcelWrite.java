/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.Constant;
import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/11/25
 */
public abstract class AbstractExcelWrite<T> implements ExcelWrite<T> {

    /**
     * 文件类型
     */
    protected String type;

    /**
     * 标题
     */
    protected String[] title;

    /**
     * 文件名
     */
    protected String fileName;

    /**
     * 数据
     */
    protected List<T> list;

    protected OutputStream outputStream;

    public AbstractExcelWrite(String[] title, String fileName, List<T> list) {
        this.title = title;
        this.fileName = fileName;
        this.list = list;
    }

    public String getType() {
        return type;
    }

    public String[] getTitle() {
        return title;
    }

    public String getFileName() {
        return fileName;
    }

    public List<T> getList() {
        return list;
    }

    public OutputStream getOutputStream() {
        return outputStream;
    }

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
        Row sheetRow = sheet.createRow(0); // 下标为0的行开始
        for (int i = 0; i < title.length; i++) {
            Cell cell = sheetRow.createCell(i);
            cell.setCellValue(title[i]);
        }
        for (int i = 0; i < list.size(); i++) {
            // 创建一行
            sheetRow = sheet.createRow(i + 1);
            // 得到要插入的每一条记录
            String [] arr = deal.dealBean(list.get(i));
            for (int j = 0; j < arr.length; j++) {
                // 在一行内循环
                Cell cell = sheetRow.createCell(j);
                cell.setCellValue(arr[j]);
            }
        }
        // 创建文件输出流，准备输出电子表格
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
                }
            }
        }
    }

    public static abstract class DefaultWriteDeal<T> implements WriteDeal<T> {
        public int[] setColumnWidth(String[] title) {
            int[] columnWidth = new int[title.length];
            for (int i = 0; i < title.length; i++) {
                columnWidth[i] = Constant.WIDTH_DEFAULT;
            }
            return columnWidth;
        }

        public short setHeight() {
            return Constant.HEIGHT_DEFAULT;
        }
    }
}
