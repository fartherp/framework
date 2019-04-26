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

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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

    private Map<Integer, DealWrapper> wrapperLinkedHashMap = new LinkedHashMap<>();

    private OutputStreamDelegate delegate;

    public AbstractExcelWrite(String fileName, OutputStreamDelegate delegate) {
        this.fileName = fileName;
        this.delegate = delegate;
    }

    public ExcelWrite setLargeDataMode(boolean largeDataMode) {
        this.largeDataMode = largeDataMode;
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

    public abstract void createWb();

    @Override
    public <T> ExcelWrite deal(String[] title, WriteDeal<T> deal, List<T> list) {
        if (fileName == null) {
            throw new IllegalArgumentException("文件名不存在");
        }
        DealWrapper<T> dealWrapper = new DealWrapper<>();
        dealWrapper.setTitle(title);
        dealWrapper.setDeal(deal);
        dealWrapper.setList(list);
        // 按用户自己输入的顺序排序
        int hashCode = title != null && title.length == 0 ? deal.hashCode() : Arrays.hashCode(title);
        wrapperLinkedHashMap.compute(hashCode,
                (integer, oldValue) -> Optional.ofNullable(oldValue).map(o -> {
                    o.getList().addAll(dealWrapper.getList());
                    return o;
                }).orElse(dealWrapper));
        return this;
    }

    @Override
    public <T> ExcelWrite deal(WriteDeal<T> deal, List<T> list) {
        return this.deal(new String[0], deal, list);
    }

    private <T> void list(DealWrapper<T> dealWrapper) {
        List<T> list = dealWrapper.getList();
        // 数据准备好时，最终生成excel，total就是数据大小
        dealWrapper.setTotal(list.size());
        // 第一个sheet创建，一定创建标题
        createSheet(dealWrapper, true);
        for (T t : list) {
            // 数据超过一个sheet最大值，需要创建第二个sheet
            createSheet(dealWrapper, false);
            // 创建一行
            Row sheetRow = currentSheet.createRow(currentRow);
            // 得到要插入的每一条记录
            String[] data = dealWrapper.getDeal().dealBean(t);
            for (int k = 0; k < data.length; k++) {
                // 在一行内循环
                Cell cell = sheetRow.createCell(k);
                cell.setCellValue(data[k]);
                if (this.bodyStyle != null) {
                    cell.setCellStyle(this.bodyStyle);
                }
            }
            currentRow++;
        }
    }

    private <T> void createSheet(DealWrapper<T> dealWrapper, boolean flag) {
        WriteDeal<T> deal = dealWrapper.getDeal();
        int total = dealWrapper.getTotal();

        // excel处理的最大行数
        int maxRows = deal.setMaxRows(this.type);
        if (flag || (total > maxRows && currentRow == maxRows)) {
            currentRow = deal.skipLine();
            createSheet(dealWrapper);
        }
    }

    public abstract <T> void createSheet(DealWrapper<T> dealWrapper);

    @Override
    public void write() {
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
        wrapperLinkedHashMap.forEach((k, v) -> {
            list(v);
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

    final class DealWrapper<T> {
        /**
         * 标题
         */
        private String[] title;
        /**
         * 自定义业务处理
         */
        private WriteDeal<T> deal;
        /**
         * 实际数据
         */
        private List<T> list;
        /**
         * 总数量（同一类型数据，sheet计数）
         */
        private int total;

        public String[] getTitle() {
            return title;
        }

        public void setTitle(String[] title) {
            this.title = title;
        }

        public WriteDeal<T> getDeal() {
            return deal;
        }

        public void setDeal(WriteDeal<T> deal) {
            this.deal = deal;
        }

        public List<T> getList() {
            return list;
        }

        public void setList(List<T> list) {
            this.list = list;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public void increaseTotal() {
            this.total++;
        }
    }
}
