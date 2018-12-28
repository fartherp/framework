/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.excel.ExcelDto;
import org.apache.commons.lang3.time.StopWatch;
import org.testng.annotations.Test;

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
        FileExcelWrite<ExcelDto> excelDtoExcelWrite = new FileExcelWrite<ExcelDto>(title, fileName);
//        excelDtoExcelWrite.setLargeDataMode(false);
        excelDtoExcelWrite.deal(
                obj -> {
                    String[] result = new String[6];
                    result[0] = obj.getTime();
                    result[1] = obj.getName();
                    result[2] = obj.getClient();
                    result[3] = obj.getVersion();
                    result[4] = obj.getIp();
                    result[5] = obj.getStatus() + "";
                    return result;
                });
        // 默认情况下导出数据达到excel最大行，自动切换sheet，（xlsx=1048576，xls=65536）
        excelDtoExcelWrite.list(ExcelWriteStyleTest.getList())
                .list(ExcelWriteStyleTest.getList1())
                .write();
        stopWatch.stop();
        System.out.println(stopWatch.toString());
    }
}