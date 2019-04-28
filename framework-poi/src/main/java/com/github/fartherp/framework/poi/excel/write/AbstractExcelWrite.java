/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * Excel模板抽象类
 *
 * @author: CK
 * @date: 2017/11/25
 */
public abstract class AbstractExcelWrite implements ExcelWrite {

    /**
     * 文件类型
     */
    protected String type;

    /**
     * 文件名
     */
    protected String fileName;

    /**
     * 输出流
     */
    protected OutputStream outputStream;

    /**
     * excel
     */
    protected Workbook wb;

    /**
     * 当前处理sheet编号
     */
    protected int currentSheetNumber;

    /**
     * 大数据模式
     */
    protected boolean largeDataMode = true;

    /**
     * 内存中保留 5000 条数据，以免内存溢出，其余写入硬盘
     */
    protected int rowAccessWindowSize = 65536;

    /**
     * 头样式风格
     */
    protected CellStyle headStyle;

    /**
     * 头样式风格
     */
    protected BiConsumer<CellStyle, Font> headStyleConsumer;

    /**
     * 包体样式风格
     */
    protected CellStyle bodyStyle;

    /**
     * 包体样式风格
     */
    protected BiConsumer<CellStyle, Font> bodyStyleConsumer;

    private Map<Integer, LinkedList<SheetWrapper>> wrapperLinkedListMap = new HashMap<>();

    private OutputStreamDelegate delegate;

    public AbstractExcelWrite(String fileName, OutputStreamDelegate delegate) {
        this.fileName = fileName;
        this.delegate = delegate;
    }

    @Override
    public ExcelWrite setLargeDataMode(boolean largeDataMode) {
        this.largeDataMode = largeDataMode;
        return this;
    }

    @Override
    public ExcelWrite setRowAccessWindowSize(int rowAccessWindowSize) {
        if (rowAccessWindowSize != 0) {
            this.rowAccessWindowSize = rowAccessWindowSize;
        }
        return this;
    }

    @Override
    public ExcelWrite setHeadStyle(BiConsumer<CellStyle, Font> headStyle) {
        this.headStyleConsumer = headStyle;
        return this;
    }

    @Override
    public ExcelWrite setBodyStyle(BiConsumer<CellStyle, Font> bodyStyle) {
        this.bodyStyleConsumer = bodyStyle;
        return this;
    }

    private void build() {
        if (wb == null) {
            this.outputStream = delegate.createOutputStream(this.fileName);
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
        }
    }

    public abstract void createWb();

    @Override
    public <T> ExcelWrite deal(String[] title, WriteDeal<T> deal, List<T> list) {
        if (fileName == null) {
            throw new IllegalArgumentException("文件名不存在");
        }
        build();
        // 标题一样，认为是一个sheet类型，标题不一样，按回调对象来区分
        int hashCode = title != null && title.length == 0 ? deal.hashCode() : Arrays.hashCode(title);
        // 同一sheet类型，链表结构，达到Excel最大数量，可以追加
        LinkedList<SheetWrapper> linkedList = wrapperLinkedListMap.computeIfAbsent(hashCode, k -> new LinkedList<>());
        list(title, deal, list, linkedList);
        return this;
    }

    @Override
    public <T> ExcelWrite deal(WriteDeal<T> deal, List<T> list) {
        return this.deal(new String[0], deal, list);
    }

    private <T> void list(String[] title, WriteDeal<T> deal, List<T> list, LinkedList<SheetWrapper> linkedList) {
        SheetWrapper currentSheetWrapper = linkedList.peekLast();
        for (T t : list) {
            // 数据超过一个sheet最大值，需要创建第二个sheet
            currentSheetWrapper = createSheet(linkedList, currentSheetWrapper, title, deal, list.size());
            // 创建一行
            Row sheetRow = currentSheetWrapper.getSheet().createRow(currentSheetWrapper.increaseCurrentRow());
            // 得到要插入的每一条记录
            String[] data = deal.dealBean(t);
            for (int k = 0; k < data.length; k++) {
                // 在一行内循环
                Cell cell = sheetRow.createCell(k);
                cell.setCellValue(data[k]);
                if (this.bodyStyle != null) {
                    cell.setCellStyle(this.bodyStyle);
                }
            }
        }
    }

    private <T> SheetWrapper createSheet(LinkedList<SheetWrapper> linkedList, SheetWrapper currentSheetWrapper,
                                 String[] title, WriteDeal<T> deal, int size) {
        // excel处理的最大行数
        int maxRows = deal.setMaxRows(this.type);
        // sheet创建（当前sheet不存在，整个excel第一次创建）（第二次数据达到最大值时）
        if (currentSheetWrapper == null || currentSheetWrapper.getCurrentRow() == maxRows) {
            SheetWrapper newSheetWrapper = new SheetWrapper();
            newSheetWrapper.setTitleLength(title.length);
            newSheetWrapper.setCurrentRow(deal.skipLine());
            Sheet sheet = createSheet(newSheetWrapper, title, deal);
            newSheetWrapper.setSheet(sheet);
            SheetWrapper prev = linkedList.peekLast();
            if (prev != null) {
                int overRow = prev.total - maxRows;
                newSheetWrapper.increaseTotal(size + overRow);
            } else {
                newSheetWrapper.increaseTotal(size);
            }
            linkedList.addLast(newSheetWrapper);
            return newSheetWrapper;
        }
        return currentSheetWrapper;
    }

    public abstract <T> Sheet createSheet(SheetWrapper currentSheetWrapper, String[] title, WriteDeal<T> deal);

    @Override
    public void write() {
        // 最终把所有sheet改成自动调整列宽
        wrapperLinkedListMap.forEach((k, v) -> {
            v.forEach(currentSheetWrapper -> {
                if (largeDataMode) {
                    Sheet currentSheet = currentSheetWrapper.getSheet();
                    SXSSFSheet sxssfSheet = (SXSSFSheet) currentSheet;
                    // 自动调整列宽
                    sxssfSheet.trackAllColumnsForAutoSizing();
                    for (int i = 0; i < currentSheetWrapper.getTitleLength(); i++) {
                        currentSheet.autoSizeColumn(i);
                    }
                }
            });
        });

        // 创建文件输出流，准备输出电子表格
        try {
            wb.write(this.outputStream);
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

    final class SheetWrapper {
        private Sheet sheet;
        private int currentRow = 0;
        private int titleLength;
        /**
         * 总数量（同一类型数据，sheet计数）
         */
        private int total = 0;

        public Sheet getSheet() {
            return sheet;
        }

        public void setSheet(Sheet sheet) {
            this.sheet = sheet;
        }

        public int getCurrentRow() {
            return currentRow;
        }

        public void setCurrentRow(int currentRow) {
            this.currentRow = currentRow;
        }

        public int increaseCurrentRow() {
            return currentRow++;
        }

        public int getTitleLength() {
            return titleLength;
        }

        public void setTitleLength(int titleLength) {
            this.titleLength = titleLength;
        }

        public int getTotal() {
            return total;
        }

        public void increaseTotal() {
            ++total;
        }

        public void increaseTotal(int i) {
            total += i;
        }
    }
}
