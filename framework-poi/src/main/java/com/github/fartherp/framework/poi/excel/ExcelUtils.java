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
package com.github.fartherp.framework.poi.excel;

import com.github.fartherp.framework.poi.Constant;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.RichTextString;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;

/**
 * Excel工具类
 *
 * @author CK
 * @date 2017/11/25
 */
public class ExcelUtils {

    /**
     * 无背景颜色普通字体
     *
     * @param workbook
     * @return
     */
    @SuppressWarnings("all")
    public static CellStyle setStyleNoColor(Workbook workbook) {
        CellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直

        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框
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

        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER); // 垂直

        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框

        cellStyle.setFillForegroundColor(IndexedColors.TAN.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

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
        cellStyle.setAlignment(HorizontalAlignment.CENTER); // 居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        cellStyle.setBorderBottom(BorderStyle.THIN); //下边框
        cellStyle.setBorderLeft(BorderStyle.THIN);//左边框
        cellStyle.setBorderTop(BorderStyle.THIN);//上边框
        cellStyle.setBorderRight(BorderStyle.THIN);//右边框

        cellStyle.setFillForegroundColor(IndexedColors.TAN.getIndex());
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setFontName("黑体");
        font.setBold(true);//粗体显示
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

    public static RichTextString setRichTextString(boolean largeDataMode, String type, String value) {
        if (largeDataMode) {
            return new XSSFRichTextString(value);
        }
		if (Constant.OFFICE_EXCEL_2003_POSTFIX.equals(type)) {
			return new HSSFRichTextString(value);
		} else if (Constant.OFFICE_EXCEL_2010_POSTFIX.equals(type)) {
			return new XSSFRichTextString(value);
		}
        throw new IllegalArgumentException("不是Excel文件");
    }
}
