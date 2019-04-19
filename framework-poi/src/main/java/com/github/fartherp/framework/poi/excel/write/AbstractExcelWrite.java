/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.Constant;
import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.commons.collections4.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.text.StringSubstitutor;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Excel模板抽象类
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
     * 输出流
     */
    protected OutputStream outputStream;

    /**
     * 自定义处理类
     */
    protected WriteDeal<T> deal;

    /**
     * excel
     */
    protected Workbook wb;

    /**
     * 总数量
     */
    protected int total;

    /**
     * 当前处理sheet编号
     */
    protected int currentSheetNumber;

    /**
     * 当前处理sheet
     */
    protected Sheet currentSheet;

    /**
     * 当前处理sheet的行
     */
    protected int currentRow;

    /**
     * 大数据模式
     */
    protected boolean largeDataMode = true;

    /**
     * 内存中保留 5000 条数据，以免内存溢出，其余写入硬盘
     */
    protected int rowAccessWindowSize = 5000;

    /**
     * 文件输入流
     */
    private InputStream inputStream;

    /**
     * 额外参数
     */
    private Map<String, Object> params;
    /**
     * 头样式风格
     */
    private CellStyle headStyle;

    /**
     * 头样式风格
     */
    private BiConsumer<CellStyle, Font> headStyleConsumer;

    /**
     * 包体样式风格
     */
    private CellStyle bodyStyle;

    /**
     * 包体样式风格
     */
    private BiConsumer<CellStyle, Font> bodyStyleConsumer;

    public AbstractExcelWrite(InputStream inputStream, String fileName) {
        this.inputStream = inputStream;
        this.fileName = fileName;
    }

    public AbstractExcelWrite(String[] title, String fileName) {
        this.title = title;
        this.fileName = fileName;
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

    public OutputStream getOutputStream() {
        return outputStream;
    }

    public WriteDeal<T> getDeal() {
        return deal;
    }

    public Workbook getWb() {
        return wb;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getCurrentSheetNumber() {
        return currentSheetNumber;
    }

    public void setCurrentSheetNumber(int currentSheetNumber) {
        this.currentSheetNumber = currentSheetNumber;
    }

    public Sheet getCurrentSheet() {
        return currentSheet;
    }

    public void setCurrentSheet(Sheet currentSheet) {
        this.currentSheet = currentSheet;
    }

    public int getCurrentRow() {
        return currentRow;
    }

    public void setCurrentRow(int currentRow) {
        this.currentRow = currentRow;
    }

    public ExcelWrite<T> setLargeDataMode(boolean largeDataMode) {
        this.largeDataMode = largeDataMode;
        return this;
    }

    public boolean getLargeDataMode() {
        return largeDataMode;
    }

    public ExcelWrite<T> additional(Map<String, Object> params) {
        this.params = params;
        return this;
    }

    @Override
    public ExcelWrite<T> setHeadStyle(BiConsumer<CellStyle, Font> headStyle) {
        this.headStyleConsumer = headStyle;
        return this;
    }

    @Override
    public ExcelWrite<T> setBodyStyle(BiConsumer<CellStyle, Font> bodyStyle) {
        this.bodyStyleConsumer = bodyStyle;
        return this;
    }

    public void createWb() {
        if (this.inputStream != null) {
            try {
                this.wb = WorkbookFactory.create(this.inputStream);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
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

    public ExcelWrite<T> deal(WriteDeal<T> deal) {
        if (fileName == null) {
            throw new IllegalArgumentException("文件名不存在");
        }
        this.createOutputStream();
        // 创建excel
        createWb();
        if (this.headStyleConsumer != null) {
            this.headStyle = wb.createCellStyle();
            Font headFont = wb.createFont();
            this.headStyleConsumer.accept(this.headStyle, headFont);
            this.headStyle.setFont(headFont);
        }
        if (this.bodyStyleConsumer != null) {
            this.bodyStyle = wb.createCellStyle();
            Font bodyFont = wb.createFont();
            this.bodyStyleConsumer.accept(this.bodyStyle, bodyFont);
            this.bodyStyle.setFont(bodyFont);
        }
        this.deal = deal;
        return this;
    }

    public ExcelWrite<T> list(List<T> list) {
        // 实际业务处理
        // 当前总数
        total += list.size();
        createSheet(list, true);
        for (T t : list) {
            createSheet(list, false);
            // 创建一行
            Row sheetRow = currentSheet.createRow(currentRow);
            // 得到要插入的每一条记录
            String [] data = deal.dealBean(t);
            for (int k = 0; k < data.length; k++) {
                // 在一行内循环
                Cell cell = sheetRow.createCell(k);
                cell.setCellValue(data[k]);
                if(this.bodyStyle != null){
                    cell.setCellStyle(this.bodyStyle);
                }
            }
            currentRow++;
        }
        return this;
    }

    public void createSheet(List<T> list, boolean flag) {
        // excel处理的最大行数
        int maxRows = deal.setMaxRows(this.getType());
        if ((flag && total == list.size()) || (total > maxRows && currentRow == maxRows)) {
            currentRow = deal.skipLine();
            if (this.inputStream != null) {
                if (currentSheetNumber >= wb.getNumberOfSheets()) {
                    throw new RuntimeException("数据太大，超过设置的sheet数");
                }
                currentSheet = wb.getSheetAt(currentSheetNumber++);

                // 处理跳过的行数据
                int lastRowNum = currentSheet.getLastRowNum();
                for (int i = 0; i <= lastRowNum; i++) {
                    Row row = currentSheet.getRow(i);
                    int lastCellNum = row.getLastCellNum();
                    for (int j = 0; j < lastCellNum; j++) {
                        Cell cell = row.getCell(j);
                        String stringCellValue = cell.getStringCellValue();
                        if (StringUtils.isBlank(stringCellValue) || MapUtils.isEmpty(params)) {
                            continue;
                        }
                        stringCellValue =  new StringSubstitutor(params).replace(stringCellValue);
                        cell.setCellValue(stringCellValue);
                        if(this.headStyle != null){
                            cell.setCellStyle(this.headStyle);
                        }
                    }
                }

                return;
            }
            total += 1;
            // 第一个sheet(数量相等), 第二个及以后(超过最大行)
            // sheet 对应一个工作页
            currentSheet = wb.createSheet("sheet" + currentSheetNumber++);
            Row dataRow = currentSheet.createRow(currentRow);
            for (int i = 0; i < title.length; i++) {
                Cell cell = dataRow.createCell(i);
                cell.setCellValue(title[i]);
                if(this.headStyle != null){
                    cell.setCellStyle(this.headStyle);
                }
            }
            currentRow++;
        }
    }

    public void write() {
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
            if (this.inputStream != null) {
                try {
                    this.inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
