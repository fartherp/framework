/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * Author: CK
 * Date: 2016/5/29
 */
public class ExcelWrite<T> {

    /**
     *
     * @param title 标题
     * @param list 实际数据列表
     * @param deal 实际处理结果
     */
    public void writeExcelXls(String [] title, List<T> list, WriteDeal<T> deal, String fileName) {
        // 创建Excel文档
        HSSFWorkbook hssfWorkbook = new HSSFWorkbook();
        // sheet 对应一个工作页
        HSSFSheet sheet = hssfWorkbook.createSheet("sheet1");
        HSSFRow sheetRow = sheet.createRow(0); // 下标为0的行开始
        for (int i = 0; i < title.length; i++) {
            HSSFCell cell = sheetRow.createCell(i);
            cell.setCellValue(new HSSFRichTextString(title[i]));
        }
        for (int i = 0; i < list.size(); i++) {
            // 创建一行
            sheetRow = sheet.createRow(i + 1);
            // 得到要插入的每一条记录
            String [] arr = deal.dealBean(list.get(i));
            for (int j = 0; j < arr.length; j++) {
                // 在一行内循环
                HSSFCell cell = sheetRow.createCell(j);
                cell.setCellValue(arr[j]);
            }
        }
        // 创建文件输出流，准备输出电子表格
        OutputStream out = null;
        try {
            out = new FileOutputStream(fileName);
            hssfWorkbook.write(out);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static interface WriteDeal<T> {
        /**
         * 一行excel数据返回业务BEAN
         * @param obj 一行excel数据
         * @return 业务BEAN
         */
         String[] dealBean(T obj);
    }
}
