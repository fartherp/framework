/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.common.util.FileUtilies;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;

import static com.github.fartherp.framework.poi.Constant.EXCEL_CONTENT_TYPE;

/**
 * 响应流
 *
 * @author: CK
 * @date: 2017/11/25
 */
public class HttpServletResponseExcelWrite<T> extends AbstractExcelWrite<T> {

    private HttpServletResponse response;

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
     *  HttpServletResponseExcelWrite excelWrite = new HttpServletResponseExcelWrite(this.getClass().getResourceAsStream("/c.xls"), fileName, request, response);
     *  excelWrite.additional(map).deal(new WriteDeal<ExcelDto>() {
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
     * @see <a href="https://github.com/fartherp/framework/blob/master/framework-poi/src/test/resources/c.xls">
     *     file content</a>
     */
    public HttpServletResponseExcelWrite(InputStream inputStream, String fileName, HttpServletRequest request, HttpServletResponse response) {
        super(inputStream, fileName);
        this.setResponse(request, response);
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
     *  HttpServletResponseExcelWrite<ExcelDto> excelWrite = new HttpServletResponseExcelWrite<>(title, fileName, request, response);
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
     */
    public HttpServletResponseExcelWrite(String[] title, String fileName, HttpServletRequest request, HttpServletResponse response) {
        super(title, fileName);
        this.setResponse(request, response);
    }

    public ExcelWrite<T> createOutputStream() {
        try {
            this.outputStream = this.response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("响应流异常", e);
        }
        return this;
    }

    public ExcelWrite<T> setResponse(HttpServletRequest request, HttpServletResponse response) {
        this.response = response;
        response.reset();
        response.setContentType(EXCEL_CONTENT_TYPE);
        String filename = FileUtilies.getFileName(this.fileName, request);
        response.setHeader("content-disposition", "attachment; filename=" + filename);
        return this;
    }
}
