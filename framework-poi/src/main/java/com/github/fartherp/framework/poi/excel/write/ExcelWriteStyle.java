/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.Constant;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.servlet.http.HttpServletResponse;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/5/29
 */
public class ExcelWriteStyle {

    /**
     *
     * @param head 头
     * @param title 标题
     * @param condition 条件
     * @param fileName 文件名
     * @param list 数据
     * @param <T> 泛型
     * @return ExcelWrite
     */
    @SuppressWarnings("all")
    public static <T> ExcelWrite<T> build(String head, String[] title, String condition, String fileName, List<T> list) {
        ExcelWrite<T> excelWrite = new ExcelWrite<T>();
        excelWrite.setHead(head).setTitle(title).setCondition(condition).setFileName(fileName).setList(list);
        return excelWrite;
    }

    public static class ExcelWrite<T> {
        /**
         * 文件类型
         */
        private String type;

        /**
         * 头
         */
        private String head;

        /**
         * 标题
         */
        private String[] title;

        /**
         * 条件
         */
        private String condition;

        /**
         * 文件名
         */
        private String fileName;

        /**
         * 数据
         */
        private List<T> list;

        private OutputStream outputStream;

        private HttpServletResponse response;

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
            row0.setHeight(deal.getHeight());
            offset++;

            Cell cell0 = row0.createCell(0);
            cell0.setCellValue(setRichTextString(type, head));

            CellStyle lightColorStrong = setStyleLightColorStrong(wb);
            setRegionStyle(sheet, region0, lightColorStrong);

            CellRangeAddress region1 = new CellRangeAddress(offset, offset, (short) 0, (short) title.length - 1);
            sheet.addMergedRegion(region1);

            Row row1 = sheet.createRow(offset);
            row1.setHeight(deal.getHeight());
            offset++;

            Cell cell1 = row1.createCell(0);
            cell1.setCellValue(setRichTextString(type, condition));

            setRegionStyle(sheet, region1, lightColorStrong);

            Row titleRow = sheet.createRow(offset);
            titleRow.setHeight(deal.getHeight());
            offset++;

            CellStyle colorStrong = setStyleColorStrong(wb);
            int[] columnWidth = deal.getColumnWidth(title);
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
                dataRow.setHeight(deal.getHeight());
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

        public ExcelWrite createFileOutputStream() {
            try {
                this.outputStream = new FileOutputStream(this.fileName);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            return this;
        }

        public ExcelWrite createHttpOutputStream() {
            try {
                this.outputStream = this.response.getOutputStream();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return this;
        }

        public ExcelWrite setType(String type) {
            this.type = type;
            return this;
        }

        public ExcelWrite setHead(String head) {
            this.head = head;
            return this;
        }

        public ExcelWrite setTitle(String[] title) {
            this.title = title;
            return this;
        }

        public ExcelWrite setCondition(String condition) {
            this.condition = condition;
            return this;
        }

        public ExcelWrite setFileName(String fileName) {
            this.fileName = fileName;
            return this;
        }

        public ExcelWrite setList(List<T> list) {
            this.list = list;
            return this;
        }

        public ExcelWrite setResponse(HttpServletResponse response) {
            this.response = response;
            response.reset();
            response.setContentType("application/msexcel;charset=GBK");
            String filename;
            try {
                filename = URLEncoder.encode(this.fileName, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
                try {
                    filename = new String(fileName.getBytes("UTF-8"), "iso-8859-1");
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                    filename = fileName;
                }
            }
            response.setHeader("content-disposition", "attachment; filename=" + filename);
            return this;
        }
    }

    /**
     * 无背景颜色普通字体
     *
     * @param workbook
     * @return
     */
    @SuppressWarnings("all")
    public static CellStyle setStyleNoColor(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框
        cellStyle.setWrapText(true);
        return cellStyle;
    }

    /**
     * Color背景颜色
     *
     * @param workbook
     * @return
     */
    @SuppressWarnings("all")
    public static CellStyle setStyleColorStrong(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();

        cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框

        cellStyle.setFillForegroundColor(IndexedColors.TAN.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        cellStyle.setWrapText(true);
        return cellStyle;
    }

    /**
     * LightColor背景颜色，加粗字体
     *
     * @param workbook
     * @return
     * @see <code>http://blog.csdn.net/for_china2012/article/details/29844661</code>
     */
    @SuppressWarnings("all")
    public static CellStyle setStyleLightColorStrong(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(CellStyle.ALIGN_CENTER); // 居中
        cellStyle.setVerticalAlignment(CellStyle.VERTICAL_CENTER);

        cellStyle.setBorderBottom(CellStyle.BORDER_THIN); //下边框
        cellStyle.setBorderLeft(CellStyle.BORDER_THIN);//左边框
        cellStyle.setBorderTop(CellStyle.BORDER_THIN);//上边框
        cellStyle.setBorderRight(CellStyle.BORDER_THIN);//右边框

        cellStyle.setFillForegroundColor(IndexedColors.TAN.getIndex());
        cellStyle.setFillPattern(CellStyle.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setFontName("黑体");
        font.setBoldweight(Font.BOLDWEIGHT_BOLD);//粗体显示
        cellStyle.setFont(font);

        cellStyle.setWrapText(true);
        return cellStyle;
    }

    /**
     * 解决合并单元格边框消失问题，不仅需要调用此方法，单元格自身也需要正常设置上下左右的边框
     *
     * @param sheet
     * @param region
     * @param cs
     */
    @SuppressWarnings("all")
    public static void setRegionStyle(Sheet sheet, CellRangeAddress region, CellStyle cs) {
        for (int i = region.getFirstRow(); i <= region.getLastRow(); i++) {
            Row row = sheet.getRow(i);
            if (row == null) {
                row = sheet.createRow(i);
            }
            for (int j = region.getFirstColumn(); j <= region.getLastColumn(); j++) {
                Cell cell = row.getCell(j);
                if (cell == null) {
                    cell = row.createCell(j);
                    cell.setCellValue("");
                }
                cell.setCellStyle(cs);
            }
        }
    }

    public static RichTextString setRichTextString(String type, String value) {
        if (Constant.OFFICE_EXCEL_2003_POSTFIX.equals(type)) {
            return new HSSFRichTextString(value);
        } else if (Constant.OFFICE_EXCEL_2010_POSTFIX.equals(type)) {
            return new XSSFRichTextString(value);
        }
        throw new IllegalArgumentException("不是Excel文件");
    }

    public static interface WriteDeal<T> {
        /**
         * 一行excel数据返回业务BEAN
         *
         * @param obj 一行excel数据
         * @return 业务BEAN
         */
        String[] dealBean(T obj);

        int[] getColumnWidth(String[] title);

        short getHeight();
    }

    public static abstract class DefaultWriteDeal<T> implements WriteDeal<T> {
        public int[] getColumnWidth(String[] title) {
            int[] columnWidth = new int[title.length];
            for (int i = 0; i < title.length; i++) {
                columnWidth[i] = Constant.WIDTH_DEFAULT;
            }
            return columnWidth;
        }

        public short getHeight() {
            return Constant.HEIGHT_DEFAULT;
        }
    }
}
