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

    protected Workbook wb;

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

    public void createWb() {
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

    public void writeExcel(WriteDeal<T> deal) {
        // 创建excel
        createWb();
        deal.setExcelWrite(this);
        // 实际业务处理
        this.deal(deal);
        // 写内容
        write(this.wb);
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
            Row sheetRow = sheet.createRow(0); // 下标为0的行开始
            for (int i = 0; i < this.title.length; i++) {
                Cell cell = sheetRow.createCell(i);
                cell.setCellValue(this.title[i]);
            }
            int offset = 1;
            // 除去标题
            int maxRow = maxRows - offset;
            // 开始位置
            int start = (j - 1) * maxRow;
            // 结束位置
            int end = j * maxRow > size ? size : j * maxRow;
            // 每个sheet的row重新计算
            int row = offset;
            for (int i = start; i < end; i++) {
                // 创建一行
                sheetRow = sheet.createRow(row);
                row++;
                // 得到要插入的每一条记录
                String [] arr = deal.dealBean(this.list.get(i));
                for (int k = 0; k < arr.length; k++) {
                    // 在一行内循环
                    Cell cell = sheetRow.createCell(k);
                    cell.setCellValue(arr[k]);
                }
            }
        }
    }

    public void write(Workbook wb) {
        // 创建文件输出流，准备输出电子表格
        try {
            wb.write(this.outputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (this.outputStream != null) {
                try {
                    this.outputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static abstract class DefaultWriteDeal<T> implements WriteDeal<T> {
        private ExcelWrite excelWrite;
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

        public int setMaxRows() {
            if (Constant.OFFICE_EXCEL_2010_POSTFIX.equals(excelWrite.getType())) {
                return 1048576;
            }
            return 65536;
        }

        public void setExcelWrite(ExcelWrite excelWrite) {
            this.excelWrite = excelWrite;
        }
    }
}
