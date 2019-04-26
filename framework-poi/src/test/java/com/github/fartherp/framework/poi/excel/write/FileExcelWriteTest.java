/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.excel.ExcelDto;
import com.github.fartherp.framework.poi.excel.ExcelDto1;
import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 *
 * @author: CK
 * @date: 2017/11/25
 */
public class FileExcelWriteTest {
//    @Test
    public void writeSingleSheetExcel() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String[] title = new String [6];
        title[0] = "登录时间";
        title[1] = "用户名";
        title[2] = "访问端";
        title[3] = "版本系统";
        title[4] = "登录IP";
        title[5] = "状态";
        String fileName = "D:\\style1.xls";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, obj -> {
                    String[] result = new String[6];
                    result[0] = obj.getTime();
                    result[1] = obj.getName();
                    result[2] = obj.getClient();
                    result[3] = obj.getVersion();
                    result[4] = obj.getIp();
                    result[5] = obj.getStatus() + "";
                    return result;
                }, ExcelDataList.getList())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String[] title = new String [6];
        title[0] = "登录时间";
        title[1] = "用户名";
        title[2] = "访问端";
        title[3] = "版本系统";
        title[4] = "登录IP";
        title[5] = "状态";
        String fileName = "D:\\style1.xls";

        String[] title1 = new String [6];
        title1[0] = "id";
        title1[1] = "type";
        title1[2] = "desc";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, obj -> {
                    String[] result = new String[6];
                    result[0] = obj.getTime();
                    result[1] = obj.getName();
                    result[2] = obj.getClient();
                    result[3] = obj.getVersion();
                    result[4] = obj.getIp();
                    result[5] = obj.getStatus() + "";
                    return result;
                }, ExcelDataList.getList1())
                .deal(title1, obj -> {
                    String[] result = new String[3];
                    result[0] = obj.getId() + "";
                    result[1] = obj.getType();
                    result[2] = obj.getDesc();
                    return result;
                }, ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetOverMaxCountExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String[] title = new String [6];
        title[0] = "登录时间";
        title[1] = "用户名";
        title[2] = "访问端";
        title[3] = "版本系统";
        title[4] = "登录IP";
        title[5] = "状态";
        String fileName = "D:\\style1.xls";

        String[] title1 = new String [6];
        title1[0] = "id";
        title1[1] = "type";
        title1[2] = "desc";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, obj -> {
                    String[] result = new String[6];
                    result[0] = obj.getTime();
                    result[1] = obj.getName();
                    result[2] = obj.getClient();
                    result[3] = obj.getVersion();
                    result[4] = obj.getIp();
                    result[5] = obj.getStatus() + "";
                    return result;
                }, ExcelDataList.getList())
                .deal(title1, obj -> {
                    String[] result = new String[3];
                    result[0] = obj.getId() + "";
                    result[1] = obj.getType();
                    result[2] = obj.getDesc();
                    return result;
                }, ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetAndNameExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String[] title = new String [6];
        title[0] = "登录时间";
        title[1] = "用户名";
        title[2] = "访问端";
        title[3] = "版本系统";
        title[4] = "登录IP";
        title[5] = "状态";
        String fileName = "D:\\style1.xls";

        String[] title1 = new String [6];
        title1[0] = "id";
        title1[1] = "type";
        title1[2] = "desc";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, new WriteDeal<ExcelDto>() {
                    @Override
                    public String[] dealBean(ExcelDto obj) {
                        String[] result = new String[6];
                        result[0] = obj.getTime();
                        result[1] = obj.getName();
                        result[2] = obj.getClient();
                        result[3] = obj.getVersion();
                        result[4] = obj.getIp();
                        result[5] = obj.getStatus() + "";
                        return result;
                    }

                    @Override
                    public String name() {
                        return "ExcelDto";
                    }
                }, ExcelDataList.getList1())
                .deal(title1, new WriteDeal<ExcelDto1>() {
                    @Override
                    public String[] dealBean(ExcelDto1 obj) {
                        String[] result = new String[3];
                        result[0] = obj.getId() + "";
                        result[1] = obj.getType();
                        result[2] = obj.getDesc();
                        return result;
                    }

                    @Override
                    public String name() {
                        return "test";
                    }
                }, ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetAndNameOverMaxCountExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String[] title = new String [6];
        title[0] = "登录时间";
        title[1] = "用户名";
        title[2] = "访问端";
        title[3] = "版本系统";
        title[4] = "登录IP";
        title[5] = "状态";
        String fileName = "D:\\style1.xls";

        String[] title1 = new String [6];
        title1[0] = "id";
        title1[1] = "type";
        title1[2] = "desc";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, new WriteDeal<ExcelDto>() {
                    @Override
                    public String[] dealBean(ExcelDto obj) {
                        String[] result = new String[6];
                        result[0] = obj.getTime();
                        result[1] = obj.getName();
                        result[2] = obj.getClient();
                        result[3] = obj.getVersion();
                        result[4] = obj.getIp();
                        result[5] = obj.getStatus() + "";
                        return result;
                    }

                    @Override
                    public String name() {
                        return "ExcelDto";
                    }
                }, ExcelDataList.getList())
                .deal(title1, new WriteDeal<ExcelDto1>() {
                    @Override
                    public String[] dealBean(ExcelDto1 obj) {
                        String[] result = new String[3];
                        result[0] = obj.getId() + "";
                        result[1] = obj.getType();
                        result[2] = obj.getDesc();
                        return result;
                    }

                    @Override
                    public String name() {
                        return "test";
                    }
                }, ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeExcelByInputStream() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\styleInputStream.xls";
        FileExcelWrite.build(this.getClass().getResourceAsStream("/c.xls"), fileName)
                .setLargeDataMode(true)
                .deal(new WriteDeal<ExcelDto>() {
                    public String[] dealBean(ExcelDto obj) {
                        String[] result = new String[3];
                        result[0] = obj.getId() + "";
                        result[1] = obj.getName();
                        result[2] = obj.getAge() + "";
                        return result;
                    }

                    public int skipLine() {
                        return 4;
                    }

                    @Override
                    public Map<String, Object> additional() {
                        Map<String, Object> map = new HashMap<>();
                        map.put("quoteCurrency", "ETH");
                        map.put("symbol", "USDT_ETH");
                        map.put("startTime", "2019-01-09 00:00:00");
                        map.put("endTime", "2019-01-09 12:00:00");
                        return map;
                    }
                }, ExcelDataList.getTenList())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetExcelByInputStream() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\styleInputStream1.xls";
        FileExcelWrite.build(this.getClass().getResourceAsStream("/d.xls"), fileName)
                .setLargeDataMode(true)
                .deal(new WriteDeal<ExcelDto>() {
                    public String[] dealBean(ExcelDto obj) {
                        String[] result = new String[3];
                        result[0] = obj.getId() + "";
                        result[1] = obj.getName();
                        result[2] = obj.getAge() + "";
                        return result;
                    }

                    public int skipLine() {
                        return 4;
                    }

                    @Override
                    public Map<String, Object> additional() {
                        Map<String, Object> map = new HashMap<>();
                        map.put("quoteCurrency", "ETH");
                        map.put("symbol", "USDT_ETH");
                        map.put("startTime", "2019-01-09 00:00:00");
                        map.put("endTime", "2019-01-09 12:00:00");
                        return map;
                    }
                }, ExcelDataList.getTenList())
                .deal(new WriteDeal<ExcelDto1>() {
                    public String[] dealBean(ExcelDto1 obj) {
                        String[] result = new String[3];
                        result[0] = obj.getId() + "";
                        result[1] = obj.getType();
                        result[2] = obj.getDesc();
                        return result;
                    }

                    public int skipLine() {
                        return 4;
                    }

                    @Override
                    public Map<String, Object> additional() {
                        Map<String, Object> map = new HashMap<>();
                        map.put("quoteCurrency", "BTC");
                        map.put("symbol", "BTC_USDT");
                        map.put("startTime", "2019-04-01 00:00:00");
                        map.put("endTime", "2019-04-01 12:00:00");
                        return map;
                    }
                }, ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test(expectedExceptions = RuntimeException.class)
    public void writeExcelOverMaxCountExceptionByInputStream() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\styleInputStream.xls";
        FileExcelWrite.build(this.getClass().getResourceAsStream("/c.xls"), fileName)
                .setLargeDataMode(true)
                .deal(new WriteDeal<ExcelDto>() {
                    public String[] dealBean(ExcelDto obj) {
                        String[] result = new String[3];
                        result[0] = obj.getId() + "";
                        result[1] = obj.getName();
                        result[2] = obj.getAge() + "";
                        return result;
                    }

                    public int skipLine() {
                        return 4;
                    }

                    @Override
                    public Map<String, Object> additional() {
                        Map<String, Object> map = new HashMap<>();
                        map.put("quoteCurrency", "ETH");
                        map.put("symbol", "USDT_ETH");
                        map.put("startTime", "2019-01-09 00:00:00");
                        map.put("endTime", "2019-01-09 12:00:00");
                        return map;
                    }
                }, ExcelDataList.getList())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeExcelMaxCountByInputStream() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        List<ExcelDto> list = ExcelDataList.getList();
        list.remove(65535);
        list.remove(65534);
        list.remove(65533);
        list.remove(65532);
        String fileName = "D:\\styleInputStream.xls";
        FileExcelWrite.build(this.getClass().getResourceAsStream("/c.xls"), fileName)
                .setLargeDataMode(true)
                .deal(new WriteDeal<ExcelDto>() {
                    public String[] dealBean(ExcelDto obj) {
                        String[] result = new String[3];
                        result[0] = obj.getIp();
                        result[1] = obj.getName();
                        result[2] = obj.getVersion();
                        return result;
                    }

                    public int skipLine() {
                        return 4;
                    }

                    @Override
                    public Map<String, Object> additional() {
                        Map<String, Object> map = new HashMap<>();
                        map.put("quoteCurrency", "ETH");
                        map.put("symbol", "USDT_ETH");
                        map.put("startTime", "2019-01-09 00:00:00");
                        map.put("endTime", "2019-01-09 12:00:00");
                        return map;
                    }
                }, list)
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }
}