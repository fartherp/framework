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

    public static final String[] title = new String[6];
    public static final String[] title1 = new String [6];
    static {
        title[0] = "登录时间";
        title[1] = "用户名";
        title[2] = "访问端";
        title[3] = "版本系统";
        title[4] = "登录IP";
        title[5] = "状态";
        title1[0] = "id";
        title1[1] = "type";
        title1[2] = "desc";
    }

//    @Test
    public void writeSingleOverMaxSheetExcel() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getList())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeTwoOverMaxSheetExcel() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getList())
                .deal(title, get(), ExcelDataList.getList())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeThreeOverMaxSheetExcel() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getList())
                .deal(title, get(), ExcelDataList.getList())
                .deal(title, get(), ExcelDataList.getList())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeSingleEqualMaxSheetExcel() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";
        List<ExcelDto> excelDtos = ExcelDataList.getList();
        excelDtos.remove(65535);
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), excelDtos)
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeTwoEqualMaxSheetExcel() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";

        List<ExcelDto> excelDtos = ExcelDataList.getList();
        excelDtos.remove(65535);
        List<ExcelDto> excelDtos1 = ExcelDataList.getList();
        excelDtos1.remove(65535);
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), excelDtos)
                .deal(title, get(), excelDtos1)
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeThreeEqualMaxSheetExcel() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";
        List<ExcelDto> excelDtos = ExcelDataList.getList();
        excelDtos.remove(65535);
        List<ExcelDto> excelDtos1 = ExcelDataList.getList();
        excelDtos1.remove(65535);
        List<ExcelDto> excelDtos2 = ExcelDataList.getList();
        excelDtos2.remove(65535);

        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), excelDtos)
                .deal(title, get(), excelDtos1)
                .deal(title, get(), excelDtos2)
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeSingleLtMaxSheetExcel() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title, get(), ExcelDataList.getTenList())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeSingleGtMaxSheetExcel() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title, get(), ExcelDataList.getList())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

    private WriteDeal<ExcelDto> get() {
        return obj -> {
            String[] result = new String[6];
            result[0] = obj.getTime();
            result[1] = obj.getName();
            result[2] = obj.getClient();
            result[3] = obj.getVersion();
            result[4] = obj.getIp();
            result[5] = obj.getStatus() + "";
            return result;
        };
    }

//    @Test
    public void writeMultipleSheetExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";

        String[] title1 = new String [6];
        title1[0] = "id";
        title1[1] = "type";
        title1[2] = "desc";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getTenList())
                .deal(title1, get1(), ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

    private WriteDeal<ExcelDto1> get1() {
        return obj -> {
            String[] result = new String[3];
            result[0] = obj.getId() + "";
            result[1] = obj.getType();
            result[2] = obj.getDesc();
            return result;
        };
    }

//    @Test
    public void writeMultipleSheetOverMaxCountExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getList())
                .deal(title1, get1(), ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetOverMaxTwoCountExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";
        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getList())
                .deal(title1, get1(), ExcelDataList.getTenList1())
                .deal(title, get(), ExcelDataList.getList())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetOverMaxTwoDoubleCountExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";

        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, get(), ExcelDataList.getList())
                .deal(title1, get1(), ExcelDataList.getTenList1())
                .deal(title, get(), ExcelDataList.getList())
                .deal(title1, get1(), ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetAndNameExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";

        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetAndNameLtMaxExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";

        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetAndNameLtMaxDisorderExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";

        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .deal(title, getExcelDtoName(), ExcelDataList.getTenList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

    public WriteDeal<ExcelDto> getExcelDtoName() {
        return new WriteDeal<ExcelDto>() {
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
        };
    }

    public WriteDeal<ExcelDto1> getTestName() {
        return new WriteDeal<ExcelDto1>() {
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
        };
    }

//    @Test
    public void writeMultipleSheetAndNameOverMaxCountExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";

        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetAndNameOverMaxCountTwoExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";

        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetAndNameOverMaxCountTwoDisorderExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";

        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeMultipleSheetAndNameOverMaxCountTwoTwoDisorderExcel() {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        String fileName = "D:\\style1.xls";

        FileExcelWrite.build(fileName)
                .setLargeDataMode(false)
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
                .deal(title1, getTestName(), ExcelDataList.getList1())
                .deal(title, getExcelDtoName(), ExcelDataList.getList())
                .deal(title1, getTestName(), ExcelDataList.getTenList1())
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
                .setLargeDataMode(false)
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