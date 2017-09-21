/*
 * Copyright (c) 2017. CK. All rights reserved.
 */

package com.github.fartherp.framework.poi.excel.write;

import com.github.fartherp.framework.poi.excel.ExcelDto;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

public class ExcelWriteTest {

    @Test
    public void testWriteExcelXls() throws Exception {
        ExcelWrite<ExcelDto> excelWrite = new ExcelWrite<ExcelDto>();
        String [] title = new String [3];
        title[0] = "ID";
        title[1] = "名称";
        title[2] = "年龄";
        excelWrite.writeExcelXls(title, getList(), new ExcelWrite.WriteDeal<ExcelDto>() {
            public String[] dealBean(ExcelDto obj) {
                String [] result = new String [3];
                result[0] = obj.getId().toString();
                result[1] = obj.getName();
                result[2] = obj.getAge().toString();
                return result;
            }
        }, "D:\\a.xls");
    }

    public static List<ExcelDto> getList() {
        List<ExcelDto> excelDtos = new ArrayList<ExcelDto>();
        for (int i = 1; i < 4; i++) {
            ExcelDto dto = new ExcelDto();
            dto.setId(1111111111111111111L);
            dto.setName(i + "");
            dto.setAge(i);
            excelDtos.add(dto);
        }
        return excelDtos;
    }
}