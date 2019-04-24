/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.common.util.FileUtilies;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

import static com.github.fartherp.framework.poi.Constant.EXCEL_CONTENT_TYPE;

/**
 * 响应流
 *
 * @author: CK
 * @date: 2017/11/25
 */
public class HttpServletResponseExcelWrite implements OutputStreamDelegate {

    private HttpServletResponse response;

    private HttpServletRequest request;

    private HttpServletResponseExcelWrite(HttpServletRequest request, HttpServletResponse response) {
        this.request = request;
        this.response = response;
    }

    @Override
    public OutputStream createOutputStream(String fileName) {
        try {
            this.setResponse(request, response, fileName);
            return this.response.getOutputStream();
        } catch (IOException e) {
            throw new RuntimeException("响应流异常", e);
        }
    }

    private void setResponse(HttpServletRequest request, HttpServletResponse response, String fileName) {
        this.response = response;
        response.reset();
        response.setContentType(EXCEL_CONTENT_TYPE);
        String filename = FileUtilies.getFileName(fileName, request);
        response.setHeader("content-disposition", "attachment; filename=" + filename);
    }

    /**
     * <p>
     * Example code:
     * </p>
     * <pre>
     *  Map&lt;String, Object&gt; map = new HashMap<>();
     *  map.put("quoteCurrency", "ETH");
     *  map.put("symbol", "USDT_ETH");
     *  map.put("startTime", "2019-01-09 00:00:00");
     *  map.put("endTime", "2019-01-09 12:00:00");
     *  String fileName = "D:\\styleInputStream.xls";
     *  HttpServletResponseExcelWrite.build(this.getClass().getResourceAsStream("/c.xls"), fileName, request, response)
     *          .additional(map)
     *          .deal(new WriteDeal&lt;ExcelDto&gt;() {
     *              public String[] dealBean(ExcelDto obj) {
     *                  String[] result = new String[3];
     *                  result[0] = obj.getId() + "";
     *                  result[1] = obj.getName();
     *                  result[2] = obj.getAge() + "";
     *                  return result;
     *              }
     *
     *              public int skipLine() {
     *                  return 4;
     *              }
     *          }, getList())
     *          .write();
     * </pre>
     *
     * @see <a href="https://github.com/fartherp/framework/blob/master/framework-poi/src/test/resources/c.xls">
     *     file content</a>
     */
    public static InputStreamExcelWrite build(InputStream inputStream, String fileName, HttpServletRequest request, HttpServletResponse response) {
        Objects.requireNonNull(inputStream);
        Objects.requireNonNull(fileName);
        Objects.requireNonNull(request);
        Objects.requireNonNull(response);
        return new CopyInputStreamExcelWrite(inputStream, fileName, new HttpServletResponseExcelWrite(request, response));
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
     *  HttpServletResponseExcelWrite.build(fileName, request, response)
     *          .setLargeDataMode(false)
     *          .deal(title, obj -> {
     *              String[] result = new String[6];
     *              result[0] = obj.getTime();
     *              result[1] = obj.getName();
     *              result[2] = obj.getClient();
     *              result[3] = obj.getVersion();
     *              result[4] = obj.getIp();
     *              result[5] = obj.getStatus() + "";
     *              return result;
     *          }, ExcelDataList.getList())
     *          .write();
     * </pre>
     */
    public static ExcelWrite build(String fileName, HttpServletRequest request, HttpServletResponse response) {
        Objects.requireNonNull(fileName);
        Objects.requireNonNull(request);
        Objects.requireNonNull(response);
        return new CreateNewExcelWrite(fileName, new HttpServletResponseExcelWrite(request, response));
    }
}
