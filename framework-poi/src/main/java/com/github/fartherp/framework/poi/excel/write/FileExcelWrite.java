/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * 文件流
 *
 * Author: CK
 * Date: 2017/11/25
 */
public class FileExcelWrite<T> extends AbstractExcelWrite<T> {

    /**
     * <p>
     * Example code:
     * </p>
     * <pre>
     *  Map<String, Object> map = new HashMap<>();
     *  map.put("quoteCurrency", "ETH");
     *  map.put("symbol", "USDT_ETH");
     *  map.put("startTime", "2019-01-09 00:00:00");
     *  map.put("endTime", "2019-01-09 12:00:00");
     *  String fileName = "D:\\styleInputStream.xls";
     *  FileExcelWrite<ExcelDto> excelWrite = new FileExcelWrite<>(this.getClass().getResourceAsStream("/c.xls"), fileName);
     *  excelWrite.additional(map).deal(new WriteDeal<>() {
     *      public String[] dealBean(ExcelDto obj) {
     *          String[] result = new String[3];
     *          result[0] = obj.getId() + "";
     *          result[1] = obj.getName();
     *          result[2] = obj.getAge() + "";
     *          return result;
     *      }
     *
     *      public int skipLine() {
     *          return 4;
     *      }
     *  }).list(getList()).write();
     * </pre>
     *
     * @param inputStream the file InputStream
     * @param fileName the output file name
     *
     * @see <a href="https://github.com/fartherp/framework/blob/master/framework-poi/src/test/resources/c.xls">
     *     file content</a>
     */
    public FileExcelWrite(InputStream inputStream, String fileName) {
        super(inputStream, fileName);
    }

    /**
     * <p>
     * Example code:
     * </p>
     * <pre>
     *  String[] title = new String [6];
     *  title[0] = "登录时间";
     *  title[1] = "用户名";
     *  title[2] = "访问端";
     *  title[3] = "版本系统";
     *  title[4] = "登录IP";
     *  title[5] = "状态";
     *  String fileName = "D:\\style1.xls";
     *  FileExcelWrite<ExcelDto> excelWrite = new FileExcelWrite<>(title, fileName);
     *  excelWrite.setLargeDataMode(false).deal(obj -> {
     *      String[] result = new String[6];
     *      result[0] = obj.getTime();
     *      result[1] = obj.getName();
     *      result[2] = obj.getClient();
     *      result[3] = obj.getVersion();
     *      result[4] = obj.getIp();
     *      result[5] = obj.getStatus() + "";
     *      return result;
     *  }).list(ExcelWriteStyleTest.getList())// 默认情况下导出数据达到excel最大行，自动切换sheet，（xlsx=1048576，xls=65536）
     *          .list(ExcelWriteStyleTest.getList1())
     *          .write();
     * </pre>
     *
     * @param title the output file title
     * @param fileName the output file name
     */
    public FileExcelWrite(String[] title, String fileName) {
        super(title, fileName);
    }

    public ExcelWrite<T> createOutputStream() {
        try {
            this.outputStream = new FileOutputStream(this.fileName);
        } catch (FileNotFoundException e) {
            throw new RuntimeException("文件不存在", e);
        }
        return this;
    }
}
