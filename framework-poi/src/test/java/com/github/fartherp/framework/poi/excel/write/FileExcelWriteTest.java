/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.excel.ExcelDto;
import com.github.fartherp.framework.poi.excel.WriteDeal;
import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.Test;

import java.util.ArrayList;
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
    public void writeExcel() throws Exception {
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
        FileExcelWrite.<ExcelDto>build(title, fileName)
                .setLargeDataMode(false)
                .deal(obj -> {
                    String[] result = new String[6];
                    result[0] = obj.getTime();
                    result[1] = obj.getName();
                    result[2] = obj.getClient();
                    result[3] = obj.getVersion();
                    result[4] = obj.getIp();
                    result[5] = obj.getStatus() + "";
                    return result;
                })
                .list(ExcelWriteStyleTest.getList())// 默认情况下导出数据达到excel最大行，自动切换sheet，（xlsx=1048576，xls=65536）
                .list(ExcelWriteStyleTest.getList1())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

//    @Test
    public void writeExcelByInputStream() throws Exception {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        Map<String, Object> map = new HashMap<>();
        map.put("quoteCurrency", "ETH");
        map.put("symbol", "USDT_ETH");
        map.put("startTime", "2019-01-09 00:00:00");
        map.put("endTime", "2019-01-09 12:00:00");
        String fileName = "D:\\styleInputStream.xls";
        FileExcelWrite.<ExcelDto>build(this.getClass().getResourceAsStream("/c.xls"), fileName)
                .additional(map)
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
                })
                .list(getList())
                .write();

        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }

    public static List<ExcelDto> getList() {
        List<ExcelDto> excelDtos = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelDto dto = new ExcelDto();
            dto.setId((long) i);
            dto.setName("name" + i);
            dto.setAge(i);
            excelDtos.add(dto);
        }
        return excelDtos;
    }
}